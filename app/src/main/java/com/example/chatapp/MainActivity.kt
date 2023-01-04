package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn : Button = findViewById(R.id.enterchat)
        var nickname : EditText = findViewById(R.id.nickname)

        btn.setOnClickListener {
            if(!nickname.text.toString().isEmpty()) {
                var i: Intent = Intent(applicationContext,ChatBoxActivity::class.java)
                i.putExtra("nickName",nickname.text.toString())
                startActivity(i)
            }
            else{
                Toast.makeText(applicationContext,"Enter Your Nick Name!",Toast.LENGTH_SHORT)
            }
        }
    }
}