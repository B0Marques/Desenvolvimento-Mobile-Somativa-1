package com.example.somativaddm.controller

import android.content.Context
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.somativaddm.controller.User.Model.User
import com.example.somativaddm.controller.User.Model.UserRepository
import com.example.somativaddm.controller.User.Repository.UserDatabase
import com.example.somativaddm.controller.User.UserDTO
import com.example.somativaddm.controller.User.UserService
import com.example.somativaddm.controller.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()
    @Inject lateinit var repository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh()
        setContent{
            Surface {
                var login = remember {
                    mutableStateOf("")
                }
                var password = remember{
                    mutableStateOf("")
                }
                val context = LocalContext.current
                //val service = UserService(db,context)

                val coroutineScope = rememberCoroutineScope()
                repository = AppModule().provideRepository(AppModule().provideDao(AppModule().provideDatabase(context)))
                repository.add(User(1,"Clebinho","1234"))


                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)
                    ) {
                    LoginField(value = login.value, onChange = {login.value = it},
                        modifier = Modifier.fillMaxWidth())
                    PasswordField(value = password.value, onChange = {password.value = it},
                        modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(2.dp))
                    Button(onClick = {
                        if(login.value.isNotEmpty() && password.value.isNotEmpty()) {
                            if(Login(login.value,password.value,repository)){
                                Toast.makeText(context,"Welcome ${login.value}", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context,"Login invalid", Toast.LENGTH_SHORT).show()
                            }

                            /*
                            coroutineScope.launch {
                                withContext(Dispatchers.IO){
                                    try{
                                        //makeLogin().checkLogin(login.value,password.value,service)

                                    }
                                    catch (e:Exception){
                                        Log.w("DEBUG", "LOGIN ERROR")
                                    }
                                }
                            }
                            */
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
                        val intent = Intent(context,RegisterActivity::class.java)
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
class makeLogin:ViewModel(){

    public suspend fun checkLogin(username:String, userPassword:String, service: UserService){
        withContext(Dispatchers.IO) {
            val user: UserDTO? = service.findByUserName(username)
            if (user != null) {
                Log.d("DEBUG", "FIND USER")
                if (user.password == userPassword) {
                    Log.d("DEBUG", "Welcome, ${username}")

                } else {
                    Log.d("DEBUG", "Wrong password")
                }

            } else {

                Log.d("DEBUG", "CANT FIND USER")

            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(value:String, onChange: (String) -> Unit,
               modifier: Modifier = Modifier,){


    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.height(40.dp))


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
               modifier: Modifier = Modifier){
    val focusManager = LocalFocusManager.current


    TextField(value = value,
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down)}),
        placeholder = { Text(text = "User Name")},
        label = { Text(text = "User Name")},
        singleLine = true,
        visualTransformation = VisualTransformation.None
        )
}

