package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registeractivity.*

class Registeractivity : AppCompatActivity() {

    private lateinit var mAuth :FirebaseAuth
    private lateinit var refUsers :DatabaseReference
    private var firebaseUserID: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeractivity)



        mAuth = FirebaseAuth.getInstance()

        register_bbtn.setOnClickListener{
            val intent = Intent( this@Registeractivity, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        register_btn.setOnClickListener{
            registerUser()

        }
    }

    private fun registerUser() {
        val username: String = username_register.text.toString()
        val email: String = email_register.text.toString()
        val password: String = password_register.text.toString()

        if(username == "")
        {
            Toast.makeText( this@Registeractivity, "Please write username", Toast.LENGTH_LONG).show()
        }
        else if(email == "")
        {
            Toast.makeText( this@Registeractivity, "Please write email", Toast.LENGTH_LONG).show()
        }
        else if(password == "")
        {
            Toast.makeText( this@Registeractivity, "Please write password", Toast.LENGTH_LONG).show()
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->
                if(task.isSuccessful)
                {
                    firebaseUserID= mAuth.currentUser!!.uid
                    refUsers=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserID)

                    val userHashMap=  HashMap<String , Any>()
                    userHashMap["uid"] = firebaseUserID
                    userHashMap["username"]= username

                    refUsers.updateChildren(userHashMap)
                        .addOnCompleteListener {  task->
                            if(task.isSuccessful)
                            {
                                val intent = Intent( this@Registeractivity, MessageChatActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }

                        }
                }
                else
                {
                    Toast.makeText( this@Registeractivity, "Error message", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
