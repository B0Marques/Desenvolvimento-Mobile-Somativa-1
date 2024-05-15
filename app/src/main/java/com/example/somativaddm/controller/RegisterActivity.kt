package com.example.somativaddm.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.somativaddm.R
import com.example.somativaddm.controller.User.Model.User
import com.example.somativaddm.controller.User.Model.UserRepository
import com.example.somativaddm.controller.viewmodel.MainViewModel
import com.example.somativaddm.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity :  ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    @Inject lateinit var repository:UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh()


        setContent {
            Surface(color = Color.LightGray
                ) {
                var login = remember{
                    mutableStateOf("")
                }
                var password = remember {
                    mutableStateOf("")
                }
                var confirmPassword = remember{
                    mutableStateOf("")
                }

                    val context = LocalContext.current
                    repository = AppModule().provideRepository(
                        AppModule().provideDao(
                            AppModule().provideDatabase(
                                context
                            )
                        )
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp)
                    ) {
                        RegisterUsernameField(
                            value = login.value, onChange = { login.value = it }
                        )
                         
                        RegisterPasswordField(
                            value = password.value, onChange = { password.value = it},
                            text = "Password"
                        )
                        RegisterPasswordField(
                                value = confirmPassword.value, onChange = { confirmPassword.value = it},
                            text = "Confirm Password"
                        )



                        Spacer(modifier = Modifier.height(2.dp))
                        Button(
                            onClick = {
                                if(CheckCredentials(login.value,password.value,confirmPassword.value,repository,context)){
                                    val intent = Intent(context,LoginActivity::class.java)
                                    startActivity(intent)
                                }

                            },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            Text("Register")
                        }

                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp)

                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(context, LoginActivity::class.java)
                                startActivity(intent)
                            },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text("Back")
                        }
                    }
            }

        }

    }
}

fun CheckCredentials(name:String, password:String, confirmPassword:String, repository: UserRepository,context:Context):Boolean{
    if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        Toast.makeText(
            context,
            "One of the fields is blank",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }
    if(password != confirmPassword){
        Toast.makeText(
            context,
            "Passwords dont match",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }
    val users = repository.dao.getAll()
    users.forEach{
        if(it.userName == name){
            Toast.makeText(
                context,
                "User Already Exist",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    repository.add(User(users.size+1, userName = name, password = password))
    Toast.makeText(
        context,
        "User cadastred",
        Toast.LENGTH_SHORT
    ).show()
    return true
}

@Composable
fun RegisterUsernameField(value:String, onChange:(String) -> Unit){
    val focusManager = LocalFocusManager.current

    TextField(value = value,
        onValueChange = onChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down)}),
        placeholder = { Text(text = "User Name")},
        label = { Text(text = "User Name")},
        singleLine = true,
        visualTransformation = VisualTransformation.None
        )
}

@Composable
fun RegisterPasswordField(value:String, onChange:(String) -> Unit, text:String){
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextField(value = value, onValueChange = onChange,
        label = {Text(text)},
        singleLine = true,
        placeholder = { Text(text = text)},
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if(passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if(passwordVisible) "Hide Password" else "Show Password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description )
            }
        }

    )

}
