plugins {
    java
    application
}

group = "com.github.sqyyy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies { }

application {
    mainClass.set("com.github.sqyyy.urban.assembler.Bootstrap")
}
