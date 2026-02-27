package com.example.na_steptracker.domain.model

import java.util.Locale

enum class AppLanguage(
    val displayName: String,
    val isoCode: String,
    val locale: Locale
) {
    RU(
        displayName = "Русский",
        locale = Locale("ru"),
        isoCode = "ru",
    ),
    EN(
        displayName = "English",
        locale = Locale.ENGLISH,
        isoCode = "en",
    );

    companion object {
        fun fromDisplayName(name: String): AppLanguage {
            return entries.find { it.displayName == name }?: AppLanguage.RU
        }
        fun fromIsoCode(iso: String): AppLanguage {
            return entries.find { it.isoCode == iso }?: AppLanguage.RU
        }
    }
}
