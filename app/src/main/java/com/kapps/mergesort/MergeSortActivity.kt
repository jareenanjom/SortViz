package com.kapps.mergesort

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kapps.mergesort.presentation.MergeSortViewModel
import com.kapps.mergesort.ui.theme.QuickSortTheme
import com.kapps.mergesort.ui.theme.gray
import com.kapps.mergesort.ui.theme.orange
import kotlin.math.ln
import kotlin.random.Random
class MergeSortActivity : ComponentActivity() {

    private val mergeSortViewModel by viewModels<MergeSortViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = orange.toArgb()
        window.navigationBarColor = orange.toArgb()

        setContent {
            QuickSortTheme {
                MergeSortApp()
            }
        }
    }

    @Composable
    fun MergeSortApp() {
        val context = LocalContext.current
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
                var inputSize by remember { mutableStateOf(10) }

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

                if (isDescriptionVisible) {
                    MergeSortDescription()
                }

                if (isPseudocodeVisible) {
                    MergeSortPseudocode()
                }

                Button(
                    onClick = {
                        isButtonClicked = !isButtonClicked
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
                    MergeSortGraph(
                        generateLineData = { size -> generateRandomLineData(size) },
                        generateTheoreticalLineData = { size -> generateTheoreticalLineData(size) },
                        description = Description().apply {
                            text = "Merge Sort Time Complexity"
                        },
                        inputSize = inputSize
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Slider(
                        value = inputSize.toFloat(),
                        onValueChange = { newValue ->
                            inputSize = newValue.toInt()
                        },
                        valueRange = 1f..500f,
                        steps = 499,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    @Composable
    fun LineChartView(
        context: Context,
        modifier: Modifier = Modifier,
        configureChart: LineChart.() -> Unit = {}
    ) {
        AndroidView(
            factory = { ctx ->
                LineChart(ctx).apply(configureChart)
            },
            modifier = modifier.fillMaxWidth()
        )
    }

    @Composable
    fun MergeSortGraph(
        generateLineData: (Int) -> LineData,
        generateTheoreticalLineData: (Int) -> LineData,
        description: Description,
        inputSize: Int
    ) {
        LineChartView(
            context = LocalContext.current,
            configureChart = {
                data = generateTheoreticalLineData(inputSize).apply {
                    addDataSet(generateLineData(inputSize).getDataSetByIndex(0))
                }
                this.description = description
                invalidate()
            },
            modifier = Modifier.height(300.dp)
        )
    }

    @Composable
    fun MergeSortDescription() {
        val descriptionText = "Merge Sort is a divide-and-conquer algorithm that divides the array into halves, sorts them, and merges them. Its time complexity is O(n log n)."

        Text(
            text = descriptionText,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }

    @Composable
    fun MergeSortPseudocode() {
        val pseudocodeText = AnnotatedString.Builder().apply {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("mergeSort(arr[], l,  r) ")
            }
            append("  ---- T(n) \n")
            append("If r > l\n")
            append("    1. Find the middle point to divide the array into two halves:\n")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("        middle m = l + (r - l) / 2 ")
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
                append("Call mergeSort(arr, m + 1, r)")
            }
            append("  ---- T(n/2) \n")
            append("    4. Merge the two halves sorted in step 2 and 3:\n")
            append("        ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Call merge(arr, l, m, r)")
            }
            append("  ---- O(n) \n\n")
            append("    T(n) = 2 * T(n/2) + O(n) \n")
            append("    ")
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            ) {
                append("Time Complexity = O(n log n)")
            }
        }.toAnnotatedString()

        Text(text = pseudocodeText, fontSize = 16.sp)
    }

    private fun generateRandomLineData(size: Int): LineData {
        val entries = List(size) {
            val x = it.toFloat()
            val y = (x * ln(x) + Random.nextFloat() * 0.2 * x * ln(x)).toFloat()
            Entry(x, y)
        }
        val dataSet = LineDataSet(entries, "Test Points").apply {
            color = Color.Blue.toArgb()
            valueTextColor = Color.Blue.toArgb()
        }
        return LineData(dataSet)
    }

    private fun generateTheoreticalLineData(size: Int): LineData {
        val entries = List(size) {
            val x = it.toFloat()
            Entry(x, x * ln(x))
        }
        val dataSet = LineDataSet(entries, "O(n log n)").apply {
            color = Color.Red.toArgb()
            valueTextColor = Color.Red.toArgb()
        }
        return LineData(dataSet)
    }
}
