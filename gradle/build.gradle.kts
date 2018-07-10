import java.net.URI

plugins {
    java
    id("org.springframework.boot") version "2.0.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
    id("io.franzbecker.gradle-lombok") version "1.14"
    id("net.ltgt.apt-idea") version "0.17"
}

group = "com.us.itp.sandbox.webarch"
version = "1.0-SNAPSHOT"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    spring()
    lombok()
    htmlUnit()
}

fun DependencyHandlerScope.spring() {
    fun starter(name: String): String = "org.springframework.boot:spring-boot-starter-$name"

    implementation(starter("web"))
    implementation(starter("thymeleaf"))
    testImplementation(starter("test"))
}

fun DependencyHandlerScope.lombok() {
    "org.projectlombok:lombok:1.18.0".let {
        compileOnly(it)
        testCompileOnly(it)
        annotationProcessor(it)
        testAnnotationProcessor(it)
    }
}

fun DependencyHandlerScope.htmlUnit() {
    testImplementation("net.sourceforge.htmlunit:htmlunit")
}
