package com.example.githubuser.api
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.githubuser.BuildConfig
import retrofit2.Call


interface ApiToken {

    @GET("users")
    @Headers("Authorization: token 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", "UserResponse-Agent: request")
    suspend fun getUserListAsync(): ArrayList<ClickUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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", "UserResponse-Agent: request")
    suspend fun getDetailUserAsync(@Path("username") username: String): ClickUser

    @GET("search/users")
    @Headers("Authorization: token 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", "UserResponse-Agent: request")
    fun getUserBySearch(@Query("q") username: String): Call<ClickSearch>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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", "UserResponse-Agent: request")
    suspend fun getFollowersList(@Path("username") username: String): ArrayList<ClickUser>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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", "UserResponse-Agent: request")
    suspend fun getFollowingList(@Path("username") username: String): ArrayList<ClickUser>

    companion object {
        private const val API_TOKEN = BuildConfig.API_TOKEN
    }
}