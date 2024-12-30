package com.samzuhalsetiawan.localstorage.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.samzuhalsetiawan.localstorage.sample.compose.MainComposeActivity
import com.samzuhalsetiawan.localstorage.sample.databinding.ActivityMainBinding
import com.samzuhalsetiawan.localstorage.sample.view.MainViewActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater).also { _binding = it }
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnGoToComposeActivity.setOnClickListener {
            onGoToComposeActivityButtonClick()
        }
        binding.btnGoToViewActivity.setOnClickListener {
            onGoToViewActivityButtonClick()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun onGoToComposeActivityButtonClick() {
        val intent = Intent(this, MainComposeActivity::class.java)
        startActivity(intent)
    }

    private fun onGoToViewActivityButtonClick() {
        val intent = Intent(this, MainViewActivity::class.java)
        startActivity(intent)
    }
}