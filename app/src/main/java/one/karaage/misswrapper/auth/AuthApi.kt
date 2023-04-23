package one.karaage.misswrapper.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): TokenResponse

    @GET
    suspend fun authenticate(
        @Header("Authorization") token: String
    )
    // Use interceptor for multiple authentication routes
}