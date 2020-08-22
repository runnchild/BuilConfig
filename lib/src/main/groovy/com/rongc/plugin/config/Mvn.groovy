package com.rongc.plugin.config

import org.gradle.api.Project

class Mvn {
    static with(Project project, boolean isProject) {
        project.with {
            apply plugin: 'maven'
            apply plugin: 'maven-publish'

            uploadArchives {
                def repoPath = new File("${rootProject.projectDir}").parent
                doFirst {
                    println "file = $repoPath"
                    new File(repoPath, "repo/com/psnlove/$module_name").deleteDir()
                }
                repositories {
                    mavenDeployer {
                        repository(url: uri("$repoPath/repo/")) {
                            //            authentication(userName: deployUserName, password: deployPassword)
                        }

                        pom.project {
                            version = repo_version
                            groupId = group_id
                            artifactId = module_name
                            packaging = 'aar'
                        }
                    }
                }
            }

            project.task {
                upload(dependsOn: uploadArchives) {
                    doLast {
                        println "===task upload=${project.name}"
                    }
                }
            }
        }
    }
}