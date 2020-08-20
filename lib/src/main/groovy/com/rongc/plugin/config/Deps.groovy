package com.rongc.plugin.config

interface Deps {
    def ModulePath = "../../module.gradle"
    def BuildPath = "Common/build.gradle"
    def gradlePlugin = "com.android.tools.build:gradle:4.2.0-alpha04"

    interface Lib {
        String retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        String Gson = 'com.google.code.gson:gson:2.8.6'
    }
}