import java.io.ByteArrayOutputStream

plugins {
    `maven-publish`
}

allprojects {
    group = "net.tomatentum.Marinara"
    version = "1.0.0-RC1" + (if (!project.hasProperty("release")) ("-" + getGitHash()) else "")
    description = "A simple but powerful, library-agnostic Discord Interaction Wrapper."
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")

    plugins.withType<MavenPublishPlugin> {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])
                }
            }

            repositories {
                maven {
                    name = "Gitea"
                    url = uri("https://git.tomatentum.net/api/packages/tueem/maven/")

                    credentials(HttpHeaderCredentials::class) {
                        name = "Authorization"
                        value = "token " + System.getenv("GITEA_TOKEN")
                    }
                    authentication {
                        create<HttpHeaderAuthentication>("header")
                    }
                }
            }
        }
    }
}

fun getGitHash(): String {
    val output = ByteArrayOutputStream()
    project.exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = output
    }
    return output.toString().trim()
}