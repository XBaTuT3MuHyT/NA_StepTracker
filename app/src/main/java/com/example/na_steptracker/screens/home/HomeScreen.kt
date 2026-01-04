package com.example.na_steptracker.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.na_steptracker.R
import com.example.na_steptracker.ui.theme.NA_StepTrackerTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        DailyStat()
        WeaklyStat(days)
    }
}

@Composable
fun DailyStat() {
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
                        text = "5642",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.alpha(0.5f),
                        text = "/6000 Шаги",
                        fontSize = 20.sp,
                    )
                }
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .weight(1f)
                )
            }
            LinearProgressIndicator(
                progress = { days[6].steps.toFloat() / 6000 },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(15.dp)
            )
        }
    }
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
fun WeaklyStat(days: List<Day>) {
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
                    text = (" 5632"),
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
                for (day in days) {

                    val progress = day.steps.toFloat() / 6000
                    val alphaMod: Float = if (progress >= 1) 1f else 0.5f

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProgressWithCenterDot(progress)
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .padding(0.dp, 8.dp)
//                                .size(30.dp),
//                            progress = { progress },
//                        )
                        Text(
                            text = day.dayOfTheWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault()
                            ),
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


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NA_StepTrackerTheme {
        DailyStat()
    }
}

@Preview(showBackground = true)
@Composable
fun WeaklyStatPreview() {
    NA_StepTrackerTheme {
        WeaklyStat(days)
    }
}

