package io.github.chhabra_dhiraj.poempre.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.chhabra_dhiraj.poempre.domain.Poem
import io.github.chhabra_dhiraj.poempre.domain.Sentence
import io.github.chhabra_dhiraj.poempre.repository.AuthenticationRepository
import io.github.chhabra_dhiraj.poempre.repository.PoemRepository
import io.github.chhabra_dhiraj.poempre.repository.SuggestionRepository
import io.github.chhabra_dhiraj.poempre.repository.UserRepository
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import io.github.chhabra_dhiraj.poempre.utils.SingleClickEvent
import kotlinx.coroutines.*
import retrofit2.HttpException

class AppInsideViewModel : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val userRepository = UserRepository()

    private val poemRepository = PoemRepository()

    private val suggestionRepository = SuggestionRepository()

    private val _isDeleteSuccessful = MutableLiveData<SingleClickEvent<Boolean>>()

    val isDeleteSuccessful: LiveData<SingleClickEvent<Boolean>>
        get() = _isDeleteSuccessful

    private val _isUpdateSuccessful = MutableLiveData<SingleClickEvent<Boolean>>()

    val isUpdateSuccessful: LiveData<SingleClickEvent<Boolean>>
        get() = _isUpdateSuccessful

    private val _isUserDeleteSuccessful = MutableLiveData<SingleClickEvent<Boolean>>()

    val isUserDeleteSuccessful: LiveData<SingleClickEvent<Boolean>>
        get() = _isUserDeleteSuccessful

    private val _isUserUpdateSuccessful = MutableLiveData<SingleClickEvent<Boolean>>()

    val isUserUpdateSuccessful: LiveData<SingleClickEvent<Boolean>>
        get() = _isUserUpdateSuccessful

    private val _isCreateSuccessful = MutableLiveData<SingleClickEvent<Boolean>>()

    val isCreateSuccessful: LiveData<SingleClickEvent<Boolean>>
        get() = _isCreateSuccessful

    private val _sentences = MutableLiveData<List<Sentence>>()

    val sentences: LiveData<List<Sentence>>
        get() = _sentences


    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.deleteUser()
                withContext(Dispatchers.Main) {
                    SharedPreferencesManager.instance!!.clear()
                    _isUserDeleteSuccessful.value = SingleClickEvent(true)
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _isUserDeleteSuccessful.value = SingleClickEvent(false)
                }
            }
        }
    }


    fun updateUser(email: String, firstname: String, lastname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.updateUser(email, firstname, lastname)
                withContext(Dispatchers.Main) {
                    _isUserUpdateSuccessful.value = SingleClickEvent(true)

                    SharedPreferencesManager.instance!!.apply {
                        this.email = email
                        this.firstName = firstname
                        this.lastName = lastname
                    }
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _isUserUpdateSuccessful.value = SingleClickEvent(false)
                }
            }
        }
    }

    fun deletePoem(poemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = poemRepository.deletePoem(poemId)
                withContext(Dispatchers.Main) {
                    _isDeleteSuccessful.value = SingleClickEvent(true)
                }
            } catch (he: HttpException) {
                _isDeleteSuccessful.value = SingleClickEvent(false)
            }
        }
    }


    fun updatePoem(poemId: Int, title: String, genre: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = poemRepository.updatePoem(poemId, title, genre, body)
                withContext(Dispatchers.Main) {
                    _isUpdateSuccessful.value = SingleClickEvent(true)
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _isUpdateSuccessful.value = SingleClickEvent(false)
                }
            }
        }
    }

    fun createPoem(title: String, genre: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = poemRepository.createPoem(title, genre, body)
                withContext(Dispatchers.Main) {
                    _isCreateSuccessful.value = SingleClickEvent(true)
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _isCreateSuccessful.value = SingleClickEvent(false)
                }
            }
        }
    }

    fun getSuggestions(lastWord: String, genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = suggestionRepository.getPoemSuggestions(lastWord, genre)
                withContext(Dispatchers.Main) {
                    _sentences.value = response.sentencesList
                }
            } catch (he: HttpException) {
                withContext(Dispatchers.Main) {
                    _sentences.value = null
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}