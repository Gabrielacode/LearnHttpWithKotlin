package RawURLConnection

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun main(args: Array<String>) {
    println("Hello World!")
    runBlocking {
        makeRequest("https://jsonplaceholder.typicode.com/posts")
    }

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}
//TODAY WE WILL BE MAKING HTTP REQUESTS IN KOTLIN
//So lets create a function that makes the request using pure kotlin
// We will be using the fake api lets create a data class that maps the json
fun makeRequest (
    endpoint:String
){

     val url =URL(endpoint)// We set the endpoint and create it as a url
     val openconnection =  url.openConnection() as HttpURLConnection
     openconnection.requestMethod = "GET"
     //Then we get the input stream
     val inputStream =  BufferedReader(InputStreamReader(openconnection.inputStream))
     val response = inputStream.use {
         inputStream.readText()
     }

        // val responseinFakeResponse=  Gson().fromJson<FakeResponse>(response, FakeResponse::class.java)
         println(response)
       //  println(responseinFakeResponse)


     //Now we need  to convert the input stream to json  by using the gson
}
 data class FakeResponse(
     @SerializedName("userId")
     val userId :Int,
     @SerializedName("id")
     val id:Int,
     val title:String,
     val body : String
 )