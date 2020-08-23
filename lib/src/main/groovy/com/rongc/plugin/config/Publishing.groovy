package com.rongc.plugin.config

import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.Project
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.publish.maven.MavenPublication

class Publishing {
    static void with(Project project) {
        project.with {
            if (findProperty("group_id") != null) {
                apply plugin: 'maven-publish'
                apply plugin: 'com.jfrog.bintray'

                def isJavaPlugin = project.plugins.findPlugin("java-library")

                afterEvaluate {
                    publishing {
                        publications {
                            // Creates a Maven publication called "release".
                            release(MavenPublication) {
                                // Applies the component for the release build variant.
                                from isJavaPlugin ? components.java : components.release
                                artifact androidSourcesJar
                                artifact androidJavadocsJar

                                // You can then customize attributes of the publication as shown below.
                                groupId = group_id
                                artifactId = module_name
                                version = repo_version
                            }
                            // Creates a Maven publication called “debug”.
//                            debug(MavenPublication) {
//                                // Applies the component for the debug build variant.
//                                from components.debug
//
//                                groupId = group_id
//                                artifactId = module_name
//                                version = repo_version
//                            }
                        }
                    }
                }

                bintray {
                    user = repo_userOrg
                    println("REPO_KEY=${System.getenv("REPO_KEY")}")

                    key = System.getenv("REPO_KEY")
                    dryRun = false
                    //[Default: false] Whether to run this as dry-run, without deploying
                    publish = true
                    //[Default: false] Whether version should be auto published after an upload
                    override = true
                    //[Default: false] Whether to override version artifacts already published
                    publications = ['release']
                    pkg {
                        repo = 'maven'
                        name = module_name
                        userOrg = repo_userOrg
                        licenses = ['Apache-2.0']
                        vcsUrl = repo_website

                        version {
                            name = repo_version
                            desc = repo_desc
                            released = new Date()
                            vcsTag = repo_version
                            attributes = ['gradle-plugin': 'com.use.less:com.use.less.gradle:gradle-useless-plugin']
                        }
                    }
                }

                tasks.create("androidJavadocs", Javadoc.class) {
                    failOnError = false
                    source = android.sourceSets.main.java.srcDirs
                    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))

                    android.libraryVariants.all { variant ->
                        if (variant.name == 'release') {
                            owner.classpath += variant.getJavaCompile().classpath
                        }
                    }

                    exclude '**/R.html', '**/R.*.html', '**/index.html'
                    options.encoding "UTF-8"
                    options.charSet "UTF-8"
                }

                tasks.create("androidJavadocsJar", Jar.class) {
                    archiveClassifier.set('javadoc')
                    from androidJavadocs.destinationDir
                }
                androidJavadocsJar.dependsOn androidJavadocs

                tasks.create("androidSourcesJar", Jar.class) {
                    archiveClassifier.set('sources')
                    from android.sourceSets.main.java.srcDirs
                }}
        }
    }
}