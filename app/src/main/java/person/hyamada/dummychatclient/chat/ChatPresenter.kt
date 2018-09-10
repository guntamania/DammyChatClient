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
    var onMessageArrive: ((message : String) -> Unit)? = null
    init {
    }

    fun getMessages() {
        val client = OkHttpClient()
        val url = HttpUrl.Builder()
                .scheme("https")
                .host("dammy-chat-server.herokuapp.com")
                .addPathSegment("apis")
                .addPathSegment("v1")
                .addPathSegment("posts")
                .build()

        val request = Request.Builder()
                .url(url)
                .addHeader("email", email)
                .addHeader("access-token", token)
                .get().build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ymd", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("ymd", "success get messages")
                if (response!!.code() != 200) {
                    return
                }
                onMessageArrive?.invoke(response.body().toString())
            }
        })
    }
}