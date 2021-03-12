package com.rongc.plugin.config

import org.gradle.api.Project

class LibConfig {

    static with(Project project) {
        project.with {
            apply plugin: 'kotlin-android'
            apply plugin: 'kotlin-parcelize'
            apply plugin: 'kotlin-kapt'

            android {
                compileSdkVersion 30
                def code = (findProperty("VERSION_CODE") ?: 1) as Integer
                def name = (findProperty("VERSION_NAME") ?: repo_version).toString()
                defaultConfig {
                    minSdkVersion 21
                    targetSdkVersion 30
                    versionCode code
                    versionName name
                    multiDexEnabled true
                    consumerProguardFiles 'proguard-rules.pro'
                    testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    release {
                        minifyEnabled true
                        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                    }
                }
                buildFeatures {
                    dataBinding true
                }
//                packagingOptions {
//                    exclude 'META-INF/*.kotlin_module'
//                    exclude 'META-INF/xxx'
//                }
                compileOptions {
                    kotlinOptions.freeCompilerArgs += ['-module-name', "${group_id}.${module_name}.${project.name}"]
                }
//                androidExtensions {
//                    experimental = true
//                }
//                dexOptions {
//                    preDexLibraries true
//                    maxProcessCount 8
//                }
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

                lintOptions {
                    checkReleaseBuilds false
                    // Or, if you prefer, you can continue to check for errors in release builds,
                    // but continue the build even when errors are found:
//                    abortOnError false
                }
            }

            dependencies {
                implementation fileTree(dir: "libs", include: ["*.jar"])
                compileOnly "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"

                implementation "androidx.multidex:multidex:2.0.1"
                implementation 'androidx.activity:activity:1.1.0'
                implementation 'androidx.activity:activity-ktx:1.1.0'
                implementation 'androidx.fragment:fragment-ktx:1.2.5'
                implementation 'androidx.core:core-ktx:1.3.2'
                implementation 'androidx.appcompat:appcompat:1.2.0'
                implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
                implementation 'com.google.android.material:material:1.3.0-beta01'
                testImplementation 'junit:junit:4.13.2'
                androidTestImplementation 'androidx.test.ext:junit:1.1.2'
                androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
            }
        }
    }
}