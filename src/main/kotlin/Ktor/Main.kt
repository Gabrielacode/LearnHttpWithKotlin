package Ktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

fun main(){
    val client = HttpClient(io.ktor.client.engine.cio.CIO){

    } /// For The Http Client and we specify the engine that is in charge of the http
    runBlocking {
        val service = PostServiceImp(client)
        println(service.getPosts())
        println(service.createPost(PostRequest(1,"House","SkibidiToilet")))
        println(service.getPosts()?.filter { it.title == "House" })
    }
}
//We use Ktor we need some dependencies like the core , engines , logging etc
//Now we have te depndecnies lets start
//The api we are using is JSON placeholder

//Lets define routes for the request in an object class
object HttpRoutes{
    private const val  BASE_URL = "https://jsonplaceholder.typicode.com"
    const val POSTS = "$BASE_URL/posts"
}
@Serializable
data class PostResponse(
    val userId :Int,
    val id:Int,
    val title:String,
    val body :String
)
@Serializable
data class PostRequest(
    val userId :Int,
    val title:String,
    val body :String
)
//We now  create an interface for the operations we want to make to the api
//just like dtatabase

interface PostService{
    suspend fun getPosts():List<PostResponse>?
    suspend fun createPost(postRequest: PostRequest):PostResponse?
}
//We will now create the impl
class PostServiceImp(val client :HttpClient):PostService{
    override suspend fun getPosts(): List<PostResponse>? {
       val response =  client.get{
           url(HttpRoutes.POSTS)
       }
        if(response.status == HttpStatusCode.OK){
            val body = response.bodyAsText()
             return Json.decodeFromString<List<PostResponse>>(body) as List<PostResponse>
        }
     return null
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
         val response = client.post {
            url(HttpRoutes.POSTS)
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToJsonElement(postRequest).toString())
        }
        if(response.status == HttpStatusCode.OK){
            val body = response.bodyAsText()
            return Json.decodeFromString<PostResponse>(body) as PostResponse
        }
        return null
    }

}