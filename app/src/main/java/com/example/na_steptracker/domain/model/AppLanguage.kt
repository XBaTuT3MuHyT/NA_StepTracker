package com.example.na_steptracker.domain.model

import java.util.Locale

enum class AppLanguage(
    val displayName: String,
    val locale: Locale
) {
    RU(
        displayName = "Русский",
        locale = Locale("ru")
    ),
    EN(
        displayName = "English",
        locale = Locale.ENGLISH
    );

    companion object {
        fun fromDisplayName(name: String): AppLanguage {
            return entries.find { it.displayName == name }?: AppLanguage.RU
        }
    }
}
