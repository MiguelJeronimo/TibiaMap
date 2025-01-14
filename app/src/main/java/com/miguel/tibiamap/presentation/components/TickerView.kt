package com.miguel.tibiamap.presentation.components

import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView

@Composable
fun TickerView(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    size: Float
    //font: Typeface? = null,
){
    AndroidView(
        factory = { context ->
            TickerView(context).apply {
                setCharacterLists(TickerUtils.provideNumberList())
                textSize = size
                textColor = color.hashCode()
                animationDuration = 1000
                //typeface = font
            }
        },
        update = { view ->
            view.text = text
        },
        modifier = modifier
    )
}