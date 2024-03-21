package com.lib.appupdatelibrary

import com.lib.appupdatelibrary.network.ResponseModel
import com.lib.appupdatelibrary.network.UrlApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

suspend fun getResponseFetchUrl(urlKey : String) : ResponseModel{
    if(UrlData.LISTURL.containsKey(urlKey)) {
        try {
            val result = UrlApi.retrofitService.getFetchUrl(
                UrlData.LISTURL[urlKey]!!
            )

            val responseBodyString : String? = result.body()?.string()

            return if(responseBodyString == null){
                ResponseModel(
                    code = "30001",
                    body = "",
                    errorMessage = "")
            } else{
                ResponseModel(
                    code = result.code().toString(),
                    body = responseBodyString,
                    errorMessage = "")
            }
        }
        catch (e: Exception){
            return ResponseModel(
                code = "30002",
                body = "",
                errorMessage = e.message ?: ""
            )
        }
    }
    else{
        return ResponseModel(
            code = "30003",
            body = "",
            errorMessage = "Url Not Found!"
        )
    }
}

suspend fun postResponseFetchUrl(urlKey : String, jsonData : String) : ResponseModel{

    val requestBody = jsonData.toRequestBody("application/json".toMediaType())
    if(UrlData.LISTURL.containsKey(urlKey)) {
        try {
            val result = UrlApi.retrofitService.postFetchUrlJson(
                UrlData.LISTURL[urlKey]!!,
                requestBody
            )

            val responseBodyString : String? = result.body()?.string()

            return if(responseBodyString == null){
                ResponseModel(
                    code = "30001",
                    body = "",
                    errorMessage = "")
            } else{
                ResponseModel(
                    code = result.code().toString(),
                    body = responseBodyString,
                    errorMessage = "")
            }
        }
        catch (e: Exception){
            return ResponseModel(
                code = "30002",
                body = "",
                errorMessage = e.message ?: ""
            )
        }
    }
    else{
        return ResponseModel(
            code = "30003",
            body = "",
            errorMessage = "Url Not Found!"
        )
    }
}