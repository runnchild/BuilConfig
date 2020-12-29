package com.rongc.plugin

import com.rongc.plugin.config.JavaConfig
import com.rongc.plugin.config.Publishing
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.with {
            JavaConfig.with(target)
            //Publishing.with(project)
        }
    }
}