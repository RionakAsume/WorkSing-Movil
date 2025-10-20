package com.belloni.worksing.data.remote

import com.belloni.worksing.data.model.UserApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("api/user")
    suspend fun getUsers(): UserApiResponse
}