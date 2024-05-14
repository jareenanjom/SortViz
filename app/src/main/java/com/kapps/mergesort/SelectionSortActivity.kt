package com.kapps.mergesort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapps.mergesort.presentation.SortViewModel
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange
import androidx.compose.runtime.*
import androidx.compose.material.Text
import com.kapps.mergesort.presentation.SelectionSortViewModel


class SelectionSortActivity : ComponentActivity() {

    private val sortViewModel = SelectionSortViewModel()


    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickSortTheme {
                window.statusBarColor = orange.toArgb()
                window.navigationBarColor = orange.toArgb()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(20.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    // EditText field for user input
                    var inputText by remember { mutableStateOf("") }
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        label = { Text("Enter comma-separated numbers") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White
                        ),

                        )
                    Button(onClick = {
                        // Pass the input text to the view model to initialize the list
                        sortViewModel.initializeListWithInput(inputText)
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = orange)
                    ) {
                        Text(
                            "Enter",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }

                    Button(onClick = {
                        sortViewModel.startSorting()
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = orange)
                    ) {
                        Text(
                            "Sort List",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        items(
                            sortViewModel.listToSort,
                            key = {
                                it.id
                            }
                        ){
                            val borderStroke = if(it.isCurrentlyCompared){
                                BorderStroke(width = 3.dp,Color.White,)
                            }else{
                                BorderStroke(width = 0.dp,Color.Transparent)
                            }
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(it.color, RoundedCornerShape(15.dp))
                                    .border(borderStroke, RoundedCornerShape(15.dp))
                                    .animateItemPlacement(
                                        tween(300)
                                    ),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    "${it.value}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                // Handle Description button click
                                // Show description
                                // This is where you would handle showing the description
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = orange)
                        ) {
                            Text("Description")
                        }

                        Button(
                            onClick = {
                                // Handle Code button click
                                // Show pseudocode
                                // This is where you would handle showing the pseudocode
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = orange)
                        ) {
                            Text("Code")
                        }
                    }

                    // Description or Code text section
                    // This is where you would display the description or pseudocode
                }
            }
        }
    }
}
