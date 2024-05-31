package com.github.smmousavi.convention.plugins

import androidx.room.gradle.RoomExtension
import com.github.smmousavi.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kotlin-kapt")
            pluginManager.apply("androidx.room")

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                add("implementation", libs.findLibrary("room.runtime").get())
                add("implementation", libs.findLibrary("room.ktx").get())
                add("kapt", libs.findLibrary("room.compiler").get())
            }
        }
    }
}