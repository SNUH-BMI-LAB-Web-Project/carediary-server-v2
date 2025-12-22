package kr.io.snuhbmilab.carediaryserverv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone

@SpringBootApplication
class CarediaryServerV2Application

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    runApplication<CarediaryServerV2Application>(*args)
}
