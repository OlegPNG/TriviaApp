package com.example.triviaapp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaapp.ui.theme.Shapes

@Composable
fun RadioButtons(
    radioOptions: MutableList<String>,
    selectedOption: MutableState<String>,
    modifier: Modifier = Modifier,
    onSelected: (String) -> Unit = { },
    onNextButtonClick: () -> Unit = { }
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption.value),
                            //onClick = { selectedOption.value = text }
                            onClick = { onSelected(text) }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(

                        selected = (text == selectedOption.value),modifier = Modifier.padding(all=8.dp),
                        // onClick = { selectedOption.value = text }
                        onClick = { onSelected(text) }
                    )

                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                }
                /*Button(
                    onClick = onNextButtonClick
                ) {
                    Text(text="Next", fontSize=16.sp)
                }*/
            }
        }
    }
}

@Composable
fun CardButtons(
    modifier: Modifier = Modifier,
    buttonOptions: MutableList<String>,
    selectedOption: MutableState<String>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        buttonOptions.forEach { text ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption.value),
                        onClick = { }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal=16.dp,vertical=8.dp),
                    shape = Shapes.medium,
                    backgroundColor = Color.Red
                ) {
                    Box(
                        modifier = Modifier.padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewCardButtons() {
    val buttonOptions: MutableList<String> = mutableListOf("Option 1", "Option 2", "Option 3", "Option 4")
    val selectedOption: MutableState<String> = remember { mutableStateOf("") }
    CardButtons(
        buttonOptions = buttonOptions,
        selectedOption = selectedOption,
        modifier = Modifier.width(124.dp)
    )
}
