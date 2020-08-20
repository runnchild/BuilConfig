package com.rongc.plugin

import com.rongc.plugin.config.Mvn
import com.rongc.plugin.config.JavaConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.with {
            JavaConfig.with(target)
            Mvn.with(target, false)
        }
    }
}