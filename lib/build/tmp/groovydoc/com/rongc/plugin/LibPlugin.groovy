package com.rongc.plugin

import com.rongc.plugin.config.Composite
import com.rongc.plugin.config.LibConfig
import com.rongc.plugin.config.Mvn
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.with {
            apply plugin: 'com.android.library'

            Mvn.with(target, true)
            Composite.with(target)
            LibConfig.with(target)
        }
    }
}