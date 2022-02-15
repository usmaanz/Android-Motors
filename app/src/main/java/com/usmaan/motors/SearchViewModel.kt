package com.usmaan.motors

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    var uiMotorsState: MutableState<SearchResult> = mutableStateOf(SearchResult.Empty)
    var defaultMake = "Select Make"
    var defaultModel = "Select Model"
    val motors: HashMap<String, List<String>> = hashMapOf(
        defaultMake to listOf(defaultModel),
        "Nissan" to listOf(defaultModel, "Micra", "Juke", "Qashqai"),
        "BMW" to listOf(defaultModel, "1 Series", "3 Series", "X5"),
        "Audi" to listOf(defaultModel, "A1", "A3", "A4")
    )

    var years = listOf("Select Year", "2000", "2010", "2020")

    val selectedMake: MutableState<String> = mutableStateOf("Select Make")
    val selectedModel: MutableState<String> = mutableStateOf("")
    val selectedYear: MutableState<String> = mutableStateOf("")

    init {
        getAllResults()
    }

    fun search() {
        uiMotorsState.value = SearchResult.Loading

        val selectedMakeParam = getSelectedParam(selectedMake)
        val selectedModelParam = getSelectedParam(selectedModel)

        viewModelScope.launch {
            searchRepository.getSearch(
                selectedMakeParam,
                selectedModelParam,
                selectedYear.value,
                ::onMotorsReceived,
                ::onMotorsError
            )
        }
    }

    private fun getSelectedParam(param: MutableState<String>): String {
        return if (param.value == defaultMake || param.value == defaultModel) {
            ""
        } else {
            param.value
        }
    }

    private fun getAllResults() {
        viewModelScope.launch {
            searchRepository.getAll(
                ::onMotorsReceived,
                ::onMotorsError
            )
        }
    }

    private fun onMotorsReceived(motors: List<Motor>) {
        uiMotorsState.value = SearchResult.Success(motors)
    }

    private fun onMotorsError(error: String) {
        uiMotorsState.value = SearchResult.Error
    }
}