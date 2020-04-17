package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatsAdapter(
    mContext: Context,
    mChatList: List<Chat>
) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>()
{

    private val mContext: Context
    private val mChatList: List<Chat>
     init{
         this.mChatList= mChatList
         this.mContext =mContext
     }


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var message: TextView? = null
        var sender: TextView?= null

        init{
            message = itemView.findViewById(R.id.show_text_message)
            sender = itemView.findViewById(R.id.show_User)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.message_left,parent,false)

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
    return mChatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat: Chat = mChatList[position]
        holder.message!!.text = chat.getMessage()
        holder.sender!!.text = chat.getSender()
        holder.message!!.visibility= View.VISIBLE
        holder.sender!!.visibility= View.VISIBLE

    }

    override fun getItemViewType(position: Int): Int {

        return 0
    }

}