package person.hyamada.dummychatclient.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import person.hyamada.dummychatclient.R
import person.hyamada.dummychatclient.data.Message

class ChatMessageAdapter(private val context: Context, private val mPresenter: ChatPresenter)
    :RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    private var mRecyclerView : RecyclerView? = null
    var itemList:MutableList<Message>? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.item_chat_message, parent, false)

        return ChatViewHolder(mView)
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {
        viewHolder.let { vh ->
            val item = itemList!!.get(position)
            if (item.email.equals(mPresenter.email)) {
                vh.myMessageView.visibility = View.VISIBLE
                vh.messageView.visibility = View.GONE
                vh.myMessageView.text = item.message
            } else {
                vh.messageView.text = item.message
            }
            vh.usernameView.text = item.user
        }
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    fun addItem(message: Message) {
        itemList!!.add(message)
        notifyDataSetChanged()
    }

    class ChatViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val usernameView: TextView = view.findViewById(R.id.username_in_item)
        val messageView: TextView = view.findViewById(R.id.message_in_item)
        val myMessageView: TextView = view.findViewById(R.id.my_message_in_item)
    }
}