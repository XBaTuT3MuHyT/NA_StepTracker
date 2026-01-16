package com.example.na_steptracker.widget

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceNode
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.background
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment

import androidx.glance.layout.Row
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width

import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.na_steptracker.R

data class WidgetModel(
    val steps: Int,
    val target: Int,
    @param:DrawableRes val imageId: Int,
    val temp: String,
) {
    companion object {
        val mock
            get() = WidgetModel(
                steps = 7230,
                target = 8000,
                imageId = R.drawable.outline_wb_sunny_24,
                temp = "23°",
            )
    }
}

class StepsWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {

        provideContent {
            WidgetContent(
                widgetModel = WidgetModel.mock
            )
        }
    }
}

@Composable
fun WidgetContent(
    widgetModel: WidgetModel
) {
    Row(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                day = Color(0xFFFFFFFF),
                night = Color(0xF0000000),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(

        ) {
            Image(
                provider = ImageProvider(widgetModel.imageId),
                contentDescription = null,
                modifier = GlanceModifier
                    .size(64.dp),
                colorFilter = ColorFilter.tint(ColorProvider(day = Color.Black, night = Color.White)),
            )

            Spacer(modifier = GlanceModifier.width(4.dp))
            Text(

                text = "${widgetModel.temp}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = ColorProvider(
                        day = Color(0xFF1C1B1F),
                        night = Color(0xFFE6E1E5)
                    ),
                )
            )
        }

//        Spacer(modifier = GlanceModifier.defaultWeight())

        Column(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "${widgetModel.steps}/    ",
                style = TextStyle(
                    fontSize = 26.sp,
                    color = ColorProvider(
                        day = Color(0xFF1C1B1F),
                        night = Color(0xFFE6E1E5)
                    )
                )
            )

            Spacer(modifier = GlanceModifier.size(6.dp))

            Text(
                text = "      ${widgetModel.target} шагов",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = ColorProvider(
                        day = Color(0x8F1C1B1F),
                        night = Color(0x8FE6E1E5)
                    )
                )
            )
        }

    }
}
