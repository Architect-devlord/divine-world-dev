plugins {
    java
}

group = "com.divineworld"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}


repositories {
    mavenCentral()
    mavenLocal()
    flatDir{
        dirs("libs")
    }
}

dependencies {
    compileOnly(files("libs/paper-api-1.21.8-R0.1-20250804.131414-24.jar"))
    implementation(files("libs/adventure-api-4.14.0.jar"))
    implementation(files("libs/adventure-key-4.14.0.jar")) 
    implementation(files("libs/gson:2.13.1.jar"))  
    
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        archiveFileName.set("DivineWorld.jar")
    }  
}