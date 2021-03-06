package io.github.chhabra_dhiraj.poempre.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.chhabra_dhiraj.poempre.domain.User
import io.github.chhabra_dhiraj.poempre.network.RegisterApiResponse
import io.github.chhabra_dhiraj.poempre.repository.AuthenticationRepository
import io.github.chhabra_dhiraj.poempre.repository.UserRepository
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class AuthenticationViewModel : ViewModel() {

    private lateinit var email: String

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val authenticationRepository = AuthenticationRepository()

    private val _isEmailPresent = MutableLiveData<Boolean>()

    val isEmailPresent: LiveData<Boolean>
        get() = _isEmailPresent

    private val _isLoginSuccessful = MutableLiveData<Boolean>()

    val isLoginSuccessful: LiveData<Boolean>
        get() = _isLoginSuccessful

    private val _isRegisterSuccessful = MutableLiveData<RegisterApiResponse>()

    val isRegisterSuccessful: LiveData<RegisterApiResponse>
        get() = _isRegisterSuccessful

    fun checkEmailPresent(email: String) {
        this.email = email
        viewModelScope.launch {
            val isEmailPresent = authenticationRepository.checkEmailPresent(email)
            withContext(Dispatchers.Main) {
                _isEmailPresent.value = isEmailPresent
            }
        }
    }

    fun login(password: String) {
        viewModelScope.launch {
            try {
                val loginApiResponse = authenticationRepository.login(email, password)

                val sessionIdHeader = loginApiResponse.headers()["Set-Cookie"]?.substring(12)
                SharedPreferencesManager.instance!!.apply {
                    sessionId = sessionIdHeader
                    userId = loginApiResponse.body()?.user!!.userId
                    email = loginApiResponse.body()?.user!!.email
                    firstName = loginApiResponse.body()?.user!!.firstname
                    lastName = loginApiResponse.body()?.user!!.lastname
                }
                withContext(Dispatchers.Main) {
                    _isLoginSuccessful.value = true
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _isLoginSuccessful.value = false
                }
            }

        }
    }

    fun register(firstName: String, lastName: String, password: String) {
        viewModelScope.launch {
            try {
                val registerApiResponse =
                    authenticationRepository.register(email, firstName, lastName, password)
                val sessionIdHeader = registerApiResponse.headers()["Set-Cookie"]?.substring(12)
                SharedPreferencesManager.instance!!.apply {
                    sessionId = sessionIdHeader
                    userId = registerApiResponse.body()?.user!!.userId
                    email = registerApiResponse.body()?.user?.email
                    this.firstName = registerApiResponse.body()?.user?.firstname
                    this.lastName = registerApiResponse.body()?.user?.lastname
                }
                withContext(Dispatchers.Main) {
                    _isRegisterSuccessful.value = registerApiResponse.body()
                }
            } catch (he: HttpException) {

            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}