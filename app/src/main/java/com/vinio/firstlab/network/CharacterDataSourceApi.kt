package com.vinio.firstlab.network

import android.content.Context
import android.util.Log
import com.vinio.firstlab.AppDatabase
import com.vinio.firstlab.entity.Character
import com.vinio.firstlab.entity.CharacterDao
import com.vinio.firstlab.entity.CharacterEntity
import com.vinio.firstlab.entity.CharacterRepository
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
import kotlinx.serialization.json.Json
import java.net.UnknownHostException
import kotlin.time.Duration.Companion.seconds


interface CharacterDataSourceApi {
    suspend fun getCharacter(): Result<Character>
    suspend fun getSomeCharacters(page: Int): Result<List<Character>>
}

class CharacterDataSource(private val context: Context) : CharacterDataSourceApi {
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
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Unable to resolve host.", e))
        }
    }

    // TODO закрыть ktor
    override suspend fun getSomeCharacters(page: Int): Result<List<Character>> {
        Log.d("[MYRESP]", page.toString())
        val path = "https://anapioficeandfire.com/api/characters?page=$page&pageSize=50"
        return try {
            val response: List<Character> = client.get(path).body()
            Log.d("[MYRESP]", response.toString())
            Result.success(response)
        } catch (e: ClientRequestException) { // 4xx errors
            Log.d("[RESP ERR]", "Client error: ${e.response.status.description}")
            Result.failure(Exception("Client error: ${e.response.status.description}", e))
        } catch (e: ServerResponseException) { // 5xx errors
            Log.d("[RESP ERR]", "Server error: ${e.response.status.description}.")
            Result.failure(Exception("Server error: ${e.response.status.description}", e))
        } catch (e: TimeoutCancellationException) {
            Log.d("[RESP ERR]", "Request timed out.")
            Result.failure(Exception("Request timed out", e))
        } catch (e: UnknownHostException) {
            Log.d("[RESP ERR]", "Network error: Unable to resolve host.")
            Result.failure(Exception("Network error: Unable to resolve host.", e))
        } catch (e: Exception) {
            Log.d("[RESP ERR]", "Unknown error occurred.")
            Result.failure(Exception("Unknown error occurred", e))
        }
    }
}



