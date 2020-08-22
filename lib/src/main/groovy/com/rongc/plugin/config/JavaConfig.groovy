package com.rongc.plugin.config

import org.gradle.api.JavaVersion
import org.gradle.api.Project

class JavaConfig {
    static void with(Project project) {
        project.with {
            apply plugin: 'java-library'
            apply plugin: 'kotlin'
            apply plugin: 'kotlin-kapt'

            java {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            dependencies {
                implementation fileTree(dir: "libs", include: ["*.jar"])
                implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
            }
        }
    }
}