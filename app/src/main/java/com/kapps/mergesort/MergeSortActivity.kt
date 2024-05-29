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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kapps.mergesort.presentation.MergeSortViewModel
import com.kapps.mergesort.presentation.MergeSortViewPseudo
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
        val mergeSortViewModel: MergeSortViewModel = viewModel()
        val mergeSortViewPseudo: MergeSortViewPseudo = viewModel()
        val currentPseudocodeStep by mergeSortViewPseudo.currentPseudocodeStep

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gray)
                .padding(1.dp)
        ) {
            // Element Visualization Process
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
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
                            fontSize = 22.sp,
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
                            fontSize = 22.sp,
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

            // Pseudocode Visualization
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(5.dp)
            ) {
                MergeSortPseudocode(currentStep = currentPseudocodeStep)
            }

            // Buttons and Additional Components
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gray),
                horizontalAlignment = Alignment.CenterHorizontally
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
                        mergeSortViewPseudo.startSortingPseudo()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = orange,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Start Sort",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                var isDescriptionVisible by remember { mutableStateOf(false) }
                var isButtonClicked by remember { mutableStateOf(false) }
                var inputSize by remember { mutableStateOf(10) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            isDescriptionVisible = !isDescriptionVisible
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = orange,
                            contentColor = Color.White
                        ),
                                modifier = Modifier
                                .fillMaxWidth()
                    ) {
                        Text(
                            "Description",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (isDescriptionVisible) {
                    MergeSortDescription()
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
                        fontSize = 15.sp,
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


                    Slider(
                        value = inputSize.toFloat(),
                        onValueChange = { newValue ->
                            inputSize = newValue.toInt()
                        },
                        valueRange = 1f..50f,
                        steps = 49,
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
                val theoreticalLineData = generateTheoreticalLineData(inputSize)
                val randomLineData = generateLineData(inputSize)

                data = LineData().apply {
                    addDataSet(theoreticalLineData.getDataSetByIndex(0))
                    addDataSet(randomLineData.getDataSetByIndex(0))
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
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }

    @Composable
    fun MergeSortPseudocode(currentStep: Int) {
        val pseudocode = listOf(
            "MergeSort(arr, left, right):",
            "    if left > right return",
            "    Find mid point to divide array into two halves:\nmid = (left + right) / 2",
            "    Call mergeSort for first half:\nmergeSort(arr, left, mid)",
            "    Call mergeSort for second half:\nmergeSort(arr, mid + 1, right)",
            "    Merge the two halves sorted:\nmerge(arr, left, mid, right)"
        )

        val highlightedPseudocode = pseudocode.mapIndexed { index, line ->
            if (index == currentStep) {
                AnnotatedString.Builder().apply {
                    withStyle(style = SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)) {
                        append(line)
                    }
                }.toAnnotatedString()
            } else {
                AnnotatedString(line)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            for (line in highlightedPseudocode) {
                Text(text = line, fontSize = 14.sp, color = Color.White)
            }
        }
    }

    private fun generateRandomLineData(size: Int): LineData {
        val entries = List(size) {
            val x = (it + 1).toFloat()
            val y = (x * ln(x) + Random.nextFloat() * 0.2 * x * ln(x)).toFloat()
            Entry(x, y)
        }
        val dataSet = LineDataSet(entries, "Test Points").apply {
            color = Color.Blue.toArgb() // Line color
            valueTextColor = Color.Blue.toArgb()
            setCircleColor(Color.Green.toArgb()) // Plot points color
            circleRadius = 4f // Plot points radius
        }
        return LineData(dataSet)
    }

    private fun generateTheoreticalLineData(size: Int): LineData {
        val entries = List(size) {
            val x = (it + 1).toFloat()
            Entry(x, x * ln(x))
        }
        val dataSet = LineDataSet(entries, "O(n log n)").apply {
            color = Color.Red.toArgb() // Line color
            valueTextColor = Color.Red.toArgb()
            setCircleColor(Color.Yellow.toArgb()) // Plot points color
            circleRadius = 4f // Plot points radius
        }
        return LineData(dataSet)
    }

    private fun simulateMergeSortTime(size: Int): Float {
        val random = Random(size)
        return random.nextFloat() * size * ln(size.toDouble()).toFloat()
    }

}