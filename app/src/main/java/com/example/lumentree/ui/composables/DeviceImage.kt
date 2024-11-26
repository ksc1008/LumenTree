package com.example.lumentree.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.lumentree.R

@Composable
fun DeviceImage(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.img_mood_light),
        modifier = modifier,
        contentDescription = "Light Image"
    )
}