package one.karaage.misswrapper.auth

interface AuthRepository {
    suspend fun signUp(username: String, password: String): AuthResult<Unit>
    suspend fun signIn(username: String, password: String): AuthResult<Unit>
    suspend fun authorize(): AuthResult<Unit>
}
// Token is stored in preferences