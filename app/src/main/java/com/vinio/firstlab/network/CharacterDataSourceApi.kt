package com.vinio.firstlab.network

import com.vinio.firstlab.entity.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds


interface CharacterDataSourceApi {
    suspend fun getCharacter(): Result<Character>
    suspend fun getSomeCharacters(): Result<List<Character>>
}

class CharacterDataSource : CharacterDataSourceApi {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client: HttpClient by lazy {
        HttpClient(engine = OkHttp.create()) {
            install(ContentNegotiation) { json(json) }
            install(HttpTimeout) {
                connectTimeoutMillis = 20.seconds.inWholeMilliseconds
                requestTimeoutMillis = 60.seconds.inWholeMilliseconds
                socketTimeoutMillis = 20.seconds.inWholeMilliseconds
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    override suspend fun getCharacter(): Result<Character> {
        val path = "https://anapioficeandfire.com/api/characters/583"
        return try {
            val response: Character = client.get(path).body()
            Result.success(response)
        } catch (e: ClientRequestException) { // 4xx errors
            Result.failure(Exception("Client error: ${e.response.status.description}", e))
        } catch (e: ServerResponseException) { // 5xx errors
            Result.failure(Exception("Server error: ${e.response.status.description}", e))
        } catch (e: TimeoutCancellationException) {
            Result.failure(Exception("Request timed out", e))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error occurred", e))
        }
    }

    override suspend fun getSomeCharacters(): Result<List<Character>> {
        val path = "https://anapioficeandfire.com/api/characters?page=2&pageSize=50"
        return try {
            val response: List<Character> = client.get(path).body()
            Result.success(response)
        } catch (e: ClientRequestException) { // 4xx errors
            Result.failure(Exception("Client error: ${e.response.status.description}", e))
        } catch (e: ServerResponseException) { // 5xx errors
            Result.failure(Exception("Server error: ${e.response.status.description}", e))
        } catch (e: TimeoutCancellationException) {
            Result.failure(Exception("Request timed out", e))
        } catch (e: Exception) {
            Result.failure(Exception("Unknown error occurred", e))
        }
    }
}

fun main() {
    val characterDataSource = CharacterDataSource()
    runBlocking {
        println(characterDataSource.getCharacter().getOrNull())
    }
}