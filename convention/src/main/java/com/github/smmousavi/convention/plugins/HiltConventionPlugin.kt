package com.github.smmousavi.convention.plugins

import com.github.smmousavi.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                pluginManager.apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt.android").get())
                "kapt"(libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}
