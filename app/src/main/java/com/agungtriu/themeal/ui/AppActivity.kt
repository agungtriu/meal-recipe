package com.agungtriu.themeal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.agungtriu.themeal.databinding.ActivityAppBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}