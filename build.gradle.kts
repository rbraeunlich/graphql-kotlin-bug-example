import com.expediagroup.graphql.plugin.gradle.config.GraphQLClientType
import com.expediagroup.graphql.plugin.gradle.tasks.GraphQLGenerateClientTask

buildscript {
    repositories {
        jcenter()
        mavenLocal()
        mavenCentral()
    }
}


plugins {
    kotlin("jvm") version "1.4.21"
    id("com.expediagroup.graphql") version "4.0.0-alpha.11"
    id("org.springframework.boot") version "2.4.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

val generatedSourcesPath = project.buildDir.path + "/generated"
val generateGraphQLClient = tasks.register<GraphQLGenerateClientTask>("generateGraphQLClient")
generateGraphQLClient {
    packageName.set("com.promaterial.network.graphql")
    schemaFileName.set("src/main/resources/graphql/dev.schema.graphql")
    clientType.set(GraphQLClientType.WEBCLIENT)
    allowDeprecatedFields.set(true)
    queryFileDirectory.set("src/main/resources/graphql")
    outputDirectory.set(File(generatedSourcesPath))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2020.0.0"))
    implementation("com.expediagroup:graphql-kotlin-spring-client:4.0.0-alpha.11")
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDir(generatedSourcesPath)
    }
}
tasks.compileKotlin {
    dependsOn(generateGraphQLClient)
    kotlinOptions {
        jvmTarget = "11"
    }
}
tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "11"
    }
}
