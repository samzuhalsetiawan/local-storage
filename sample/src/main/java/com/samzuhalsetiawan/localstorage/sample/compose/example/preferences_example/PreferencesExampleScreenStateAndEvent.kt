package com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example

data class PreferencesExampleScreenState(
    val preferences: Map<String, Any> = emptyMap(),
    val dialogState: PreferencesInputDialogState = PreferencesInputDialogState()
)

data class PreferencesInputDialogState(
    val preferencesKey: String = "",
    val preferencesValue: Any? = null,
    val selectedType: PreferencesType = PreferencesType.STRING
)

sealed interface PreferencesExampleScreenEvent {
    data class OnAddPreference<T: Any>(val key: String, val value: T): PreferencesExampleScreenEvent
    data class OnRemovePreference(val key: String): PreferencesExampleScreenEvent
    data class OnEditPreference<T: Any>(val key: String, val value: T): PreferencesExampleScreenEvent
    data class OnPreferencesKeyChange(val key: String): PreferencesExampleScreenEvent
    data class OnPreferencesValueChange<T: Any>(val value: T): PreferencesExampleScreenEvent
    data class OnSelectedTypeChange(val type: PreferencesType): PreferencesExampleScreenEvent
}

sealed interface PreferencesExampleScreenNavigationEvent {
    data object NavigateBack: PreferencesExampleScreenNavigationEvent
}