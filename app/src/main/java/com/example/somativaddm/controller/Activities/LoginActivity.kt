package com.example.somativaddm.controller.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.somativaddm.controller.AppModule
import com.example.somativaddm.controller.Game.GameDatabase
import com.example.somativaddm.controller.Game.GameRepository
import com.example.somativaddm.controller.Game.GameViewModel
import com.example.somativaddm.controller.User.Model.UserRepository
import com.example.somativaddm.controller.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    val userViewModel: MainViewModel by viewModels()
    val gameViewModel: GameViewModel by viewModels()
    @Inject lateinit var gameRepository: GameRepository
    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var gameDatabase: GameDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.refresh()
        val users = userRepository.users
        users.forEach{
            val text = "User: ${it.userName}, password: ${it.password}"
            Log.d("Users", text)
        }
        userViewModel.refresh()

        userRepository = AppModule().provideRepository(
            AppModule().provideDao(AppModule().provideDatabase(applicationContext))
        )
        gameRepository = AppModule().provideGameRepository(AppModule().providesGameDatabase(applicationContext))

        gameViewModel.refresh()

        gameDatabase = AppModule().providesGameDatabase(applicationContext)




        setContent{
            Surface {
                var login = remember {
                    mutableStateOf("")
                }
                var password = remember{
                    mutableStateOf("")
                }
                val context = LocalContext.current

                val coroutineScope = rememberCoroutineScope()
                userRepository = AppModule().provideRepository(AppModule().provideDao(AppModule().provideDatabase(context)))
                coroutineScope.launch {

                    gameRepository.createGamesDatabase()

                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                    ) {

                    Text(text = "VAPOR",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(2.dp))
                    LoginField(value = login.value, onChange = {login.value = it},
                        modifier = Modifier.fillMaxWidth())
                    PasswordField(value = password.value, onChange = {password.value = it},
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(2.dp))
                    Button(onClick = {
                        if(login.value.isNotEmpty() && password.value.isNotEmpty()) {
                            if(Login(login.value,password.value,userRepository)){
                                Toast.makeText(context,"Welcome ${login.value}", Toast.LENGTH_SHORT).show()
                                val allGamesIntent = Intent(context, AllGamesActivity::class.java)
                                startActivity(allGamesIntent)
                            }
                            else{
                                Toast.makeText(context,"Login invalid", Toast.LENGTH_SHORT).show()
                            }


                        }
                        else{
                            Toast.makeText(context,"One of the fields is blank", Toast.LENGTH_SHORT).show()
                        }

                    },
                        shape= RoundedCornerShape(5.dp),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text("Login")
                    }
                    Button(onClick = {
                        val intent = Intent(context, RegisterActivity::class.java)
                        startActivity(intent)
                    },
                        shape= RoundedCornerShape(5.dp),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text("Register")
                    }
                }
            }
        }
         
    }


}
fun Login(nickname:String, password:String, repository: UserRepository):Boolean{
    val users = repository.dao.getByName(nickname)
    users.forEach{
        if(nickname == it.userName && password == it.password){
             return true
        }
    }
    return false
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(value:String, onChange: (String) -> Unit,
               modifier: Modifier = Modifier,){


    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.height(5.dp))


    TextField(value = value, onValueChange = onChange,
        label = {Text("Password")},
        singleLine = true,
        placeholder = {Text("Password")},
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

@Composable
fun LoginField(value:String, onChange: (String) -> Unit,
               modifier: Modifier ){
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




