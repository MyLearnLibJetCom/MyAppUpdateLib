package com.lib.appupdatelibrary.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

private const val TAG = "UrlLauncherApi"

private const val BASE_URl = ""
private const val BASE_URL_PATH = "https://hehe.com"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Setup [HttpLoggingInterceptor] for logging each HTTP request
 */
private val logging = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

/**
 * Build [OkHttpClient] to add [HttpLoggingInterceptor]
 */
val okHttpClient = OkHttpClient()
    .newBuilder()
    .readTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .callTimeout(5, TimeUnit.SECONDS)
    .connectTimeout(5, TimeUnit.SECONDS)
    .followRedirects(false)
    .followSslRedirects(false)
    .addInterceptor(logging)
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("http://192.168.20.104:2750")
    .build()

/**
 * A public interface that exposes the [getUrl] method
 * and also express the [reportFailedUrl] method.
 */
interface UrlApiService {
    @GET
    suspend fun getFetchUrl(@Url url : String) : Response<ResponseBody>

    @POST
    suspend fun postFetchUrl(@Url url : String, @Body bodyData : String) : Response<ResponseBody>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object UrlApi {
    val retrofitService: UrlApiService by lazy {
        Log.i(TAG, "UrlLauncherApiService lazy init")
        retrofit.create(UrlApiService::class.java)
    }
}