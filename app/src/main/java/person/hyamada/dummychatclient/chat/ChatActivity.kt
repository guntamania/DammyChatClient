package person.hyamada.dummychatclient.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import person.hyamada.dummychatclient.R
import person.hyamada.dummychatclient.data.Message
import java.io.IOException

class ChatActivity : AppCompatActivity() {
    lateinit var mPresenter: ChatPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mPresenter = ChatPresenter(this)
        findViewById<TextView>(R.id.textView1).setText(mPresenter.client)
        findViewById<TextView>(R.id.textView2).setText(mPresenter.token)
        mPresenter.onMessageArrive = { messages : Array<Message> ->
            val all = messages.joinToString(
                    transform = { message -> message.user + message.message },
                    separator = "\n"
            )
            findViewById<TextView>(R.id.textView2).setText(all)
        }
        mPresenter.getMessages()
        findViewById<Button>(R.id.send_message).setOnClickListener{_ ->
            mPresenter.sendMessage(findViewById<EditText>(R.id.message_edit_text).text.toString())
            findViewById<EditText>(R.id.message_edit_text).text.clear()
        }
    }



}