package ayoub.anbara.yoga;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelId");
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                //.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(context.getString(R.string.it_s_time))
                .setContentText(context.getString(R.string.time_to_training))
                .setContentInfo(context.getString(R.string.info));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.ic_play_circle_large);
            builder.setLargeIcon(BitmapFactory.decodeResource(
                    context.getResources(), R.mipmap.ic_launcher));

            builder.setColor(context.getResources().getColor(R.color.move));
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher_round);
        }


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(resultPendingIntent);

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }

    }
}
