package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_message_chat.*
import kotlinx.android.synthetic.main.activity_registeractivity.*

class MessageChatActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser?= null
    var chatsAdapter: ChatsAdapter?= null
    var mChatList:  List<Chat>? = null
    lateinit var recycler_view_chats: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)

        recycler_view_chats=findViewById(R.id.recycler_view_chats)
        recycler_view_chats.setHasFixedSize(true)
        var linearlayoutmanager = LinearLayoutManager(applicationContext)
        linearlayoutmanager.stackFromEnd= true
        recycler_view_chats.layoutManager = linearlayoutmanager


        firebaseUser= FirebaseAuth.getInstance().currentUser
        retrieveMessages()
        signout_btn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent( this@MessageChatActivity, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        send_message_btn.setOnClickListener {
            val message = text_message.text.toString()
            if(message == "")
            {
                Toast.makeText( this@MessageChatActivity, "Please write message", Toast.LENGTH_LONG).show()

            }
            else
            {
                sendMessageToUser(firebaseUser!!.uid, message)

            }
        }
    }

    override fun onStart() {
        super.onStart()

        retrieveMessages()
    }
    private fun retrieveMessages() {
        mChatList= ArrayList()
        val reference = FirebaseDatabase.getInstance().reference.child("Chats")
        reference. addValueEventListener( object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                (mChatList as ArrayList<Chat>).clear()
                for (snapshot in p0.children) {
                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat != null) {
                        (mChatList as ArrayList<Chat>).add(chat)
                    }
                    chatsAdapter =
                        ChatsAdapter(this@MessageChatActivity, (mChatList as ArrayList<Chat>))
                    recycler_view_chats.adapter = chatsAdapter
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }





    private fun sendMessageToUser(uid: String, message: String) {
        val reference = FirebaseDatabase.getInstance().reference
        val messageKey = reference.push().key

        val messageHashMap = HashMap<String, Any?>()
        messageHashMap["sender"] = uid
        messageHashMap["message"]=message
        messageHashMap["messageId"]=messageKey
        reference.child("Chats").child(messageKey!!).setValue(messageHashMap).addOnCompleteListener {
            task->
            if(task.isSuccessful)
            {
                val chatsListReference = FirebaseDatabase.getInstance().reference.child("Chats").child(firebaseUser!!.uid)


                chatsListReference.child("id").setValue(firebaseUser!!.uid)
                val reference = FirebaseDatabase.getInstance().reference.child("Users")
                    .child(firebaseUser!!.uid)

            }
        }
        retrieveMessages()
    }


}
