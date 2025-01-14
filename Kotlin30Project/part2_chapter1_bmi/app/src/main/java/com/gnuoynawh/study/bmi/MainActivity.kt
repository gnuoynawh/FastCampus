package com.gnuoynawh.study.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heightEditText: AppCompatEditText = findViewById(R.id.heightEditText)
        val weightEditText: AppCompatEditText = findViewById(R.id.weightEditText)
        val resultButton: AppCompatButton = findViewById(R.id.resultButton)

        resultButton.setOnClickListener {
            if(heightEditText.text!!.isEmpty() || weightEditText.text!!.isEmpty()) {
                return@setOnClickListener
            }

            val height: Int = heightEditText.text.toString().toInt()
            val weight: Int = weightEditText.text.toString().toInt()

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }
    }
}