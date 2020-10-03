package com.trendster.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    lateinit var cardAddNotice : MaterialCardView
    lateinit var cardAddImage : MaterialCardView
    lateinit var cardAddEbook : MaterialCardView
    lateinit var cardAddFaculty : MaterialCardView
    lateinit var cardDeleteNotice : MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        cardAddNotice = findViewById(R.id.cardAddNotice)
        cardAddImage = findViewById(R.id.cardAddImage)
        cardAddEbook = findViewById(R.id.cardAddEbook)
        cardAddFaculty = findViewById(R.id.cardAddFaculty)
        cardDeleteNotice = findViewById(R.id.cardDeleteNotice)



    }

    fun method(view: View) {

        /* this method to implement many clicklistener , write only method in the view(onClick = "methodName") in xml , or kamal dekhe*/

        val intent = Intent(this@MainActivity , UploadNotice::class.java)
        startActivity(intent)
    }
}