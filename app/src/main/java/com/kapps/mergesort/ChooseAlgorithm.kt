package com.kapps.mergesort

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import com.kapps.mergesort.InsertionSortActivity
import com.kapps.mergesort.databinding.ActivityChooseAlgorithmBinding
import com.kapps.mergesort.ui.theme.orange
import com.kapps.mergesort.ui.theme.lightred

class ChooseAlgorithm : AppCompatActivity() {

    private lateinit var binding: ActivityChooseAlgorithmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseAlgorithmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        binding.sortButton.setOnClickListener {
            val intent = Intent(this@ChooseAlgorithm, SortActivity::class.java)
            startActivity(intent)
        }

        binding.graphButton.setOnClickListener {
            val intent = Intent(this@ChooseAlgorithm, GraphActivity::class.java)
            startActivity(intent)
        }

        binding.greedyButton.setOnClickListener {
            val intent = Intent(this@ChooseAlgorithm, QuickSortActivity::class.java)
            startActivity(intent)
        }

        binding.dynamicProgrammingButton.setOnClickListener {
            val intent = Intent(this@ChooseAlgorithm, InsertionSortActivity::class.java)
            startActivity(intent)
        }

        binding.backtrackingButton.setOnClickListener {
            val intent = Intent(this@ChooseAlgorithm, SelectionSortActivity::class.java)
            startActivity(intent)
        }


    }
}
