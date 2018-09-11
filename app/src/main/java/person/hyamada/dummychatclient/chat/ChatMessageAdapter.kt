package person.hyamada.dummychatclient.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import person.hyamada.dummychatclient.R
import person.hyamada.dummychatclient.data.Message

class ChatMessageAdapter(private val context: Context, private val itemList:List<Message>)
    :RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    private var mRecyclerView : RecyclerView? = null

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
        viewHolder?.let {
            it.messageView.text = itemList.get(position).message
            it.usernameView.text = itemList.get(position).user
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ChatViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val usernameView: TextView = view.findViewById(R.id.username_in_item)
        val messageView: TextView = view.findViewById(R.id.message_in_item)
    }
}