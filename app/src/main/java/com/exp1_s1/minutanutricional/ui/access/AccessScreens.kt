package com.exp1_s1.minutanutricional.ui.access

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onRecoverPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AccessLayout(title = "Bienvenido a Minuta Nutricional") {
        Text(
            text = "Ingresa para revisar el menú de esta semana.",
            style = MaterialTheme.typography.bodyLarge
        )
        EmailField(value = email, onValueChange = { email = it })
        PasswordField(value = password, onValueChange = { password = it })
        Button(onClick = onLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Ingresar")
        }
        TextButton(onClick = onRegister, modifier = Modifier.fillMaxWidth()) {
            Text("Crear una cuenta")
        }
        TextButton(onClick = onRecoverPassword, modifier = Modifier.fillMaxWidth()) {
            Text("Olvidé mi contraseña")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(onBackToLogin: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var preference by remember { mutableStateOf("Sin preferencia") }
    var preferenceExpanded by remember { mutableStateOf(false) }
    var householdSize by remember { mutableStateOf("1 a 2 personas") }
    var acceptsTerms by remember { mutableStateOf(false) }
    val preferences = listOf("Sin preferencia", "Vegetariana", "Baja en sal")
    val householdSizes = listOf("1 a 2 personas", "3 a 4 personas", "5 o más personas")

    AccessLayout(title = "Crea tu cuenta") {
        Text("Completa estos datos para comenzar.", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        EmailField(value = email, onValueChange = { email = it })
        PasswordField(value = password, onValueChange = { password = it })
        ExposedDropdownMenuBox(
            expanded = preferenceExpanded,
            onExpandedChange = { preferenceExpanded = it }
        ) {
            OutlinedTextField(
                value = preference,
                onValueChange = {},
                readOnly = true,
                label = { Text("Preferencia de alimentación") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = preferenceExpanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
            )
            ExposedDropdownMenu(expanded = preferenceExpanded, onDismissRequest = { preferenceExpanded = false }) {
                preferences.forEach { option ->
                    androidx.compose.material3.DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            preference = option
                            preferenceExpanded = false
                        }
                    )
                }
            }
        }
        Text("¿Para cuántas personas cocinas?", style = MaterialTheme.typography.titleMedium)
        householdSizes.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = householdSize == option, onClick = { householdSize = option })
                Text(option)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acceptsTerms, onCheckedChange = { acceptsTerms = it })
            Text("Acepto los términos de uso")
        }
        Button(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Registrarme")
        }
        TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al ingreso")
        }
    }
}

@Composable
fun RecoveryScreen(onBackToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var linkSent by remember { mutableStateOf(false) }

    AccessLayout(title = "Recupera tu contraseña") {
        Text(
            text = "Escribe tu correo. Te enviaremos un enlace para crear una nueva contraseña.",
            style = MaterialTheme.typography.bodyLarge
        )
        EmailField(value = email, onValueChange = { email = it })
        Button(onClick = { linkSent = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Enviar enlace")
        }
        if (linkSent) {
            Text(
                text = "El enlace fue enviado. Revisa tu correo electrónico.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al ingreso")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccessLayout(title: String, content: @Composable () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(title) }) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun EmailField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Correo electrónico") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true
    )
}

@Composable
private fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (value.isEmpty()) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true
    )
}
