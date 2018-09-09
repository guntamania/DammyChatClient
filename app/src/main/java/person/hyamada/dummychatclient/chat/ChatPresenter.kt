package person.hyamada.dummychatclient.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import okhttp3.*
import java.io.IOException

class ChatPresenter(activity: ChatActivity) {
    val activity : Activity = activity
    val email : String = activity.intent.getStringExtra("EMAIL")
    val token : String = activity.intent.getStringExtra("TOKEN")
    init {
    }


    fun getMessages() {
        val client = OkHttpClient()
        val type = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(type, "")
        //val url = "https://dammy-chat-server.herokuapp.com/apis/v1/auth/sign_in"
        val url = HttpUrl.Builder()
                .scheme("https")
                .host("dammy-chat-server.herokuapp.com")
                .addPathSegment("apis")
                .addPathSegment("v1")
                .addPathSegment("posts")
                .build()

        Log.d("ymd", url.toString())
        val request = Request.Builder().url(url).post(requestBody).build()
        val app = this.application
        client.newCall(request).enqueue( object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ymd", e.toString())
                toast("TIMEOUT")
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("ymd", "success")
                if (response!!.code() != 200) {
                    toast("response status:" + response!!.code())
                    return
                }
                Log.d("ymd", "uid: " + response!!.header("Uid"))
                Log.d("ymd", "token: " + response!!.header("Access-Token"))
                val token = response.header("Access-Token")
                val intent = Intent(app, ChatActivity::class.java )
                        .putExtra("EMAIL", id)
                        .putExtra("TOKEN", token)
                startActivity(intent)
            }
        })

}