import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "Hypercharge Batch Server"

tasks.getByName<BootJar>("bootJar") {
	enabled = true
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

dependencies {
	implementation(project(":hypercharge-domain"))
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework:spring-web")
	implementation("org.springframework.boot:spring-boot-starter-json")
	runtimeOnly("com.mysql:mysql-connector-j")
}
