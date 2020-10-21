package com.hzrelaper.hospitalmachine.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.hzrelaper.hospitalmachine.data.LoginRepository
import com.hzrelaper.hospitalmachine.data.Result

import com.hzrelaper.hospitalmachine.R
import com.hzrelaper.hospitalmachine.data.entity.UserEntity
import com.hzrelaper.hospitalmachine.nettools.ApiClient
import com.hzrelaper.hospitalmachine.nettools.NetTool
import com.hzrelaper.hospitalmachine.nettools.int.UserServiceImp
import com.hzrelaper.hospitalmachine.url.StaticUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
        UserServiceImp().login(username,password,object: Callback<UserEntity?>{
            override fun onResponse(call: Call<UserEntity?>, response: Response<UserEntity?>) {
                val name = response.body()?.username
                var tt = name+""
                _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName =tt))
            }

            override fun onFailure(call: Call<UserEntity?>, t: Throwable) {
                _loginResult.value = LoginResult(error = R.string.net_error)
            }

        })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {

            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
//        } else if (!isPasswordValid(password)) {
//            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}