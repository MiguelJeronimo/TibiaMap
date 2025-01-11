package com.miguel.tibiamap.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolTipMaker(
    modifier: Modifier = Modifier,
    description: String,
    icon: Int,
    tooltipState: TooltipState,
    scope: CoroutineScope
){
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        state = tooltipState,
        tooltip = {ToolTipMakerDescription(description = description, icon = icon)}
    ) {
        Column(modifier.padding(5.dp)) {
            IconButton(
                onClick = { scope.launch { tooltipState.show() } },
                modifier = Modifier.size(10.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                )
            }
        }
    }
}

@Composable
fun ToolTipMakerDescription(modifier: Modifier = Modifier, description: String, icon: Int){
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = description,
                modifier = Modifier.size(25.dp),
            )
            Text(
                text = description,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp
            )
        }
    }
}