package person.hyamada.dummychatclient.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {
    lateinit var mPresenter: ChatPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ChatPresenter()


    }
}