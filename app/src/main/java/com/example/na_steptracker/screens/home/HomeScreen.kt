package com.example.na_steptracker.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
            .height(186.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "5642",
                    fontSize = 52.sp,
                    modifier = Modifier.padding(32.dp, 8.dp)
                )
                Text(
                    text = "/6000",
                    fontSize = 26.sp,
                    modifier = Modifier.padding(32.dp, 0.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = null,
                modifier = Modifier
                    .padding(36.dp)
                    .fillMaxSize()
            )
        }
    }
}

data class Day(val steps: Int, val dayOfTheWeek: DayOfWeek)

val today = LocalDate.now()
val days = List(7) { index ->
    val date = today.minusDays(index.toLong())
    Day(
        steps = Random.nextInt(0..6000),
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
                modifier = Modifier.padding(16.dp, 0.dp)
            ){
                Text(
                    text = stringResource(R.string.avg_steps),
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(0.dp, 8.dp),
                            progress = { Random.nextFloat() },
                            color = ProgressIndicatorDefaults.circularColor,
                            strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                        )
                        Text(
                            text = day.dayOfTheWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault()
                            )
                        )
                    }
                }
            }
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

