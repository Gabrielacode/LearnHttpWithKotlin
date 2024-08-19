package OkHttp

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

const val BASE_URL = "https://dummyjson.com"
 const val PRODUCTS = "$BASE_URL/products"
fun main(){
    val client = OkHttpClient()
    //blockingGetRequest(PRODUCTS,client)
    //nonBlockingGetRequest("$PRODUCTS/3",client)
    //nonBlockingGetRequestwithQuery("$PRODUCTS/search",client,"phone")
    asyncPostData("$PRODUCTS/add",client,"Skibidi")
    println("Non Blocking")
}
 fun blockingGetRequest(url:String , client :OkHttpClient){
// First we get the request by using its builder
     val request = Request.Builder()
         .url(url) //Here we specify the url
         .build() //Here we return the request

     val call = client.newCall(request)//THis call will be used to execute the request
     //To execute it synchronous;y we call execute whic returns  a response

     val response =  call.execute().use{
         if(it.isSuccessful) {println(it.message)
         println(it.body?.string()) //We can also get the body of the response
         }
     }
 }
fun nonBlockingGetRequest(url: String,client: OkHttpClient){
    val request = Request.Builder().
            url(url)
            .build()
     client.newCall(request).enqueue(object :Callback{
         override fun onFailure(call: Call, e: IOException) {
             TODO("Not yet implemented")
         }

         override fun onResponse(call: Call, response: Response) {
             //The operation success is not guarantented as this is jyst tcp success not application susccess
             //Meaning the request was sent but might still send an 404 eror
             println(response.message)
             //We can also get the headers of the response and also put headers in our requests
             for ((name,value) in response.headers){
                 println("Name : $name = Value : $value")
             }
             println(response.body?.string())
         }

     }) ///As it is asyncronous we define call backs that will be called when the response is gotten whether a failure or sucess

}

//Lets us now use query parameter
fun nonBlockingGetRequestwithQuery(domainurl: String,client: OkHttpClient , searchTerm :String){
     val url = domainurl.toHttpUrl().newBuilder().addQueryParameter("q",searchTerm)
    val request = Request.Builder().
    url(url.build())
        .build()
    client.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call, response: Response) {
            //The operation success is not guarantented as this is jyst tcp success not application susccess
            //Meaning the request was sent but might still send an 404 eror
            println(response.message)
            //We can also get the headers of the response and also put headers in our requests
            for ((name,value) in response.headers){
                println("Name : $name = Value : $value")
            }
            println(response.body?.string())
        }

    }) ///As it is asyncronous we define call backs that will be called when the response is gotten whether a failure or sucess

}

//Now lets post some data to a server
fun asyncPostData(url: String,client: OkHttpClient,sample:String){
    val requestBody = sample.toRequestBody()
    // We have to convert the request body to something fitting
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()
    client.newCall(request).enqueue(object :Callback{
        override fun onFailure(call: Call, e: IOException) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call, response: Response) {
            //The operation success is not guarantented as this is jyst tcp success not application susccess
            //Meaning the request was sent but might still send an 404 eror
            println(response.message)
            //We can also get the headers of the response and also put headers in our requests
            for ((name,value) in response.headers){
                println("Name : $name = Value : $value")
            }
            println(response.body?.string())
        }

    })
}
//We can also make files , create our own request body and etc

//Now we wil use OkHttpr
//It is also another  http client call Okhttp which is normally used for Android
//Created by Square all these have their different ways of doing Http clients
//It has both blocking and non -blocking
//It is also the back bone of retrofit , picasso , glide
//We can also do post , put , delete , configure the client  , cancel calls and so non