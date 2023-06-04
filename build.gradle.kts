plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.allopen") version "1.8.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.0-Beta"
    id("com.diffplug.spotless") version "6.10.0"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project


dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-resteasy")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
}

group = "org.grjus"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks {
    spotless {
        kotlin {
            ktlint("0.46.1")
            trimTrailingWhitespace()
            endWithNewline()
            custom("Refuse wildcard imports") {
                if ("import .*\\*".toRegex().containsMatchIn(it)) {
                    error("Do not use wildcard imports.")
                } else {
                    it
                }
            }
            targetExclude("build/**")
        }
    }
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}
