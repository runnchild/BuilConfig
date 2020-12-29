package com.rongc.plugin.config

import org.gradle.api.Project

class Composite {
    static with(Project project) {
        project.with {
            new File('./').listFiles(new FilenameFilter() {
                @Override
                boolean accept(File file, String s) {
                    return s.charAt(0).isUpperCase() && !s.contains("Version")
                }
            }).each {
                def moduleName = it.name
                project.task {
                    "enable${moduleName}Composite"() {
                        group = 'Tools'
                        description = "Enable ${moduleName} composite build"
                        doLast {
                            println("create $moduleName .composite-enable file")
                            new File("${moduleName}/.composite-enable").createNewFile()
                        }
                    }
                }
                project.task {
                    "disable${moduleName}Composite"() {
                        group = 'Tools'
                        description = "Disable ${moduleName} composite build"
                        doLast {
                            println("delete .composite-enable file")
                            File file = new File("$moduleName/.composite-enable")
                            if (file.exists()) {
                                file.delete()
                            }
                        }
                    }
                }
            }
        }
    }
}