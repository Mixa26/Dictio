package com.mixa.dictio.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mixa.dictio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}