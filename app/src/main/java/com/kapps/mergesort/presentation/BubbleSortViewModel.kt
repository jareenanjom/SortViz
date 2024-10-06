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
    //var listToSort = mutableStateListOf<Int>()

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


    fun startSorting() {
        viewModelScope.launch {
            bubbleSortUseCase(listToSort.map { listUiItem ->
                listUiItem.value
            }.toMutableList()).collect { swapInfo ->
                val currentItemIndex = swapInfo.currentItem
                val nextItemIndex = currentItemIndex + 1

                // Update comparison states
                listToSort[currentItemIndex] = listToSort[currentItemIndex].copy(isCurrentlyCompared = true)
                listToSort[nextItemIndex] = listToSort[nextItemIndex].copy(isCurrentlyCompared = true)

                delay(500) // Wait for some time to visualize the comparison

                if (swapInfo.shouldSwap) {
                    // Perform the swap in the UI list
                    val tempItem = listToSort[currentItemIndex]
                    listToSort[currentItemIndex] = listToSort[nextItemIndex].copy(isCurrentlyCompared = false)
                    listToSort[nextItemIndex] = tempItem.copy(isCurrentlyCompared = false)
                } else {
                    // No swap needed, just reset comparison state
                    listToSort[currentItemIndex] = listToSort[currentItemIndex].copy(isCurrentlyCompared = false)
                    listToSort[nextItemIndex] = listToSort[nextItemIndex].copy(isCurrentlyCompared = false)
                }

                delay(500) // Wait for some time to visualize the swap or no effect
            }
        }
    }
}
