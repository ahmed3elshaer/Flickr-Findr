package com.component.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

fun httpClient(withLog: Boolean, engine: HttpClientEngine = OkHttp.create()) = HttpClient(engine) {
    defaultRequest {
        host = "api.foursquare.com/v3"
        url {
            protocol = URLProtocol.HTTPS
        }
        header("Authorization", BuildConfig.API_KEY)
        header("Content-Type", "application/json")

    }
    if (withLog) {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("HttpClient : $message")
                }
            }
        }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }
}