package com.example.apicallbasic


import com.example.example.ExampleJson2KtKotlin
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("users?page=2")
    suspend fun imdbFunction() : Response<ExampleJson2KtKotlin>

}