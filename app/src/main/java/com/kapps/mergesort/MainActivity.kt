package com.kapps.mergesort

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        setContent {
            QuickSortTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(40.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            // Start SortingActivity
                            val intent = Intent(this@MainActivity, SortingActivity::class.java)
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = orange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Merge Sort",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            // Start SortingActivity
                            val intent = Intent(this@MainActivity, BubbleSortActivity::class.java)
                            startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = orange,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Bubble Sort",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                        Button(
                            onClick = {
                                // Start SortingActivity
                                val intent = Intent(this@MainActivity, QuickSortActivity::class.java)
                                startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = orange,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Quick Sort",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                            Button(
                                onClick = {
                                    // Start SortingActivity
                                    val intent = Intent(this@MainActivity, SortingActivity::class.java)
                                    startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = orange,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Insertion Sort",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                                Button(
                                    onClick = {
                                        // Start SortingActivity
                                        val intent = Intent(this@MainActivity, SelectionSortActivity::class.java)
                                        startActivity(intent)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = orange,
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "Selection Sort",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                    Button(
                                        onClick = {
                                            // Start SortingActivity
                                            val intent = Intent(this@MainActivity, SelectionSortActivity::class.java)
                                            startActivity(intent)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = orange,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            "Heap Sort",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                }
            }
        }
    }
}