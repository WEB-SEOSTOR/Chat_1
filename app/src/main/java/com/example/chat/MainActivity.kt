package com.example.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var messageList: ListView? = null
    private var messageEditText: TextView? = null
    private var sendButton: Button? = null
    private var photoButton: ImageButton? = null
    private lateinit var adapter: AwesomeMessageAdapter

    private lateinit var nameUser: String

    private lateinit var database: FirebaseDatabase
    private lateinit var messages: DatabaseReference
    private lateinit var messagesListener: ChildEventListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database
        messages = database.reference.child("messages")

        nameUser = "Anonymous"

        messageList = findViewById(R.id.chatBody)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendMessageButton)
        photoButton = findViewById(R.id.sendPhoto)

        val listMessages = mutableListOf<AwesomeMessage>()
        adapter = AwesomeMessageAdapter(this, R.layout.message_item, listMessages)
        messageList?.adapter = adapter

        messageEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                sendButton?.isEnabled = s.toString().trim().isNotEmpty()
            }
        })

        messageEditText?.filters = arrayOf<InputFilter>( InputFilter.LengthFilter(500))

        sendButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val awesomeMessage: AwesomeMessage = AwesomeMessage()

                awesomeMessage.text = messageEditText?.text.toString()
                awesomeMessage.name = nameUser
                awesomeMessage.photoURL = null

                messages.push().setValue(awesomeMessage)

                messageEditText?.text = ""
            }
        })

        photoButton?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })

        messagesListener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val messageBase: AwesomeMessage? = snapshot.getValue(AwesomeMessage::class.java)

                adapter.add(messageBase)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

        }

        messages.addChildEventListener(messagesListener)
    }
}