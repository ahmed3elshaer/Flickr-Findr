package com.component.network

import com.components.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun androidHttpClient(
    withLog: Boolean,
    engine: HttpClientEngine = OkHttp.create()
) = HttpClient(engine) {
    defaultRequest {
        host = "flickr.com/services/rest/"
        url {
            protocol = URLProtocol.HTTPS
            parameters.append("api_key", BuildConfig.API_KEY)
            parameters.append("format", "json")
        }
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
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
        )
    }
}
