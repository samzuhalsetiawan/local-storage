package com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.PreferencesExampleScreenEvent
import com.samzuhalsetiawan.localstorage.sample.compose.example.preferences_example.PreferencesType

@Composable
fun PreferencesInputDialog(
    preferencesKey: String = "",
    preferencesValue: Any? = null,
    selectedType: PreferencesType = PreferencesType.STRING,
    onPreferencesKeyChange: (String) -> Unit,
    onPreferencesValueChange: (Any?) -> Unit,
    onSelectedTypeChange: (PreferencesType) -> Unit,
    onSavePreferences: (String, Any) -> Unit,
    onDismissRequest: () -> Unit,
) {
    LaunchedEffect(selectedType) {
        onPreferencesValueChange(null)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Add new preferences")
        },
        text = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var showDropdown by remember { mutableStateOf(false) }

                    Text(text = "Type: ")
                    Column {
                        TextButton(
                            onClick = {
                                showDropdown = true
                            }
                        ) {
                            Text(text = selectedType.name)
                        }
                        DropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = {
                                showDropdown = false
                            }
                        ) {
                            PreferencesType.entries.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(it.name)
                                    },
                                    onClick = {
                                        onSelectedTypeChange(it)
                                        showDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }
                OutlinedTextField(
                    value = preferencesKey,
                    onValueChange = {
                        onPreferencesKeyChange(it)
                    },
                    label = {
                        Text(text = "Key")
                    }
                )
                when (selectedType) {
                    PreferencesType.STRING -> {
                        OutlinedTextField(
                            value = (preferencesValue as? String) ?: "",
                            onValueChange = {
                                onPreferencesValueChange(it)
                            },
                            label = {
                                Text(text = "String Value")
                            }
                        )
                    }
                    PreferencesType.INT -> {
                        OutlinedTextField(
                            value = (preferencesValue as? Int)?.toString() ?: "",
                            onValueChange = {
                                val value = try {
                                    it.toInt()
                                } catch (_: Throwable) {
                                    0
                                }
                                onPreferencesValueChange(value)
                            },
                            label = {
                                Text(text = "Int Value")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }
                    PreferencesType.BOOLEAN -> {
                        Column {
                            Text(text = "Boolean Value")
                            Row {
                                Row(
                                    modifier = Modifier
                                        .clickable(
                                            role = Role.RadioButton,
                                            onClick = { onPreferencesValueChange(true) }
                                        )
                                        .selectableGroup(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = try {
                                            preferencesValue as Boolean
                                        } catch (_: Throwable) {
                                            false
                                        },
                                        onClick = null
                                    )
                                    Text(text = "True")
                                }
                                Row(
                                    modifier = Modifier
                                        .clickable(
                                            role = Role.RadioButton,
                                            onClick = { onPreferencesValueChange(false) }
                                        )
                                        .selectableGroup(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = try {
                                            !(preferencesValue as Boolean)
                                        } catch (_: Throwable) {
                                            false
                                        },
                                        onClick = null
                                    )
                                    Text(text = "False")
                                }
                            }
                        }
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                enabled = preferencesValue != null && preferencesKey.isNotBlank(),
                onClick = {
                    onSavePreferences(preferencesKey, preferencesValue!!)
                    onDismissRequest()
                }
            ) {
                Text(text = "Save")
            }
        },
    )
}