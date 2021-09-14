package com.example.diffviewer.retrofit.rest

import android.util.Log

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object ApiClient {

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    private fun getRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var ua = System.getProperty("http.agent")
        ua = if(ua !== null) {
            ua.toString()
        } else {
            ""
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val request: Request = original.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .header("User-Agent", ua)
                        .build()

                    val response: Response = chain.proceed(request)
                    when(response.code) {
                        401 -> {
                            Log.d("Unauthorized-->", "$response")
                        }
                        400,403,404 -> {
                            Log.d("Error--> ", "$response")
                        }
                        500,503 -> {
                            Log.d("ServerError-->", "$response")
                        }
                    }
                    return response
                }
            })
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

