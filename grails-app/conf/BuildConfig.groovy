grails.project.work.dir = "target"

grails.project.dependency.resolution = {
    inherits("global")
    log "warn"
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        runtime 'org.jsoup:jsoup:1.6.2'
    }

    plugins {
        build(":tomcat:$grailsVersion") {
            export = false
        }


        build ':release:2.2.1', ':rest-client-builder:1.0.3', {
            export = false
        }

        runtime ":resources:1.1.6"
    }
}
