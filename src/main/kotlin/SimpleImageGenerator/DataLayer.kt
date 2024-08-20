package SimpleImageGenerator

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.awt.Color
import java.io.File
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.Random

//In this project we will use the DummyJson api to request for images
// Link at https://dummyjson.com/
//Lets make the request

object HttpRoutes{
    const val  ImageUrl = "https://dummyjson.com/image"

}
@OptIn(ExperimentalStdlibApi::class)
fun getCustomImage(client :OkHttpClient, baseUrl: String, height:Int, width:Int, color: Color, text:String) :ApiResult?{
    var result :ApiResult? = null
     val url = baseUrl.toHttpUrl().newBuilder()
         .addPathSegment("$height x$width")
         .addPathSegment(color.rgb.toHexString())
         .addQueryParameter("text",text)
         .build()
    println(url.toString())
     val request = Request.Builder()
         .url(url)
         .build()
     client.newCall(request).enqueue(object :Callback{
         override fun onFailure(call: Call, e: IOException) {
            println( ApiResult.Failure.TransferFailure(e))
         }

         override fun onResponse(call: Call, response: Response) {
              when(response.isSuccessful){
                  true ->{
                      val responseByte = response.body?.bytes()
                       val file = File.createTempFile("Response${kotlin.random.Random.nextInt()}",".png",File("C:\\Users\\gabri\\OneDrive\\Documents\\HttpKotlin\\src\\main\\kotlin\\SimpleImageGenerator\\images"),)
                      file.outputStream().use (){
                          if (responseByte != null) {
                              it.write(responseByte)
                          }
                      }
                      println( ApiResult.Success(file))
                  }
                  false->{
                      println( ApiResult.Failure.ApplicationFailure(response.message))}
              }
         }

     })
    return result

}

  sealed interface ApiResult{
      class Success(file:File): ApiResult

     open class Failure():ApiResult{
           data class TransferFailure( val exception: Exception):Failure(){}
         data class  ApplicationFailure(  val responseMessage:String):Failure(){

         }
      }
  }
