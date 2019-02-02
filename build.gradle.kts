import com.jfrog.bintray.gradle.BintrayExtension

val artifactGroup = "com.github.jneat"
val artifactVersion = "0.5.1"

plugins {
    id("java")
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
    id("io.franzbecker.gradle-lombok") version "2.0"
}

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    runtime("org.mybatis:mybatis:3.3.0")

    testCompile("org.mybatis:mybatis:3.3.0")
    testCompile("org.testng:testng:6.14.2")
    testCompile("org.assertj:assertj-core:3.4.1")
    testCompile("org.postgresql:postgresql:42.2.1")
    testCompile("com.github.jneat:jneat:0.3")
}

//Include runtime for compilation
//sourceSets.main.compileClasspath += configurations.runtime
//javadoc.classpath += configurations.runtime
//sourceSets["main"].compileClasspath += configurations["runtime"]
tasks {

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceSets["main"].compileClasspath += configurations["runtime"]
    }

    javadoc {
        classpath += configurations["runtime"]
    }

    test {
        useTestNG()

        testLogging.showStandardStreams = false
        reports.html.isEnabled = true
        reports.html.destination = file("$buildDir/reports/testng")

//        testLogging.showStandardStreams = true
        // listen to standard out and standard error of the test JVM(s)
        onOutput(KotlinClosure2<TestDescriptor, TestOutputEvent, Unit>({descriptor, event ->
            println("Test: $descriptor  produced standard out/err: ${event.message}")
        }))

        afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ descr, r ->
            if (descr.parent == null) {
                println("TestNG total:${r.testCount}, success:${r.successfulTestCount}, fail:${r.failedTestCount}, skip:${r.skippedTestCount}")
                println("REPORT: file://${reports.html.destination}/index.html")
            } else Unit
        }))
    }

    val sourcesJar by creating(Jar::class) {
        dependsOn(classes)
        classifier = "sources"
        from(sourceSets["main"].allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn(javadoc)
        classifier = "javadoc"
        from(javadoc.get().destinationDir)
    }

    artifacts {
        add("archives", sourcesJar)
        add("archives", javadocJar)
    }

}

publishing {
    publications {
        create<MavenPublication>("projectMvnPublication") {
            version = artifactVersion
            groupId = artifactGroup
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("projectMvnPublication")
    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = "jneat"
        name = "mybatis-types"
        userOrg = System.getenv("BINTRAY_USER")
    })
}

// Tasks
//[compileJava, compileTestJava] * . options *.encoding = 'UTF-8'

//gradle.projectsEvaluated {
//    tasks.withType(JavaCompile) {
//        options.compilerArgs < < "-Xlint:unchecked" << "-Xlint:deprecation"
//    }
//}