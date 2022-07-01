package com.example.poc_voice.fcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.poc_voice.R;
import com.example.poc_voice.activity.MainActivity;

public class ShowNotificationIntentService  extends IntentService {

    private static final String ACTION_SHOW_NOTIFICATION = "my.app.service.action.show";
    private static final String ACTION_HIDE_NOTIFICATION = "my.app.service.action.hide";


    public ShowNotificationIntentService() {
        super("ShowNotificationIntentService");
    }

    public static void startActionShow(Context context) {
        Intent intent = new Intent(context, ShowNotificationIntentService.class);
        intent.setAction(ACTION_SHOW_NOTIFICATION);
        context.startService(intent);
    }

    public static void startActionHide(Context context) {
        Intent intent = new Intent(context, ShowNotificationIntentService.class);
        intent.setAction(ACTION_HIDE_NOTIFICATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_NOTIFICATION.equals(action)) {
                handleActionShow();
            } else if (ACTION_HIDE_NOTIFICATION.equals(action)) {
                handleActionHide();
            }
        }
    }

    private void handleActionShow() {
        showStatusBarIcon(ShowNotificationIntentService.this);
    }

    private void handleActionHide() {
        hideStatusBarIcon(ShowNotificationIntentService.this);
    }

    private void hideStatusBarIcon(ShowNotificationIntentService showNotificationIntentService) {

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(111);
    }

    public static void showStatusBarIcon(Context ctx) {
        Context context = ctx;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setContentTitle("Voice App")
                .setSmallIcon(R.drawable.ic_call_end_white_24dp)
                .setOngoing(true);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pIntent = PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pIntent = PendingIntent.getActivity(context, 111, intent, 0);
        }
        builder.setContentIntent(pIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = builder.build();
        notif.flags |= Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(111, notif);
    }


}
