package com.artbird.onsite.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artbird.onsite.domain.ImparialLength

@Composable
fun ImperialLengthInput(
    label: String,
    len: ImparialLength,
    onChange: (len: ImparialLength) -> Unit,
    modifier: Modifier = Modifier
        .width(200.dp)
//        .fillMaxWidth()
//        .padding(top = 8.dp, bottom= 8.dp)
){
    Column(modifier=Modifier.width(200.dp)) {
        Row(modifier=Modifier.width(200.dp)){
            Label2(text = label,
//            modifier = Modifier
//                .padding(start = 8.dp, top = 8.dp, bottom = 0.dp, end = 8.dp)
//                .width(60.dp)
            )
        }

        Row(
            modifier = modifier.width(200.dp)
        ) {

            NumberInput(
                value = len.inches,
                onValueChange = {
                    onChange(ImparialLength(it, len.leftover))
                                },
    //            label = "Inches",
                trailingIcon = { Text("in")},
                modifier = Modifier
    //                .weight(1f)
                    .padding(end = 8.dp)
                    .width(100.dp)
            )
            NumberInput(
                value = len.leftover,
                onValueChange = {
                    onChange(ImparialLength(len.inches, it))
                                },
    //            label = "And",
                trailingIcon = { Text("/16")},
                modifier = Modifier
    //                .weight(1f)
                    .padding(end = 8.dp)
                    .width(80.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImperialLengthInput(){
    var len by remember { mutableStateOf(ImparialLength("23", "11")) }

    fun handleChange(l: ImparialLength){
        len.inches = l.inches
        len.leftover = l.leftover
    }
    Column() {
//        Row(){
//            ImperialLengthInput("Left", len, ::handleChange,
//                modifier = Modifier.weight(1f).padding(end = 8.dp))
//            ImperialLengthInput("Right", len, ::handleChange,
//                modifier = Modifier.weight(1f).padding(start = 8.dp))
//        }
//
//        Row(){
//            ImperialLengthInput("Top", len, ::handleChange,
//                modifier = Modifier.weight(1f).padding(end = 8.dp))
//            ImperialLengthInput("Bottom", len, ::handleChange,
//                modifier = Modifier.weight(1f).padding(end = 8.dp))
//        }

            ImperialLengthInput("Left", len, ::handleChange)
            ImperialLengthInput("Right", len, ::handleChange)

            ImperialLengthInput("Top", len, ::handleChange)
            ImperialLengthInput("Bottom", len, ::handleChange)
    }
}