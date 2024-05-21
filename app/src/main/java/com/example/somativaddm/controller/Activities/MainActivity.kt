package com.example.somativaddm.controller.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.somativaddm.R
import com.example.somativaddm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        binding.mainRecyclerView.layoutManager =
            GridLayoutManager(this, 2)


        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)

        }


    }


