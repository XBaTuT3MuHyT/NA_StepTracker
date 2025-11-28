package com.example.na_steptracker.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.na_steptracker.R
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun StatScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        WeeklyChart()
    }
}

@Composable
fun WeeklyChart() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Row(
                modifier = Modifier.padding(start = 22.dp, bottom = 46.dp)
            ) {
                Text(
                    text = stringResource(R.string.stat_weekly_chart_title),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            SimpleBarChart(days)
        }
    }
}

@Composable
fun SimpleBarChart(
    days: List<Day>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    targetValue: Int = 6000,
    targetLineColor: Color = MaterialTheme.colorScheme.tertiary,
    onTargetLineColor: Color = MaterialTheme.colorScheme.onTertiary,
    axisColor: Color = MaterialTheme.colorScheme.secondary
) {
    val maxValue = maxOf(days.maxOfOrNull { it.steps } ?: 1, targetValue)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row() {
            Canvas(
                modifier = modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val lineY = size.height - (targetValue.toFloat() / maxValue) * size.height
                val rectHeight = 20.dp.toPx()
                val rectWidth = size.width
                val rectTop = lineY - rectHeight / 2

                drawRect(
                    color = targetLineColor,
                    topLeft = Offset(0f, rectTop),
                    size = Size(rectWidth, rectHeight)
                )

                drawContext.canvas.nativeCanvas.apply {
                    val textPaint = android.graphics.Paint().apply {
                        color = onTargetLineColor.toArgb()
                        textSize = 12.sp.toPx()
                    }

                    val textWidth = textPaint.measureText("$targetValue")

                    val textX = rectWidth / 2 - textWidth / 2

                    drawText(
                        "$targetValue",
                        textX,
                        lineY + 4.dp.toPx(),
                        textPaint
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5.5f)
            ) {
                Canvas(
                    modifier = modifier
                        .height(120.dp)
                        .fillMaxWidth()
                ) {
                    val barWidth = size.width / (days.size * 2f)
                    val space = barWidth

                    days.forEachIndexed { index, day ->
                        val steps = day.steps
                        val barHeight = (steps / maxValue.toFloat()) * size.height
                        val alphaMod: Float = if (steps > targetValue) 1f else 0.2f

                        drawRoundRect(
                            alpha = alphaMod,
                            color = barColor,
                            topLeft = Offset(
                                x = index * (barWidth + space) + space * 0.5f,
                                y = size.height - barHeight
                            ),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(
                                x = 5.dp.toPx(),
                                y = 5.dp.toPx()
                            )
                        )

                        targetValue.let { target ->
                            val lineY = size.height - (target.toFloat() / maxValue) * size.height

                            drawLine(
                                color = targetLineColor,
                                alpha = 1f,
                                start = Offset(0f, lineY),
                                end = Offset(size.width, lineY),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f))
                            )
                            drawLine(
                                alpha = 0.05f,
                                color = axisColor,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx(),
                            )
                        }
                    }
                }
                Row(
                    Modifier
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    days.forEach {
                        val steps = it.steps
                        val alphaMod: Float = if (steps > targetValue) 1f else 0.5f

                        Text(
                            text = it.dayOfTheWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault()
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .alpha(alphaMod),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WeeklyStepsChartPreview() {
    MaterialTheme {
        WeeklyChart()
    }
}

