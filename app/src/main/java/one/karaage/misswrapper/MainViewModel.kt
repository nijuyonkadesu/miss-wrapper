package one.karaage.misswrapper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.autofill.AutofillType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import one.karaage.misswrapper.auth.AuthRepository
import one.karaage.misswrapper.auth.AuthResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
): ViewModel() {

    var state by mutableStateOf(AuthState())

    // Custom channel to observe for our UI
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

    init {
        // to automatically log in, after first signIn
        authenticate()
    }
    fun onEvent(event: AuthUiEvent) {
        when(event){
            is AuthUiEvent.SignInPasswordChanged ->
                state = state.copy(signInPassword = event .value)
            is AuthUiEvent.SignInUsernameChanged ->
                state = state.copy(signInUsername = event .value)
            AuthUiEvent.SignIn -> signIn()

            is AuthUiEvent.SignUpPasswordChanged ->
                state = state.copy(signUpPassword = event .value)
            is AuthUiEvent.SignUpUsernameChanged ->
                state = state.copy(signUpUsername = event .value)
            AuthUiEvent.SignUp -> signUp()
        }
    }

    private fun signUp(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(
                state.signUpUsername,
                state.signUpPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                state.signInUsername,
                state.signInPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
    private fun authenticate(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}