plugins {
	kotlin("jvm") version "2.2.21" apply false
	kotlin("plugin.spring") version "2.2.21" apply false
	id("org.springframework.boot") version "4.0.1" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
	group = "com.brawl.stars"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlin.plugin.spring")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
	}

	the<JavaPluginExtension>().apply {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	dependencies {
		"implementation"("org.jetbrains.kotlin:kotlin-reflect")
		"testImplementation"("org.springframework.boot:spring-boot-starter-test")
		"testImplementation"("org.jetbrains.kotlin:kotlin-test-junit5")
		"testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
