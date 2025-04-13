package com.amd.pokedexapp.presentation.page.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amd.pokedexapp.presentation.theme.Roboto
import com.amd.pokedexapp.utils.showToast

@Composable
fun LoginPage(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val loginSuccessful by viewModel.loginSuccess.collectAsState()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(loginSuccessful) {
        if (loginSuccessful) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loginError.collect { message ->
            context.showToast(message)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            RoundedTextField(
                label = "Username",
                hint = "Input your username",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Text,
                onChange = { username = it },
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoundedTextField(
                label = "Password",
                hint = "Input your password",
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password,
                onChange = { password = it },
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.login(username, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Login", color = Color.White)
            }
        }
    }
}

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation? = null,
    onChange: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(text = label, color = Color.Black, fontFamily = Roboto)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onChange.invoke(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = visualTransformation ?: VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .onFocusChanged {
                        isHintDisplayed = !it.isFocused
                    }
            )
            if (isHintDisplayed && text.isEmpty()) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage(onLoginSuccess = {})
}