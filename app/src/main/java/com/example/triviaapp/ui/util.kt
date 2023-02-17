package com.example.triviaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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