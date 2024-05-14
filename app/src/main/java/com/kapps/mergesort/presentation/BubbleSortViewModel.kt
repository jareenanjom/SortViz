package com.kapps.mergesort.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapps.mergesort.domain.BubbleSortUseCase
import com.kapps.mergesort.presentation.state.BubbleListUiItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SortViewModel(
    private val bubbleSortUseCase: BubbleSortUseCase = BubbleSortUseCase()
) :ViewModel() {

    var listToSort = mutableStateListOf<BubbleListUiItem>()

    // Function to initialize the list with user input
    fun initializeListWithInput(input: String) {
        // Clear existing list
        listToSort.clear()

        // Split input by commas and trim whitespace
        val numbers = input.split(",").map { it.trim() }

        // Create ListUiItem objects for each number
        for ((index, numberString) in numbers.withIndex()) {
            val rnd = Random()
            val number = numberString.toIntOrNull() ?: continue // Skip invalid numbers
            val listUiItem = BubbleListUiItem(
                id = index,
                isCurrentlyCompared = false,
                value = number,
                color = Color(
                    255,
                    rnd.nextInt(256),
                    rnd.nextInt(256),
                    255
                )
            )
            listToSort.add(listUiItem)
        }
    }


    fun startSorting(){
        viewModelScope.launch {
            bubbleSortUseCase(listToSort.map { listUiItem ->
                listUiItem.value
            }.toMutableList()).collect{ swapInfo ->
                val currentItemIndex = swapInfo.currentItem
                listToSort[currentItemIndex] = listToSort[currentItemIndex].copy(isCurrentlyCompared = true)
                listToSort[currentItemIndex+1] = listToSort[currentItemIndex+1].copy(isCurrentlyCompared = true)

                if(swapInfo.shouldSwap){
                    val firstItem = listToSort[currentItemIndex].copy(isCurrentlyCompared = false)
                    listToSort[currentItemIndex] = listToSort[currentItemIndex+1].copy(isCurrentlyCompared = false)
                    listToSort[currentItemIndex+1] = firstItem
                }
                if(swapInfo.hadNoEffect){
                    listToSort[currentItemIndex] = listToSort[currentItemIndex].copy(isCurrentlyCompared = false)
                    listToSort[currentItemIndex+1] = listToSort[currentItemIndex+1].copy(isCurrentlyCompared = false)
                }
            }
        }
    }
}
