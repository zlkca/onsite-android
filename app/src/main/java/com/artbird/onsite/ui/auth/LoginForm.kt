package com.artbird.onsite.ui.auth

//import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artbird.onsite.ui.components.*
import com.artbird.onsite.ui.theme.SLTheme


@Composable
fun LoginForm(
    password: String = "",
    email: String = "",
    error: Map<String, String> = mapOf<String, String>(),
    onChange: (name: String, value: String) -> Unit,
    onSubmit: (password: String, email:String) -> Unit,
    onPageChange: (page: String) -> Unit = {},
)
{
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ) {

        Box(
            modifier = Modifier.padding(top=30.dp, bottom = 30.dp)
        )

        Input(
            value = email,
            onValueChange = { onChange("email", it) },
            label = "Email",
        )

        if (error["email"] != "" ) {
            Text(
                text = if(error["email"] == "Email not found") "Email not found" else "",
                color = Color.Red,
//                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Input(
            value = password,
            onValueChange = { onChange("password", it) },
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
        )

        if (error["password"] != "" ) {
            Text(
                text = "Password not match",
                color = Color.Red,
//                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Text(
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(top=20.dp, start=8.dp)
                .clickable(enabled = true) {
                    onPageChange("signup")
                },
            text = "Signup as partner"
        )

        Column(
            Modifier
                .padding(6.dp),
        ){
            LongButton(
                "Sign in",
                onClick = {
                    onSubmit(
                        password,
                        email,
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .focusTarget()
            )
        }
    }
}

@Preview(
    name="Light Mode",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(showBackground = true)
@Composable
fun PreviewLoginForm(){
    SLTheme {
        LoginForm(
            onChange = { name, value -> },
            onSubmit = { password, email -> }
        )
    }
}