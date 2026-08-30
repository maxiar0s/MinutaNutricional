package com.exp1_s1.minutanutricional.ui.access

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.exp1_s1.minutanutricional.data.LoginResult
import com.exp1_s1.minutanutricional.data.RecoveryResult
import com.exp1_s1.minutanutricional.data.RegistrationResult

@Composable
fun LoginScreen(
    onLogin: (String, String) -> LoginResult,
    onSuccessfulLogin: () -> Unit,
    onRegister: () -> Unit,
    onRecoverPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    AccessLayout(title = "Bienvenido a Minuta Nutricional") {
        Text(
            text = "Ingresa para revisar el menú de esta semana.",
            style = MaterialTheme.typography.bodyLarge
        )
        EmailField(value = email, onValueChange = { email = it })
        PasswordField(value = password, onValueChange = { password = it })
        Button(
            onClick = {
                when (onLogin(email, password)) {
                    LoginResult.Success -> onSuccessfulLogin()
                    LoginResult.EmptyFields -> message = "Completa el correo y la contraseña."
                    LoginResult.InvalidCredentials -> message = "El correo o la contraseña no son correctos."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
        message?.let { StatusMessage(it) }
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
fun RegistrationScreen(
    onRegister: (String, String, String) -> RegistrationResult,
    onBackToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var preference by remember { mutableStateOf("Sin preferencia") }
    var preferenceExpanded by remember { mutableStateOf(false) }
    var householdSize by remember { mutableStateOf("1 a 2 personas") }
    var acceptsTerms by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
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
        Button(
            onClick = {
                if (!acceptsTerms) {
                    message = "Debes aceptar los términos de uso para registrarte."
                } else {
                    message = when (onRegister(name, email, password)) {
                        RegistrationResult.Success -> "Cuenta creada. Ahora puedes volver e ingresar."
                        RegistrationResult.EmptyFields -> "Completa nombre, correo y contraseña."
                        RegistrationResult.InvalidEmail -> "Escribe un correo electrónico válido."
                        RegistrationResult.WeakPassword -> "La contraseña debe tener al menos 6 caracteres."
                        RegistrationResult.DuplicateEmail -> "Este correo ya está registrado."
                        RegistrationResult.CapacityReached -> "Se alcanzó el máximo local de 5 usuarios."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarme")
        }
        message?.let { StatusMessage(it) }
        TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al ingreso")
        }
    }
}

@Composable
fun RecoveryScreen(
    onRecoverPassword: (String) -> RecoveryResult,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    AccessLayout(title = "Recupera tu contraseña") {
        Text(
            text = "Escribe tu correo para comprobar si está registrado en este dispositivo.",
            style = MaterialTheme.typography.bodyLarge
        )
        EmailField(value = email, onValueChange = { email = it })
        Button(onClick = {
            message = when (onRecoverPassword(email)) {
                RecoveryResult.Ready -> "Correo registrado. Por seguridad, solicita ayuda a la persona que creó la cuenta."
                RecoveryResult.EmptyEmail -> "Escribe tu correo electrónico."
                RecoveryResult.InvalidEmail -> "Escribe un correo electrónico válido."
                RecoveryResult.UnregisteredEmail -> "No encontramos una cuenta con este correo en este dispositivo."
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Comprobar correo")
        }
        message?.let { StatusMessage(it) }
        TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al ingreso")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccessLayout(title: String, content: @Composable () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text(title) }) }) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val horizontalPadding = if (maxWidth < 600.dp) 24.dp else 72.dp
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = horizontalPadding, vertical = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                content()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun StatusMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.semantics { contentDescription = "Mensaje: $message" }
    )
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
