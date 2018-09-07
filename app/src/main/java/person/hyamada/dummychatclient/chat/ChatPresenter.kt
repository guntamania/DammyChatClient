package person.hyamada.dummychatclient.chat

import android.app.Activity
import android.content.Context
import android.content.Intent

class ChatPresenter(activity: ChatActivity) {
    val activity : Activity = activity
    val email : String = activity.intent.getStringExtra("EMAIL")
    val token : String = activity.intent.getStringExtra("TOKEN")
    init {
    }

}