package one.karaage.misswrapper.auth

import android.content.SharedPreferences
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val pref: SharedPreferences
): AuthRepository {
    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password,
                )
            )
            // To make user signin automatically after signup
            signIn(username, password)
        } catch (e: HttpException){
            if(e.code() == 401) AuthResult.UnAuthorized()
            else AuthResult.UnknownError()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = username,
                    password = password,
                )
            )
            // To make user signin automatically after signup
            pref.edit()
                .putString("jwt", response.token)
                .apply()

            AuthResult.Authorized()
        } catch (e: HttpException){
            if(e.code() == 401) AuthResult.UnAuthorized()
            else AuthResult.UnknownError()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            // Trying out authenticate directly when not signed up case:
            val token = pref.getString("jwt", null) ?: return AuthResult.UnAuthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException){
            if(e.code() == 401) AuthResult.UnAuthorized()
            else AuthResult.UnknownError()
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}