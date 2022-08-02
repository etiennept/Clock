package com.example.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotateRad
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.PI

@Composable
fun App(    ) {
    MaterialTheme( if (isSystemInDarkTheme() ) darkColors( ) else lightColors() ) {
        Surface( Modifier.fillMaxSize().background(MaterialTheme.colors.background) ) {
            ClockView(clockSize)
        }
    }


}


expect val clockSize  : Dp


fun Int.toPrint(int: Int) = (this * 2 * PI / int.toDouble()).toFloat()


@Composable
fun ClockView(size:Dp , isDarkTheme : Boolean  =isSystemInDarkTheme() ) {

    var instant by remember {
        mutableStateOf(Clock.System.now())
    }
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            instant = Clock.System.now()
        }

    }
    Column {
        Text(text = dateTime.run {
            "$hour : $minute : $second le ${this.dayOfWeek.name} ${this.dayOfMonth} ${this.month.name}"
        }, fontSize = 20.sp)
        Box(Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .width(size)
                    .height(size)
                    .align(Alignment.Center)
            ) {
                for (i in 0 until 60) {
                    rotateRad( i.toPrint(60) , center ){
                        drawCircle(
                            color = if(isDarkTheme ) White else  Black   ,
                            center = Offset(x = this.size.width / 2 , y= this.size.height /16  ),
                            radius =  if( i % 5 == 0 ) 8F else 4F
                        )
                    }
                }
                hand(dateTime.second.toPrint(60), if (isDarkTheme) Magenta else  Blue)
                hand(rad = dateTime.minute.toPrint(60), if (isDarkTheme) Cyan else  Red )
                hand(rad = (dateTime.hour % 12).toPrint(12), if(isDarkTheme) White else  Black)
            }
        }
    }


}

fun DrawScope.hand(rad: Float, color: Color) {
    rotateRad(rad, center) {
        drawLine(
            start = center,
            end = Offset(
                x = size.width / 2,
                y = size.height / 8
            ),
            color = color,
            strokeWidth = 5F
        )

    }
}
