package com.kapps.mergesort.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapps.mergesort.domain.BubbleSortUseCase
import com.kapps.mergesort.domain.SelectionSortUseCase
import com.kapps.mergesort.domain.swap
import com.kapps.mergesort.presentation.state.BubbleListUiItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SelectionSortViewModel(
    private val selectionSortUseCase: SelectionSortUseCase = SelectionSortUseCase()
) : ViewModel() {
    private fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
    var listToSort = mutableStateListOf<BubbleListUiItem>()

    fun initializeListWithInput(input: String) {
        listToSort.clear()
        val numbers = input.split(",").map { it.trim() }
        for ((index, numberString) in numbers.withIndex()) {
            val rnd = Random()
            val number = numberString.toIntOrNull() ?: continue
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
            selectionSortUseCase(listToSort.map { it.value }.toMutableList()).collect { swapInfo ->
                val currentItemIndex = swapInfo.currentItem
                val minItemIndex = swapInfo.minItemIndex

                // Highlight the currently compared item
                listToSort.forEachIndexed { index, bubbleListUiItem ->
                    listToSort[index] = bubbleListUiItem.copy(isCurrentlyCompared = index == currentItemIndex || index == minItemIndex)
                }

                // Perform the swap if necessary
                if (currentItemIndex != minItemIndex) {
                    listToSort.swap(currentItemIndex, minItemIndex)
                }
            }
        }
    }

}
