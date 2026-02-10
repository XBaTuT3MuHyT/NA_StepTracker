package com.example.na_steptracker.screens.home.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.na_steptracker.R
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = hiltViewModel()

    val dailyModel by viewModel.dailyModel.collectAsState()
    val weeklyModel by viewModel.weeklyModel.collectAsState()

    Content(
        dailyModel,
        weeklyModel,
    )
}

data class Day(val steps: Int, val dayOfTheWeek: DayOfWeek)

val today = LocalDate.now()
val days = List(7) { index ->
    val date = today.minusDays(index.toLong())
    Day(
        steps = Random.nextInt(0..8000),
        date.dayOfWeek
    )
}.reversed()

@Composable
fun Content(dailyModel: DailyUiModel, weeklyModel: WeeklyModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        DailyStat(dailyModel)
        WeaklyStat(weeklyModel)
    }
}

@Composable
fun DailyStat(dailyModel: DailyUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(0.1f))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${dailyModel.current}",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "/${dailyModel.target} ${stringResource(R.string.home_steps)}",
                        fontSize = 20.sp,
                    )
                }
                Icon(
                    imageVector = dailyModel.weather.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .weight(1f)
                )
            }
            LinearProgressIndicator(
                progress = { dailyModel.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(15.dp)
            )
        }
    }
}

@Composable
fun WeaklyStat(weeklyModel: WeeklyModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.padding(start = 16.dp, bottom = 6.dp)
            ) {
                Text(
                    text = stringResource(R.string.avg_steps),
                    modifier = Modifier.alpha(0.5f),
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = (" ${weeklyModel.averageSteps}"),
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in weeklyModel.days) {

                    val progress = day.first
                    val alphaMod: Float = if (progress >= 1) 1f else 0.5f

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProgressWithCenterDot(progress)

                        Text(
                            text = day.second,
                            modifier = Modifier.alpha(alphaMod)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressWithCenterDot(progress: Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 8.dp)
    ) {

        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(30.dp),
        )

        if (progress >= 1f) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                imageVector = Icons.Default.Bolt,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    NA_StepTrackerTheme {
//        DailyStat(
//            DailyUiModel(7000, 10000, 0.7f)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun WeaklyStatPreview() {
//    NA_StepTrackerTheme {
//        WeaklyStat(
//            WeeklyModel(1234, emptyList())
//        )
//    }
//}
