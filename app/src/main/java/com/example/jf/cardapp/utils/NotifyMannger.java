package com.example.jf.cardapp.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.activity.HomeActivity;


/**
 * 通知管理工具类
 */

public class NotifyMannger {

    private static final int NOTIFY_ID = 1;
    private static final String NOTIFY_CONFIG = "notify";
    private static final String NOTIFY_KEY = "notify";
    private static Notification notification;
    private static NotificationManager notificationManager;

    /** 显示通知 */
    public static void showNofify(Context context){
        if(notification == null){
            //创建通知
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.appicon) //设置通知的图标
                    .setContentTitle("")//设置通知的标题
                    .setContentText("");//设置通知的内容
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(new Intent(context,HomeActivity.class));
            stackBuilder.addParentStack(HomeActivity.class);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.layout_nofity);
            builder.setContent(remoteViews);//设置通知自定义布局
            builder.setContentIntent(pendingIntent);//设置点击通知时要做的操作
            notification = builder.build();
            notification.flags = Notification.FLAG_NO_CLEAR;//设置通知不能滑动取消
        }
        //所有的通知由NotificationManager管理
        if(notificationManager == null)
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //使用通知服务来显示一个通知
        notificationManager.notify(NOTIFY_ID, notification);//第一个参数：通知的id;第二个参数：要显示的通知
    }

    /** 取消通知 */
    public static void closeNotify(Context context){
        if(notificationManager == null)
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFY_ID);//通过通知的id取消对应的通知
    }

    /** 保存通知开关的状态 */
    public static void saveNotifytatic(Context context,boolean isShow){
        SharedPreferences sp = context.getSharedPreferences(NOTIFY_CONFIG, Context.MODE_PRIVATE);
        sp.edit().putBoolean(NOTIFY_KEY,isShow).commit();
    }

    /** 从本地读取通知的状态 */
    public static boolean isShowNofity(Context context){
        SharedPreferences sp = context.getSharedPreferences(NOTIFY_CONFIG, Context.MODE_PRIVATE);
        return sp.getBoolean(NOTIFY_KEY,false);
    }
}
