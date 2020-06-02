package io.github.chhabra_dhiraj.poempre.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.chhabra_dhiraj.poempre.domain.Poem
import io.github.chhabra_dhiraj.poempre.domain.User
import io.github.chhabra_dhiraj.poempre.network.GetUserAndPoemApiResponse
import io.github.chhabra_dhiraj.poempre.repository.AuthenticationRepository
import io.github.chhabra_dhiraj.poempre.repository.UserRepository
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import kotlinx.coroutines.*
import retrofit2.HttpException

class MainContentViewModel() : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val userRepository = UserRepository()

    private val authenticationRepository = AuthenticationRepository()

    private val _poems = MutableLiveData<List<Poem>>()

    val poems: LiveData<List<Poem>>
        get() = _poems

    private val _isLogoutSuccessful = MutableLiveData<Boolean>()

    val isLogoutSuccessful: LiveData<Boolean>
        get() = _isLogoutSuccessful

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getUserAndPoem() {
        viewModelScope.launch {
            var getUserAndPoemApiResponse: GetUserAndPoemApiResponse? = null
            withContext(Dispatchers.IO) {
                try {
                    getUserAndPoemApiResponse = userRepository.getUserAndPoems()
                } catch (he: HttpException) {
                    if (he.code() == 500) {
                        // Ask the user to retry on Splash Screen
                    }
                }
            }
            if (getUserAndPoemApiResponse != null) {
                if (getUserAndPoemApiResponse!!.isSuccessfull) {
                    _poems.value = getUserAndPoemApiResponse!!.poems
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authenticationRepository.logout()
                SharedPreferencesManager.instance!!.clear()
                withContext(Dispatchers.Main) {
                    _isLogoutSuccessful.value = true
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _isLogoutSuccessful.value = false
                }
            }
        }
    }
}