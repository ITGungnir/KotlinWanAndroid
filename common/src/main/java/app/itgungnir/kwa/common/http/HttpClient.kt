package app.itgungnir.kwa.common.http

import android.util.Log
import app.itgungnir.kwa.common.BuildConfig
import app.itgungnir.kwa.common.HTTP_BASE_URL
import app.itgungnir.kwa.common.HTTP_LOG_TAG
import app.itgungnir.kwa.common.HTTP_TIME_OUT
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient private constructor() {

    companion object {

        private val instance by lazy { HttpClient() }

        val api by lazy { HttpClient.instance.buildApi() }
    }

    private fun buildApi() = Retrofit.Builder()
        .baseUrl(HTTP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient())
        .build()
        .create(HttpApi::class.java)

    private fun okHttpClient() = OkHttpClient.Builder()
        .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor())
        .build()

    private fun loggingInterceptor() =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith(
                    "]"
                )
            ) {
                Logger.json(message)
            } else {
                Log.d(HTTP_LOG_TAG, message)
            }
        }).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
}