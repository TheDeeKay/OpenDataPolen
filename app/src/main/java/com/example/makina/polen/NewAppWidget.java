package com.example.makina.polen;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    // our actions for our buttons
    public static String ACTION_WIDGET_REFRESH = "ActionReceiverRefresh";
    public static String ACTION_WIDGET_SETTINGS = "ActionReceiverSettings";
    public static String ACTION_WIDGET_ABOUT = "ActionReceiverAbout";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Intent active = new Intent(context, NewAppWidget.class);
        active.setAction(ACTION_WIDGET_REFRESH);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.refresh_button, actionPendingIntent);

        /*active = new Intent(context, NewAppWidget.class);
        active.setAction(ACTION_WIDGET_SETTINGS);
        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.button_settings, actionPendingIntent);*/

        /*active = new Intent(context, NewAppWidget.class);
        active.setAction(ACTION_WIDGET_ABOUT);
        actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.button_about, actionPendingIntent);*/

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_WIDGET_REFRESH)) {
            Log.i("onReceive", ACTION_WIDGET_REFRESH);
        } else if (intent.getAction().equals(ACTION_WIDGET_SETTINGS)) {
            Log.i("onReceive", ACTION_WIDGET_SETTINGS);
        } else if (intent.getAction().equals(ACTION_WIDGET_ABOUT)) {
            Log.i("onReceive", ACTION_WIDGET_ABOUT);
        } else {
            super.onReceive(context, intent);
        }
    }
}

