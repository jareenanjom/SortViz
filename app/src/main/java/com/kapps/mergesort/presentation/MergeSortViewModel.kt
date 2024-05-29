package com.kapps.mergesort.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapps.mergesort.domain.MergeSortUseCase
import com.kapps.mergesort.presentation.state.MergeSortInfoUiItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class MergeSortViewModel(
    private val mergeSortUseCase: MergeSortUseCase = MergeSortUseCase()
) : ViewModel() {

    var listToSort = mutableListOf<Int>()

    var mergeSortInfoUiItemList = mutableStateListOf<MergeSortInfoUiItem>()
    var currentPseudocodeStep = mutableStateOf(0)

    init {
        for (i in 0 until 8) {
            listToSort.add(
                (10..99).random()
            )
        }
    }

    fun startSorting() {
        mergeSortInfoUiItemList.clear()
        subscribeToSortChanges()
        viewModelScope.launch {
            mergeSort(listToSort, 0, listToSort.size - 1)
        }
    }

    private var job: Job? = null
    private fun subscribeToSortChanges() {
        job?.cancel()
        job = viewModelScope.launch {
            mergeSortUseCase.sortFlow.collect { sortInfo ->
                val depthAlreadyExistListIndex = mergeSortInfoUiItemList.indexOfFirst {
                    it.depth == sortInfo.depth && it.sortState == sortInfo.sortState
                }

                if (depthAlreadyExistListIndex == -1) {
                    mergeSortInfoUiItemList.add(
                        MergeSortInfoUiItem(
                            id = UUID.randomUUID().toString(),
                            depth = sortInfo.depth,
                            sortState = sortInfo.sortState,
                            sortParts = listOf(sortInfo.sortParts),
                            color = Color(
                                (0..255).random(),
                                (0..200).random(),
                                (0..200).random(),
                                255)
                        )
                    )
                } else {
                    val currentPartList = mergeSortInfoUiItemList[depthAlreadyExistListIndex].sortParts.toMutableList()
                    currentPartList.add(sortInfo.sortParts)
                    mergeSortInfoUiItemList.set(
                        depthAlreadyExistListIndex,
                        mergeSortInfoUiItemList[depthAlreadyExistListIndex].copy(sortParts = currentPartList)
                    )
                }

                mergeSortInfoUiItemList.sortedWith(
                    compareBy(
                        {
                            it.sortState
                        },
                        {
                            it.depth
                        }
                    )
                )
            }
        }
    }

    private suspend fun mergeSort(arr: MutableList<Int>, l: Int, r: Int) {
        if (l < r) {
            currentPseudocodeStep.value = 1
            delay(1000)
            val m = l + (r - l) / 2

            currentPseudocodeStep.value = 2
            delay(1000)
            mergeSort(arr, l, m)

            currentPseudocodeStep.value = 3
            delay(1000)
            mergeSort(arr, m + 1, r)

            currentPseudocodeStep.value = 4
            delay(1000)
            merge(arr, l, m, r)
        }
    }

    private suspend fun merge(arr: MutableList<Int>, l: Int, m: Int, r: Int) {
        //jareen forgot to implement
        currentPseudocodeStep.value = 5
        delay(1000)

    }
}
