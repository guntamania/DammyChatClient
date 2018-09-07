package person.hyamada.dummychatclient.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Observable
import okhttp3.*
import person.hyamada.dummychatclient.R
import person.hyamada.dummychatclient.chat.ChatActivity
import person.hyamada.dummychatclient.ui.observer.createObserableFromButton
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val button = findViewById<Button>(R.id.button)
        val emailText = findViewById<EditText>(R.id.emailText)
        val passwordText = findViewById<EditText>(R.id.passwordText)

        button.setOnClickListener{_ -> login(emailText.text.toString(), passwordText.text.toString())}
    }

    fun toast(text: String) {
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()

    }

    fun login(id: String, password: String) {
        val client = OkHttpClient()
        val type = MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(type, "")
        //val url = "https://dammy-chat-server.herokuapp.com/apis/v1/auth/sign_in"
        val url =HttpUrl.Builder()
                .scheme("https")
                .host("dammy-chat-server.herokuapp.com")
                .addPathSegment("apis")
                .addPathSegment("v1")
                .addPathSegment("auth")
                .addPathSegment("sign_in")
                .addQueryParameter("email", id)
                .addQueryParameter("password", password)
                .build()

        Log.d("ymd", url.toString())
        val request = Request.Builder().url(url).post(requestBody).build()
        val app = this.application
        client.newCall(request).enqueue( object: Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("ymd", e.toString())
                Toast.makeText(app,"FAIL", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call?, response: Response?) {
                Log.d("ymd", "success")
                if (response!!.code() != 200) {
                    toast("response status:" + response!!.code())
                    return
                }
                Log.d("ymd", "uid: " + response!!.header("Uid"))
                Log.d("ymd", "token: " + response!!.header("Access-Token"))
                Toast.makeText(app,"res" +response.toString(), Toast.LENGTH_LONG).show()
                val token = response.header("Access-Token")
                val intent = Intent(app, ChatActivity::class.java )
                        .putExtra("EMAIL", id)
                        .putExtra("TOKEN", token)
                startActivity(intent)
            }
        })

    }
}
