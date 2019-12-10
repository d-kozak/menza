package io.dkozak.menza

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MenzaApplication

fun main(args: Array<String>) {
	runApplication<MenzaApplication>(*args)
}
