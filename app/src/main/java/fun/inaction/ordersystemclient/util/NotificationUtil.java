package fun.inaction.ordersystemclient.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import fun.inaction.ordersystemclient.BuildConfig;
import fun.inaction.ordersystemclient.MyApplication;
import fun.inaction.ordersystemclient.OrderHistoryActivity;
import fun.inaction.ordersystemclient.R;

public class NotificationUtil {

    public static NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(MyApplication.Companion.getContext());

    private static final String TakeFoodChannelID="takeFood";

    private static int takeFoodCodeID = 0;

    static {
        createChannel(TakeFoodChannelID,"取餐通知",NotificationManager.IMPORTANCE_HIGH);
    }

    public static void sendTakeFoodNotification(String code){
        sendNotification("取餐","取餐码为"+code+",请取餐",takeFoodCodeID,TakeFoodChannelID);
        takeFoodCodeID++;
    }

    public static void createChannel(String channelID,String name,int important){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelID,name,important);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void sendNotification(String title,String msg,int notificationID,String channelID){
        Context context = MyApplication.Companion.getContext();
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        Notification notification = new NotificationCompat.Builder(context,channelID)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setLargeIcon(((VectorDrawable)(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_money, null))))
                .build();
        notificationManager.notify(notificationID,notification);
    }

}
