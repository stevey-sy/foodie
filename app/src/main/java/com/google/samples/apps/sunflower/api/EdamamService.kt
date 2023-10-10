/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.api

import com.google.samples.apps.sunflower.data.EdamamFoodsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamService {

    @GET("")
    suspend fun getFoods(
        @Query("app_id") appId : String,
        @Query("app_key") appKey : String,
        @Query("nutrition-type") nutritionType: String,
    ) : EdamamFoodsResponse

    companion object {
        private const val BASE_URL = "https://api.edamam.com/api/food-database/v2/parser"

        fun create(): EdamamService {
            val logger = HttpLoggingInterceptor().apply {level = HttpLoggingInterceptor.Level.BASIC}
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EdamamService::class.java)
        }
    }
}