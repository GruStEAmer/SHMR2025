package com.example.graphics.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.graphics.model.ChartData
import kotlin.math.min

fun generateColorFromString(input: String): Color {
    val hash = input.hashCode()
    val r = (hash and 0xFF0000 shr 16)
    val g = (hash and 0x00FF00 shr 8)
    val b = (hash and 0x0000FF)
    val minBrightness = 50
    return Color(
        red = (r + minBrightness).coerceIn(0, 255),
        green = (g + minBrightness).coerceIn(0, 255),
        blue = (b + minBrightness).coerceIn(0, 255)
    )
}

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    chartDataList: List<Pair<String, Double>>,
    chartSize: Dp = 200.dp,
    legendItemSpacing: Dp = 8.dp,
    chartStrokeWidth: Float = 25f
) {
    val totalValue = chartDataList.sumOf { it.second }
    val dataWithColors = remember(chartDataList.map { it.first }) {
        chartDataList.map {
            ChartData(
                category = it.first,
                value = it.second,
                color = generateColorFromString(it.first)
            )
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .size(chartSize)
                .padding(16.dp)
        ) {
            if (totalValue == 0.0) return@Canvas

            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = min(canvasWidth, canvasHeight) / 2f - chartStrokeWidth / 2f
            val center = Offset(canvasWidth / 2, canvasHeight / 2)
            var startAngle = -90f

            dataWithColors.forEach { data ->
                val sweepAngle = (data.value / totalValue * 360f).toFloat()
                if (sweepAngle > 0f) {
                    drawArc(
                        color = data.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = chartStrokeWidth)
                    )
                }
                startAngle += sweepAngle
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        LazyColumn(
            modifier = Modifier.height(80.dp),
            verticalArrangement = Arrangement.spacedBy(legendItemSpacing)
        ) {
            items(dataWithColors) { data ->
                LegendItem(category = data.category, color = data.color)
            }
        }
    }
}

@Composable
fun LegendItem(category: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .padding(2.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = color)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = category, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartPreviewStableColors() {
    val sampleData1 = listOf(
        "Категория A" to 25.0,
        "Категория B" to 35.0,
        "Категория C" to 15.0,
        "Категория D" to 25.0
    )
    val sampleData2 = listOf(
        "Категория C" to 10.0,
        "Категория A" to 40.0,
        "Категория D" to 20.0,
        "Категория B" to 30.0
    )
    MaterialTheme {
        Column {
            Text("Набор данных 1 (Стабильные цвета)")
            PieChart(
                chartDataList = sampleData1,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Набор данных 2 (Те же категории, цвета должны совпасть)")
            PieChart(
                chartDataList = sampleData2,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
