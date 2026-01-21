package com.example.na_steptracker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

data class RecordCardModel(
    val index: Int,
    val weather: ImageVector,
    val steps: Int,
    val date: LocalDate,
) {
    companion object {
        val mock
            get() = RecordCardModel(
                index = 1,
                weather = Icons.Default.WbSunny,
                steps = 7000,
                date = LocalDate.of(2026, 1, 1)
            )
        val mockList
            get() = (1..12).map { mock.copy(index = it) }
    }
}

@Composable
fun RecordCard(
    record: RecordCardModel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(0.75f),
                    text = "${record.index}."
                )

                Icon(
                    modifier = Modifier
                        .weight(0.5f)
                        .aspectRatio(1f),
                    imageVector = record.weather,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.alpha(0.5f),
                fontSize = 14.sp,
                text = "${record.date}",
            )
            HorizontalDivider()
            Text(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                text = "${record.steps}"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecordCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(color = MaterialTheme.colorScheme.onSurface)
                .padding(8.dp)
        ) {
            RecordCard(RecordCardModel.mock)
        }
    }
}