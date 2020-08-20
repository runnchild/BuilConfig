package com.rongc.plugin

import com.rongc.plugin.config.LibConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

class AppPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {
            apply plugin: 'com.android.application'
            LibConfig.with(project)
            android {
                defaultConfig {
                    applicationId findProperty('APPLICATION_ID')
                }
            }
        }
    }
}
