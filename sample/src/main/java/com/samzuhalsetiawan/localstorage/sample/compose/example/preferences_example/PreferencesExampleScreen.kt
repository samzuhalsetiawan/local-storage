package com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.component.PreferencesInputDialog

@Composable
fun PreferencesExampleScreen(
    viewModel: PreferencesExampleScreenViewModel,
    onNavigationEvent: (PreferencesExampleScreenNavigationEvent) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PreferencesExampleScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigationEvent = onNavigationEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreferencesExampleScreen(
    state: PreferencesExampleScreenState,
    onEvent: (PreferencesExampleScreenEvent) -> Unit,
    onNavigationEvent: (PreferencesExampleScreenNavigationEvent) -> Unit
) {
    var showAddPreferenceDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Preferences Example",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigationEvent(PreferencesExampleScreenNavigationEvent.NavigateBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddPreferenceDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Preference",
                )
            }
        }
    ) { scaffoldPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(scrollState)
        ) {
            for (preference in state.preferences) {
                ListItem(
                    leadingContent = {
                        IconButton(
                            onClick = {
                                showAddPreferenceDialog = true
                                onEvent(PreferencesExampleScreenEvent.OnPreferencesKeyChange(preference.key))
                                onEvent(PreferencesExampleScreenEvent.OnPreferencesValueChange(preference.value))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    headlineContent = {
                        Text(text = "${preference.key}: ${preference.value}")
                    },
                    trailingContent = {
                        IconButton(
                            onClick = {
                                onEvent(PreferencesExampleScreenEvent.OnRemovePreference(preference.key))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
    if (showAddPreferenceDialog) {
        PreferencesInputDialog(
            preferencesKey = state.dialogState.preferencesKey,
            preferencesValue = state.dialogState.preferencesValue,
            selectedType = state.dialogState.selectedType,
            onPreferencesKeyChange = {
                onEvent(PreferencesExampleScreenEvent.OnPreferencesKeyChange(it))
            },
            onPreferencesValueChange = {
                if (it == null) return@PreferencesInputDialog
                onEvent(PreferencesExampleScreenEvent.OnPreferencesValueChange(it))
            },
            onSelectedTypeChange = {
                onEvent(PreferencesExampleScreenEvent.OnSelectedTypeChange(it))
            },
            onSavePreferences = { key, value ->
                onEvent(PreferencesExampleScreenEvent.OnAddPreference(key, value))
            },
            onDismissRequest = {
                showAddPreferenceDialog = false
            }
        )
    }
}

enum class PreferencesType {
    STRING, INT, BOOLEAN
}
