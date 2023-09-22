package com.mixa.dictio.data.datasources.remote

import com.mixa.dictio.data.models.requests.TranslateRequest
import com.mixa.dictio.data.models.responses.TranslateResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TranslateService {

    @POST("/")
    @Headers("\"Content-Type\": \"application/json\"")
    fun translate(@Body request: TranslateRequest): Observable<TranslateResponse>
}