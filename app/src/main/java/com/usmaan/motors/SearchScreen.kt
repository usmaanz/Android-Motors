package com.usmaan.motors

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.usmaan.motors.ui.theme.fontStyleListText
import com.usmaan.motors.ui.theme.fontStyleListTitle
import com.usmaan.motors.ui.theme.fontStylePrice
import com.usmaan.motors.ui.theme.fontStyleYear

@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = getViewModel()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp)) {
        SearchView(viewModel)
        ResultView(viewModel)
    }
}

@Composable
private fun SearchView(viewModel: SearchViewModel) {
    val motors = viewModel.motors
    val makesSelectedIndex = remember { mutableStateOf(0) }
    val modelsSelectedIndex = remember { mutableStateOf(0) }
    val yearSelectedIndex = remember { mutableStateOf(0) }

    Dropdown(motors.keys.toList(), makesSelectedIndex) { make ->
        viewModel.selectedMake.value = make
        modelsSelectedIndex.value = 0
    }
    Spacer(modifier = Modifier.height(5.dp))
    Dropdown(viewModel.motors[viewModel.selectedMake.value]!!, modelsSelectedIndex) {
        viewModel.selectedModel.value = it
    }
    Spacer(modifier = Modifier.height(5.dp))
    Dropdown(viewModel.years, yearSelectedIndex) {
        viewModel.selectedYear.value = it
    }
    Spacer(modifier = Modifier.height(5.dp))

    ButtonSearch(viewModel)

    Spacer(
        Modifier
            .fillMaxWidth()
            .height(10.dp))
    Divider(Modifier.fillMaxWidth())
}

@Composable
fun Dropdown(items: List<String>, selectedIndex: MutableState<Int>, onSelected: (String) -> Unit) {
    if (items.isEmpty()) {
        return
    }

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)
        .border(width = 2.dp, color = Color.Black)
        .padding(5.dp)) {
        Text(items[selectedIndex.value],modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = true }))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded = false
                    onSelected(s)
                }) {
                    Text(text = s)
                }
            }
        }
    }
}

@Composable
private fun EmptyResult() {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier, text = stringResource(id = R.string.empty_result))
    }
}

@Composable
private fun ResultView(viewModel: SearchViewModel) {
    when (viewModel.uiMotorsState.value) {
        is SearchResult.Loading -> {}
        is SearchResult.Empty -> {
            EmptyResult()
        }
        is SearchResult.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (viewModel.uiMotorsState.value is SearchResult.Success) {
                    items((viewModel.uiMotorsState.value as SearchResult.Success).data) { motor ->
                        ResultItem(motor = motor)
                    }
                }
            }
        }
        is SearchResult.Error -> {}
    }
}

@Composable
private fun ResultItem(motor: Motor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { },
        shape = RoundedCornerShape(15),
        backgroundColor = Color(233, 232, 232, 240)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = motor.name,
                style = fontStyleListTitle,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Year: ${motor.year}",
                            style = fontStyleYear,
                            modifier = Modifier.align(Alignment.TopStart)

                        )
                        Text(
                            text = motor.price,
                            style = fontStylePrice,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                    Text(
                        text = motor.title,
                        maxLines = 1,
                        style = fontStyleListText,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}


@Composable
private fun ButtonSearch(viewModel: SearchViewModel) {
    Button(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.search() }) {
        Text(text = stringResource(id = R.string.button_search))
    }
}