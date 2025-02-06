plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.asciidoctor.jvm.convert") version "4.0.4"
}

fun getGitHash(): String {
	return providers.exec {
		commandLine("git", "rev-parse", "--short", "HEAD")
	}.standardOutput.asText.get().trim()
}

group = "kr.hhplus.be"
version = getGitHash()
val asciidoctorExt: Configuration by configurations.creating

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

val querydslDir = "$buildDir/generated/querydsl"

tasks.named<Test>("test") {
	useJUnitPlatform()
}


tasks.register<Delete>("cleanQuerydsl") {
	delete(file(querydslDir))
}

tasks.withType<JavaCompile> {
	options.generatedSourceOutputDirectory.set(file(querydslDir))
}
repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.0")
	}
}

dependencies {
	// Spring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	// spring doc
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
	// AOP
	implementation("org.springframework.boot:spring-boot-starter-aop")
	//QueryDSL
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")
	annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	// Redis
	implementation("org.redisson:redisson-spring-boot-starter:3.23.2")

	// DB
	runtimeOnly("com.mysql:mysql-connector-j")

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.28") // Lombok 추가
	annotationProcessor("org.projectlombok:lombok:1.18.28")

	// Test
	testImplementation("org.testcontainers:redis")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mysql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// spring doc
//	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
//	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}



val snippetsDir by extra { file("build/generated-snippets") }

tasks {

	// 5. test Task snippetsDir 추가
	test {
		outputs.dir(snippetsDir)
		useJUnitPlatform()
	}

	// 6. asciidoctor Task 추가
	asciidoctor {
		inputs.dir(snippetsDir)
		configurations("asciidoctorExt")
		sources{
			include("**/index.adoc")
		}
		baseDirFollowsSourceFile()
		dependsOn(test)
	}

	// 7. bootJar Settings
	bootJar {
		dependsOn(asciidoctor)
		from ("build/docs/asciidoc") {
			into("static/docs")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	systemProperty("user.timezone", "UTC")
}