package com.kapps.mergesort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapps.mergesort.presentation.MergeSortViewModel
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange

class SortingActivity : ComponentActivity() {

    private val mergeSortViewModel by viewModels<MergeSortViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        setContent {
            QuickSortTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        itemsIndexed(
                            mergeSortViewModel.mergeSortInfoUiItemList,
                            key = { _, it ->
                                it.id
                            }
                        ){ index, it ->
                            val depthParts = it.sortParts
                            if(index == 0){
                                Text(
                                    "Dividing",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                            if(index == 4){
                                Text(
                                    "Merging",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 26.sp,
                                    modifier = Modifier
                                        .padding(bottom = 10.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                for(part in depthParts){
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .padding(start = if (depthParts.indexOf(part) == 0) 0.dp else 17.dp)
                                            .background(it.color, RoundedCornerShape(10.dp))
                                            .padding(5.dp)

                                    ){
                                        for(numberInformation in part){
                                            if (part.indexOf(numberInformation) != part.size-1){
                                                Text(
                                                    "$numberInformation |",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            }else{
                                                Text(
                                                    "$numberInformation",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            }

                                        }
                                    }
                                }
                            }

                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(gray)
                            .padding(20.dp)
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ){
                        var inputText by remember { mutableStateOf(TextFieldValue()) }

                        OutlinedTextField(
                            value = inputText,
                            onValueChange = {
                                inputText = it
                            },
                            label = {
                                Text("Enter numbers separated by space")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                mergeSortViewModel.listToSort = inputText.text.split(" ").map { it.toInt() }.toMutableList()
                                mergeSortViewModel.startSorting()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = orange,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                "Start sort",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        var isDescriptionVisible by remember { mutableStateOf(false) }
                        var isPseudocodeVisible by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Button(
                                onClick = {
                                    isDescriptionVisible = !isDescriptionVisible
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = orange,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    "Description",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                onClick = {
                                    isPseudocodeVisible = !isPseudocodeVisible
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = orange,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    "Pseudocode",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (isDescriptionVisible) {
                            MergeSortDescription()
                        }

                        if (isPseudocodeVisible) {
                            MergeSortPseudocode()
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MergeSortDescription() {
    // Example description text, modify as per your needs
    Text(
        "Merge sort is one of the most efficient sorting algorithms. \n " +
                "It is based on the divide-and-conquer strategy. " +
                "Merge sort continuously cuts down a list into multiple sublists until each has only one item, then merges those sublists into a sorted list. " +
                "\nTime complexity: O(nlog(n))\n" +
                "Space complexity: O(n)"
    )
}

@Composable
fun MergeSortPseudocode() {
    // Example pseudocode, modify as per your needs
    Text(
        "mergeSort(arr[], l,  r)\n" +
                "If r > l\n" +
                "    1. Find the middle point to divide the array into two halves:\n" +
                "        middle m = l+ (r-l)/2\n" +
                "    2. Call mergeSort for first half:\n" +
                "        Call mergeSort(arr, l, m)\n" +
                "    3. Call mergeSort for second half:\n" +
                "        Call mergeSort(arr, m+1, r)\n" +
                "    4. Merge the two halves sorted in step 2 and 3:\n" +
                "        Call merge(arr, l, m, r)"
    )
}
