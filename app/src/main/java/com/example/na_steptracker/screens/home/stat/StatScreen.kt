package com.example.na_steptracker.screens.home.stat

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.na_steptracker.App
import com.example.na_steptracker.R
import com.example.na_steptracker.components.RecordCard
import com.example.na_steptracker.components.RecordCardModel


@Composable
fun StatScreen() {
    val app = LocalContext.current.applicationContext as App

    val viewModel: StatViewModel = viewModel(
        factory = StatViewModelFactory(app.stepsRepository, app.weatherRepository)
    )

    val weeklyChartModel by viewModel.weeklyChartModel.collectAsState()
    val dailyChartModel by viewModel.dailyChartModel.collectAsState()
    val recordsModel by viewModel.records.collectAsState()

    Content(weeklyChartModel = weeklyChartModel, dailyChartModel = dailyChartModel, recordsModel = recordsModel)
}

@Composable
fun Content(
    weeklyChartModel: WeeklyChartModel,
    dailyChartModel: DailyChartModel,
    recordsModel: RecordsModel
) {
    val cols = 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(cols),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        item(span = { GridItemSpan(cols) }) {
            WeeklyChart(weeklyChartModel)
        }
        item(span = { GridItemSpan(cols) }) {
            DailyChart(dailyChartModel)
        }

        item(span = { GridItemSpan(cols) }) {
            Text(
                text = stringResource(R.string.stat_grid_title),
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp)
                    .fillMaxWidth()
                    .alpha(0.5f),
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(recordsModel.records) { record ->
            RecordCard(record = record)
        }
    }
}

@Composable
fun WeeklyChart(weeklyChartModel: WeeklyChartModel) {

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
            SimpleBarChart(
                chartPoints = weeklyChartModel.days,
                targetValue = weeklyChartModel.target,
                canvasHeight = 120.dp
            )
        }
    }
}

@Composable
fun DailyChart(dailyChartModel: DailyChartModel) {

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
                    text = stringResource(R.string.stat_daily_chart_title),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            SimpleBarChart(
                chartPoints = dailyChartModel.hours,
                targetValue = null,
                canvasHeight = 60.dp
            )
        }
    }
}

@Composable
fun SimpleBarChart(
    chartPoints: List<ChartPoint>,
    targetValue: Int?,
    canvasHeight: Dp,
    modifier: Modifier = Modifier,
    minVisibleHeight: Int = 300,
    barColor: Color = MaterialTheme.colorScheme.primary,
    targetLineColor: Color = MaterialTheme.colorScheme.tertiary,
    onTargetLineColor: Color = MaterialTheme.colorScheme.onTertiary,
    axisColor: Color = MaterialTheme.colorScheme.secondary,
) {
    val maxValue = maxOf(chartPoints.maxOfOrNull { it.value } ?: minVisibleHeight,
        targetValue ?: minVisibleHeight)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row() {
            Canvas(
                modifier = modifier
                    .height(canvasHeight)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (targetValue != null) {
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
                        val textPaint = Paint().apply {
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

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5.5f)
            ) {
                Canvas(
                    modifier = modifier
                        .height(canvasHeight)
                        .fillMaxWidth()
                ) {
                    val barWidth = size.width / (chartPoints.size * 2f)
                    val space = barWidth

                    chartPoints.forEachIndexed { index, day ->
                        val value = day.value
                        val barHeight = (value / maxValue.toFloat()) * size.height
                        val alphaMod: Float = if (value > (targetValue ?: -1)) 1f else 0.2f

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

                    }
                    targetValue?.let { target ->
                        val lineY = size.height - (target.toFloat() / maxValue) * size.height

                        drawLine(
                            color = targetLineColor,
                            alpha = 1f,
                            start = Offset(0f, lineY),
                            end = Offset(size.width, lineY),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f))
                        )
                    }
                    drawLine(
                        alpha = 0.05f,
                        color = axisColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
                Row(
                    Modifier
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    chartPoints.forEach {
                        val value = it.value
                        val alphaMod: Float = if (value > (targetValue ?: -1)) 1f else 0.5f

                        it.label?.let {
                            Text(
                                it,
                                modifier = Modifier
                                    .weight(1f)
                                    .alpha(alphaMod),
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                            )
                        }
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
        WeeklyChart(
            WeeklyChartModel(10, emptyList())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DailyStepsChartPreview() {
    MaterialTheme {
        DailyChart(
            DailyChartModel(emptyList())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GridPreview() {
    MaterialTheme {
        StatScreen()
    }
}

