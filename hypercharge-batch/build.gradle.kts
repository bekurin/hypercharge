import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "Hypercharge Batch Server"

tasks.getByName<BootJar>("bootJar") {
	enabled = true
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-batch")
}
