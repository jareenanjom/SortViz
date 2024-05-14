package com.kapps.mergesort

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapps.mergesort.presentation.MergeSortViewModel
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class SortingActivity : ComponentActivity() {

    private val mergeSortViewModel by viewModels<MergeSortViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val context = LocalContext.current
        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        setContent {
            QuickSortTheme {
                val context = LocalContext.current
                var xCoordinate = mutableListOf<Float>()
                var yCoordinate = mutableListOf<Float>()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gray)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter),
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(
                            mergeSortViewModel.mergeSortInfoUiItemList,
                            key = { _, it ->
                                it.id
                            }
                        ) { index, it ->
                            val depthParts = it.sortParts
                            if (index == 0) {
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
                            if (index == 4) {
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
                            ) {
                                for (part in depthParts) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .padding(start = if (depthParts.indexOf(part) == 0) 0.dp else 17.dp)
                                            .background(it.color, RoundedCornerShape(10.dp))
                                            .padding(5.dp)

                                    ) {
                                        for (numberInformation in part) {
                                            if (part.indexOf(numberInformation) != part.size - 1) {
                                                Text(
                                                    "$numberInformation |",
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    fontSize = 19.sp
                                                )
                                            } else {
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
                    ) {
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
                                mergeSortViewModel.listToSort =
                                    inputText.text.split(" ").map { it.toInt() }.toMutableList()
                                mergeSortViewModel.startSorting()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = orange,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Start sort",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        var isDescriptionVisible by remember { mutableStateOf(false) }
                        var isPseudocodeVisible by remember { mutableStateOf(false) }
                        var isButtonClicked by remember { mutableStateOf(false) }
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


                        Button(
                            onClick = {
                                isButtonClicked = !isButtonClicked
                                /*val kMax = 20 // Adjust the maximum value of k
                                val step = 100 // Adjust the step value
                                xCoordinate = mutableListOf()
                                yCoordinate = mutableListOf()

                                for (k in 1..kMax) {
                                    val arr = IntArray(k * step) { Random.nextInt(200000) }
                                    val timeTaken = measureTimeMillis { (arr) } / 1000f // Convert milliseconds to seconds
                                    println(timeTaken)
                                    xCoordinate.add((k * step).toFloat())
                                    yCoordinate.add(timeTaken)
                                }

                                // Plotting graph
                                val canvas = Canvas()
                                drawGraph(canvas, xCoordinate, yCoordinate, context)*/
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = orange,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Time Complexity Graph",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (isButtonClicked) {
                            ImageAfterButtonClick()
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
        val pseudocodeText = AnnotatedString.Builder().apply {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("mergeSort(arr[], l,  r) ")
            }
            append("  ---- T(n) \n")
            append("If r > l\n")
            append("    1. Find the middle point to divide the array into two halves:\n")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("        middle m = l+ (r-l)/2 ")
            }
            append("  ---- O(1) \n")
            append("    2. Call mergeSort for first half:\n")
            append("        ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Call mergeSort(arr, l, m)")
            }
            append("  ---- T(n/2) \n")
            append("    3. Call mergeSort for second half:\n")
            append("        ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Call mergeSort(arr, m+1, r)")
            }
            append("  ---- T(n/2) \n")
            append("    4. Merge the two halves sorted in step 2 and 3:\n")
            append("        ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Call merge(arr, l, m, r)")
            }
            append("  ---- O(n) \n\n")
            append("    T(n) = 2*T(n/2)+O(n) \n")
            append("    ")
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, color = Color.Gray)) {
                append("Time Complexity= O(nlogn)")
            }
        }.toAnnotatedString()

        Text(text = pseudocodeText, fontSize = 16.sp)
    }

    private fun drawGraph(
        canvas: Canvas,
        xValues: List<Float>,
        yValues: List<Float>,
        context: android.content.Context
    ) {
        val paint = Paint()
        paint.color = Color.hashCode()
        paint.strokeWidth = 4f

        // Finding the maximum value of x and y to scale the graph
        val maxX = xValues.maxOrNull() ?: 0f
        val maxY = yValues.maxOrNull() ?: 0f

        val scale = 0.9f // Scale factor to fit the graph within the canvas

        // Drawing x-axis and y-axis
        canvas.drawLine(100f, 100f, 100f, 700f, paint) // y-axis
        canvas.drawLine(100f, 700f, 900f, 700f, paint) // x-axis

        // Drawing points
        for (i in xValues.indices) {
            val x = 100 + (xValues[i] / maxX) * 800 * scale
            val y = 700 - (yValues[i] / maxY) * 600 * scale
            canvas.drawCircle(x, y, 5f, paint)
        }

        // Drawing lines
        for (i in 0 until xValues.size - 1) {
            val startX = 100 + (xValues[i] / maxX) * 800 * scale
            val startY = 700 - (yValues[i] / maxY) * 600 * scale
            val endX = 100 + (xValues[i + 1] / maxX) * 800 * scale
            val endY = 700 - (yValues[i + 1] / maxY) * 600 * scale
            canvas.drawLine(startX, startY, endX, endY, paint)
        }


    }

    @Composable
    fun ImageAfterButtonClick() {
        val painter: Painter = painterResource(id = R.drawable.mergesortgraph)

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
        )
    }
}
