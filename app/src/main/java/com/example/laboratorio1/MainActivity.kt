package com.example.laboratorio1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.compose.runtime.livedata.observeAsState
import com.example.laboratorio1.data.UserPreferences
import com.example.laboratorio1.ui.FormViewModel
import com.example.laboratorio1.ui.theme.Laboratorio1Theme
import androidx.lifecycle.createSavedStateHandle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio1Theme {
                val context = LocalContext.current

                val formViewModel: FormViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            FormViewModel(
                                this.createSavedStateHandle(),
                                UserPreferences(context)
                            )
                        }
                    }
                )

                ResilientFormScreen(viewModel = formViewModel)
            }
        }
    }
}

@Composable
fun ResilientFormScreen(viewModel: FormViewModel) {
    val nameDisk by viewModel.nameFromDisk.observeAsState("")
    var nameInput by remember { mutableStateOf(nameDisk) }

    LaunchedEffect(nameDisk) {
        nameInput = nameDisk
    }

    BackHandler(enabled = nameInput.isNotEmpty() || viewModel.email.isNotEmpty()) {
        Log.d("NAV", "El usuario intentó retroceder con datos en el formulario")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Borrador de Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = nameInput,
            onValueChange = {
                nameInput = it
                viewModel.saveName(it)
            },
            label = { Text("Nombre (Persiste al cerrar app)") },
            trailingIcon = {
                if (viewModel.showSavedIcon) {
                    Text("✅")
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email (Persiste al rotar/muerte proceso)") },
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}