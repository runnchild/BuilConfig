package com.rongc.plugin.config

import org.gradle.api.Project
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.javadoc.Javadoc

class Publishing {
    static void with(Project project) {
        project.with {
            if (findProperty("group_id") == null) {
                return
            }
            apply plugin: 'maven-publish'

            def isJavaPlugin = project.plugins.findPlugin("java-library")

            afterEvaluate {
                publishing {
                    publications {
                        // Creates a Maven publication called "release".
                        release(MavenPublication) {
                            // Applies the component for the release build variant.
                            from isJavaPlugin ? components.java : components.release
                            artifact isJavaPlugin ? sourcesJar : androidSourcesJar
                            artifact isJavaPlugin ? javadocJar : androidJavadocsJar

                            // You can then customize attributes of the publication as shown below.
                            groupId = group_id
                            artifactId = module_name
                            version = repo_version

                            //The publication doesn't know about our dependencies, so we have to manually add them to the pom
//                            pom.withXml {
//                                // for dependencies and exclusions
//                                def dependenciesNode = asNode().appendNode('dependencies')
//                                configurations.implementation.allDependencies.withType(ModuleDependency) { ModuleDependency dp ->
//                                    def dependencyNode = dependenciesNode.appendNode('dependency')
//                                    dependencyNode.appendNode('groupId', dp.group)
//                                    dependencyNode.appendNode('artifactId', dp.name)
//                                    dependencyNode.appendNode('version', dp.version)
//
//                                    // for exclusions
//                                    if (dp.excludeRules.size() > 0) {
//                                        def exclusions = dependencyNode.appendNode('exclusions')
//                                        dp.excludeRules.each { ExcludeRule ex ->
//                                            println("dp.excludeRules = ${ex.metaPropertyValues}")
//
//                                            def exclusion = exclusions.appendNode('exclusion')
//                                            exclusion.appendNode('groupId', ex.group)
//                                            exclusion.appendNode('artifactId', ex.module)
//                                        }
//                                    }
//                                }
//                            }
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

            def repoUserOrg = findProperty("repo_userOrg")
//            try {
//                project.dependencies.each {
////        def v = it.module("com.jfrog.bintray.gradle:gradle-bintray-plugin").name
//                    try {
//                        def v = it.module("com.jfrog.bintray.gradle:gradle-android-plugin-aspectjx").name
//                        println("each = $v")
//                    } catch(Exception e) {
//                        println("Exception each = $e")
//                    }
//
//                }
//                project.dependencies.module("com.jfrog.bintray.gradle:gradle-bintray-plugin")
//                if (repoUserOrg == null || repoUserOrg == "") {
//                    println("需要在gradle.properties里添加仓库的基本信息，eg： repo_userOrg")
//                }
//                println("each = $v")
//            } catch (Exception ignore) {
//                if (repoUserOrg != null) {
//                    println("如果需要发布到Jcenter的能力，需要在根目录的build.gradle下引入gradle-bintray-plugin插件")
//                }
//                repoUserOrg = ""
//            }
            if (repoUserOrg != null && repoUserOrg != "") {
                apply plugin: 'com.jfrog.bintray'
                bintray {
                    user = repoUserOrg
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
                        userOrg = repoUserOrg
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
            }

            if (isJavaPlugin) {
                tasks.create("sourcesJar", Jar.class) {
                    from project.sourceSets.main.java.srcDirs
                    archiveClassifier.set('sources')
                }
                tasks.create("javadocJar", Jar.class) {
                    archiveClassifier.set('javadoc')
                    from javadoc.destinationDir
                }
                javadocJar.dependsOn javadoc
            } else {
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
                }
            }
        }
    }
}