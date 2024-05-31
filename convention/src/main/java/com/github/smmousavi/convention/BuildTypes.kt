package com.github.smmousavi.convention

enum class BuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}