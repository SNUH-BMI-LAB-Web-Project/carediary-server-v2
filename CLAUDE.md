# CLAUDE.md

이 파일은 Claude Code가 CareDiary Server v2 저장소에서 작업할 때 따라야 할 가이드라인입니다.

## 프로젝트 개요

희귀질환 환자가 직접 작성한 돌봄일기에서 정신 상태와 SDoH를 LLM 기반으로 자동 추출·분석하는 서비스.

- **Spring Boot 4.0** + **Kotlin 2.2** + **Java 21**
- **Spring Security 6** + **OAuth2** (Kakao, Naver, Google)
- **JWT** 기반 Stateless 인증
- **MySQL** (prod) / **H2** (local)

## 아키텍처

레이어드 아키텍처 + Facade 패턴:

```
Controller (Api 인터페이스 구현)
    ↓
Facade (복수 Service 조율)
    ↓
Service (비즈니스 로직)
    ↓
Repository (Spring Data JPA)
    ↓
Entity
```

각 레이어에서는 바로 아래 레이어만 참조할 수 있다.
레이어 구조를 지켜서 구현해야 한다.
예) Facade는 Service 레이어만 참조 가능

## 디렉토리 구조

```
src/main/kotlin/kr/io/snuhbmilab/carediaryserverv2/
├── domain/           # 비즈니스 도메인
│   ├── user/         # 사용자, 회원가입, 위험도 평가
│   ├── diary/        # 일기 작성, AI 분석, 복지서비스 추천
│   ├── scalequestion/# 척도 질문지
│   └── home/         # 홈 대시보드
├── admin/            # 관리자 기능
├── auth/             # JWT, OAuth2, Security
├── common/           # 공통 설정, 예외, 응답 포맷
└── external/         # 외부 API 연동 (Feign)
```

각 도메인 하위 구조:
```
{domain}/
├── controller/       # REST 컨트롤러
├── {Domain}Api.kt    # API 인터페이스 (Swagger 문서화)
├── facade/           # 서비스 조율 계층
├── service/          # 비즈니스 로직
├── repository/       # JPA Repository
├── entity/           # JPA Entity
├── dto/
│   ├── request/
│   └── response/
├── exception/        # 도메인별 예외
└── constants/        # 도메인별 상수
```

## 빌드 및 실행

```bash
# 빌드
./gradlew build

# 로컬 실행
./gradlew bootRun --args='--spring.profiles.active=local'

# 테스트
./gradlew test
```

## 코딩 컨벤션

### Entity 작성
- `BaseTimeEntity` 상속 필수 (createdAt, updatedAt 자동 관리)
- `allOpen` 플러그인 적용됨 (final 문제 없음)

```kotlin
@Entity
class MyEntity(
    // 필드
) : BaseTimeEntity()
```

### Controller 작성
- Api 인터페이스 정의 후 Controller에서 구현
- `@UserId` 어노테이션으로 JWT에서 사용자 ID 추출

```kotlin
// MyApi.kt
interface MyApi {
    @Operation(summary = "설명")
    @GetMapping("/path")
    fun myEndpoint(@UserId userId: UUID): MyResponse
}

// MyController.kt
@RestController
class MyController(private val myFacade: MyFacade) : MyApi {
    override fun myEndpoint(userId: UUID) = myFacade.doSomething(userId)
}
```

### 응답 형식
- 모든 응답은 `CommonResponse`로 자동 래핑됨
- 성공 코드: `SuccessCode` enum 사용
- 예외는 `BusinessException` + `ErrorCode` 조합

```kotlin
// 성공 응답
return MyResponse(data)

// 예외 발생
throw BusinessException(ErrorCode.NOT_FOUND)
```

### DTO 네이밍
- Request: `{Action}{Domain}Request` (예: `CreateDiaryRequest`)
- Response: `{Domain}{Detail}Response` (예: `DiaryDetailResponse`)

## 새 도메인 추가 시

1. `domain/{도메인명}/` 디렉토리 생성
2. 위 디렉토리 구조대로 파일 생성
3. Api 인터페이스 → Controller → Facade → Service → Repository 순서로 구현
4. 필요 시 `SecurityConfig.kt`에 경로 인가 규칙 추가

## 인증 관련 수정 시

- `SecurityConfig.kt`: 경로별 인가 규칙
- `JwtAuthenticationFilter.kt`: 토큰 검증 로직
- 새 public 엔드포인트는 `permitAll()` 명시 필요

```kotlin
// SecurityConfig.kt 예시
.requestMatchers("/api/v1/public/**").permitAll()
.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
```

## 외부 API 연동 시

`external/` 디렉토리의 Feign Client 패턴 사용:

```kotlin
@FeignClient(name = "myApi", url = "\${external-api.my-uri}")
interface MyExternalClient {
    @PostMapping("/endpoint")
    fun call(@RequestBody request: MyRequest): MyExternalResponse
}
```

## 환경 설정

- `application-local.yml`: 개발용 (하드코딩된 값)
- `application-prod.yml`: 프로덕션 (환경변수 사용)

프로덕션 환경변수:
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `OAUTH2_*_CLIENT_ID`, `OAUTH2_*_CLIENT_SECRET`
- `JWT_SECRET_KEY`, `JWT_ISSUER`, `JWT_EXPIRES_IN`
- `CORS_ALLOWED_ORIGINS`
- `EXTERNAL_API_MODEL_URI`

## 주의사항

1. **DDL 변경**: `ddl-auto: update` 사용 중. 프로덕션 스키마 변경 시 마이그레이션 검토
2. **민감 정보**: 프로덕션 설정에 하드코딩 금지, 환경변수 사용
3. **테스트**: 변경 후 `./gradlew test` 실행하여 기존 테스트 통과 확인
4. **Swagger**: http://localhost:8080/swagger-ui.html 에서 API 문서 확인