package com.example.chatapp

import ChatBoxAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject



class ChatBoxActivity : AppCompatActivity() {

    private var Nickname : String = ""
    private lateinit var data: ArrayList<Message>
    private lateinit var adapter: ChatBoxAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_box)
        val btn : Button = findViewById(R.id.send)
        data = ArrayList()
        val recyclerView: RecyclerView = findViewById(R.id.messagelist)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ChatBoxAdapter(data)
        recyclerView.adapter = adapter
        Nickname= intent.getStringExtra("nickName").toString()

        val mSocket:Socket = IO.socket("http://192.168.29.151:4000")
        val onConnect = Emitter.Listener {
            Log.d("TAG", "connected...")
        }

        val onDisConnect = Emitter.Listener {
            Log.d("TAG", "disconnecting...")
        }

        val onConnectError = Emitter.Listener { Log.d("TAG", "Error connecting...") }


        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisConnect)
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        mSocket.connect()
        mSocket.emit("join", Nickname)
        mSocket.on("message"){
            args ->
            this@ChatBoxActivity.runOnUiThread {
                val msg: JSONObject = args[0] as JSONObject
                Log.d("tag", msg.getString("message"))
                Log.d("tag", msg.getString("senderNickname"))

                val obj = Message(msg.getString("senderNickname"), msg.getString("message"))
//                data.plusAssign(obj)
//                adapter.notifyDataSetChanged()
                addItem(obj)
                adapter.notifyDataSetChanged()
            }
        }

        btn.setOnClickListener{
            val editText: EditText = findViewById(R.id.message)
            mSocket.emit("messagedetection", Nickname, editText.text.toString())
            editText.setText("")
        }
    }
    private fun addItem(obj : Message){
        data.add(obj)
        for (element in data) {
            Log.d("Tag",element.message)
        }

        adapter.notifyDataSetChanged()
    }
}