package com.example.na_steptracker.widget

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class StepsWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = StepsWidget()
}