plugins {
    java
    kotlin("jvm") version "1.3.72"
//    id("org.junit.platform.gradle.plugin")
}

group = "org.logibar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.calimero:calimero-core:2.5-SNAPSHOT")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.3")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.3")
//    implementation("com.fasterxml.jackson.core:jackson-core:2.11.1")
//    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.11.1")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.1")
//    runtimeOnly "com.github.calimero:calimero-rxtx:${version}"
//    testCompile("junit", "junit", "4.12")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}
