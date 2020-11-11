package com.rongc.plugin.config

import org.gradle.api.Project

class LibConfig {

    static with(Project project) {
        project.with {
            apply plugin: 'kotlin-android'
            apply plugin: 'kotlin-android-extensions'
            apply plugin: 'kotlin-kapt'

            android {
                compileSdkVersion 30

                defaultConfig {
                    minSdkVersion 21
                    targetSdkVersion 30
                    versionCode 1
                    versionName "1.0"
                    consumerProguardFiles 'proguard-rules.pro'
                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled false
                        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                    }
                }
                buildFeatures {
                    dataBinding = true
                    viewBinding = false
                }
                packagingOptions {
                    exclude 'META-INF/*.kotlin_module'
                    exclude 'META-INF/xxx'
                }
                androidExtensions {
                    experimental = true
                }
                dexOptions {
                    preDexLibraries true
                    maxProcessCount 8
                }
                compileOptions {
                    sourceCompatibility '1.8'
                    targetCompatibility '1.8'
                }
                kotlinOptions {
                    jvmTarget = '1.8'
                }
                kapt {
                    useBuildCache = true
                    javacOptions {
                        option("-Xmaxerrs", 500)
                    }
                    arguments {
                        arg("MODULE_NAME", project.module_name)
                        arg("PROJECT_NAME", project.name)
                    }
                }
                sourceSets {
                    main {
                        jniLibs.srcDirs = ['libs']
                    }
                }
            }

            dependencies {
                implementation fileTree(dir: "libs", include: ["*.jar"])
                compileOnly "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"

                implementation "androidx.multidex:multidex:2.0.1"
                implementation 'androidx.activity:activity:1.1.0'
                implementation 'androidx.fragment:fragment-ktx:1.2.5'
                implementation 'androidx.fragment:fragment:1.2.5'
                implementation 'androidx.core:core-ktx:1.3.1'
                implementation 'androidx.appcompat:appcompat:1.2.0'
                implementation 'androidx.constraintlayout:constraintlayout:2.0.0'
                implementation 'com.google.android.material:material:1.3.0-alpha02'
                testImplementation 'junit:junit:4.13'
                androidTestImplementation 'androidx.test.ext:junit:1.1.1'
                androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
            }
        }
    }
}