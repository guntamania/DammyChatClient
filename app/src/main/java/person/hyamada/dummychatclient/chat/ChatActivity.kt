package person.hyamada.dummychatclient.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import okhttp3.*
import person.hyamada.dummychatclient.R
import java.io.IOException

class ChatActivity : AppCompatActivity() {
    lateinit var mPresenter: ChatPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mPresenter = ChatPresenter(this)
        findViewById<TextView>(R.id.textView1).setText(mPresenter.email)
        findViewById<TextView>(R.id.textView2).setText(mPresenter.token)

    }

    interface ChatCallback {
        fun a()
        fun b()
    }

}