package person.hyamada.dummychatclient.chat

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Subscription
import okhttp3.*
import person.hyamada.dummychatclient.data.Message
import java.io.IOException
import java.net.URI
import java.util.concurrent.TimeUnit

class ChatPresenter(activity: ChatActivity) {
    val activity: Activity = activity
    val email: String = activity.intent.getStringExtra("EMAIL")
    val token: String = activity.intent.getStringExtra("TOKEN")
    val client: String = activity.intent.getStringExtra("CLIENT")
    var onMessageArrive: ((messages: Array<Message>, adapter: ChatMessageAdapter) -> Unit)? = null
    var onNewMessageArrive: ((message: Message, adapter: ChatMessageAdapter) -> Unit)? = null

    private var mChatMessageAdapter: ChatMessageAdapter = ChatMessageAdapter(activity, this)

    init {
    }

    fun subscribe() {

        val uri = URI("wss://dammy-chat-server.herokuapp.com/cable")

        val consumer = ActionCable.createConsumer(uri)

        val appearanceChannel = Channel("PostChannel")
        val subscription = consumer.subscriptions.create(appearanceChannel)
        subscription
                .onConnected {
                    android.util.Log.d("ymd", "onConnected")
                }
                .onRejected {
                    android.util.Log.d("ymd", "onRejected")
                }
                .onReceived{ data ->
                    val mapper = jacksonObjectMapper()
                    val message = mapper.readValue<Message>(data.toString())
                    android.util.Log.d("ymd", "added")
                    activity.runOnUiThread {
                        mChatMessageAdapter!!.addItem(message)
                        onNewMessageArrive?.invoke(message, mChatMessageAdapter!!)
                    }
                }
                .onFailed{
                    android.util.Log.d("ymd", "onFailed")
                }
        consumer.connect()

    }

    fun getMessages() {
        val httpClient = OkHttpClient()
        val url = HttpUrl.Builder()
                .scheme("https")
                .host("dammy-chat-server.herokuapp.com")
                .addPathSegment("apis")
                .addPathSegment("v1")
                .addPathSegment("posts")
                .build()

        val request = Request.Builder()
                .url(url)
                .addHeader("uid", email)
                .addHeader("access-token", token)
                .addHeader("client", client)
                .get().build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ymd", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("ymd", "success get messages")
                if (response!!.code() != 200) {
                    return
                }
                val mapper = jacksonObjectMapper()
                val messages = mapper.readValue<Array<Message>>(response.body()!!.string())
                mChatMessageAdapter.itemList=messages.toMutableList()
                activity.runOnUiThread {
                    onMessageArrive?.invoke(messages, mChatMessageAdapter!!)
                }

            }
        })
    }

    fun sendMessage(message: String) {
        val httpClient = OkHttpClient()
        val url = HttpUrl.Builder()
                .scheme("https")
                .host("dammy-chat-server.herokuapp.com")
                .addPathSegment("apis")
                .addPathSegment("v1")
                .addPathSegment("posts")
                .addPathSegment("commit")
                .build()
        val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                "{\"message\": \"$message\"}"
        )

        val request = Request.Builder()
                .url(url)
                .addHeader("uid", email)
                .addHeader("access-token", token)
                .addHeader("client", client)
                .post(body).build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ymd", e.toString())
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("ymd", "success get messages")
                if (response!!.code() != 200) {
                    return
                }
                val mapper = jacksonObjectMapper()
                val messages = mapper.readValue<Array<Message>>(response.body()!!.string())
                mChatMessageAdapter.itemList = messages.toMutableList()
                activity.runOnUiThread {
                    onMessageArrive?.invoke(messages, mChatMessageAdapter!!)
                }

            }
        })
    }
}