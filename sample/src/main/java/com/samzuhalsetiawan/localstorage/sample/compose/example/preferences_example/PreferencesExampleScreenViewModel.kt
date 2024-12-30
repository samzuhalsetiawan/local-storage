package com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samzuhalsetiawan.localstorage.LocalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreferencesExampleScreenViewModel(
    private val localStorage: LocalStorage
): ViewModel() {

    private val _state = MutableStateFlow(PreferencesExampleScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: PreferencesExampleScreenEvent) {
        when (event) {
            is PreferencesExampleScreenEvent.OnAddPreference<*> -> onAddPreference(event.key, event.value)
            is PreferencesExampleScreenEvent.OnEditPreference<*> -> onEditPreference(event.key, event.value)
            is PreferencesExampleScreenEvent.OnRemovePreference -> onRemovePreference(event.key)
            is PreferencesExampleScreenEvent.OnPreferencesKeyChange -> onPreferencesKeyChange(event.key)
            is PreferencesExampleScreenEvent.OnPreferencesValueChange<*> -> onPreferencesValueChange(event.value)
            is PreferencesExampleScreenEvent.OnSelectedTypeChange -> onSelectedTypeChange(event.type)
        }
    }

    private fun onSelectedTypeChange(type: PreferencesType) {
        _state.update { it.copy(dialogState = it.dialogState.copy(selectedType = type)) }
    }

    private fun <T> onPreferencesValueChange(value: T) {
        _state.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    preferencesValue = value
                )
            )
        }
    }

    private fun onPreferencesKeyChange(key: String) {
        _state.update { it.copy(dialogState = it.dialogState.copy(preferencesKey = key)) }
    }

    private fun onRemovePreference(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            localStorage.removePreferences(key)
        }
    }

    private fun <T: Any> onEditPreference(key: String, value: T) {
        viewModelScope.launch(Dispatchers.IO) {
            localStorage.savePreferences(key, value)
        }
    }

    private fun <T: Any> onAddPreference(key: String, value: T) {
        viewModelScope.launch(Dispatchers.IO) {
            localStorage.savePreferences(key, value)
        }
    }

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                localStorage.preferencesAsFlow().collect { preferences ->
                    _state.update { it.copy(preferences = preferences) }
                }
            }
        }
    }

}