package com.example.appto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appto.databinding.ActivityRegisterBinding


class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}