package com.rongc.plugin


import com.rongc.plugin.config.LibConfig
import com.rongc.plugin.ext.KeyStoreExt
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
//        def ext = project.extensions.create("signingConfig", KeyStoreExt)
//        project.afterEvaluate {
//            project.android.defaultConfig.setSigningConfig(ext)
//            println("signingConfig==> ${project.android.defaultConfig.signingConfigs}")
//        }
        project.with {
            apply plugin: 'com.android.application'
            LibConfig.with(project)
            android {
                defaultConfig {
                    applicationId findProperty('APPLICATION_ID')
                    resConfigs "zh"
                }
                buildTypes {
                    release {
                        minifyEnabled true
                        shrinkResources true
                        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                    }
//                    canary {
//                        initWith debug
//                    }
                }
            }
            dependencies {
                debugImplementation "com.squareup.leakcanary:leakcanary-android:2.4"
                implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
            }
        }
    }
}
