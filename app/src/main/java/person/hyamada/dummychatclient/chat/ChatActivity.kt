package person.hyamada.dummychatclient.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import person.hyamada.dummychatclient.R
import person.hyamada.dummychatclient.data.Message
import java.io.IOException

class ChatActivity : AppCompatActivity() {

    private var mPresenter: ChatPresenter? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mPresenter = ChatPresenter(this)
        mPresenter?.onMessageArrive = { messages : Array<Message> ->
            mRecyclerView!!.adapter = ChatMessageAdapter(this, messages.toList())
        }
        mPresenter?.getMessages()
        findViewById<Button>(R.id.send_message).setOnClickListener{_ ->
            mPresenter?.sendMessage(findViewById<EditText>(R.id.message_edit_text).text.toString())
            findViewById<EditText>(R.id.message_edit_text).text.clear()
        }

        mRecyclerView = findViewById<RecyclerView>(R.id.chat_recycler_view)
        //chatRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }



}