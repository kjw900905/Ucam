package com.example;
/**
 * Created by HKH on 2016-06-28.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.example.Activity.ChatFragment;
import com.example.Activity.R;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendPushNotification(remoteMessage.getData().get("message"));
    }

    private void sendPushNotification(String message) {
        System.out.println("received message : " + message);
        Intent intent = new Intent(this, ChatFragment.class);
        /* TODO: 불러오는 프래그먼트 및 액티비티를 설정한다. (그 외 기타 설정 및 참고 사항들)
        *  다음 코드를 해당 클래스에 추가한다:
        *  (1) import com.google.firebase.messaging.FirebaseMessaging; //맨 위에
        *  (2) FirebaseMessaging.getInstance().subscribeToTopic("notice"); //onCreate 함수 안에
        *  ( 여기서 topic은 API를 사용하여 푸시 알림 전송 시 같은 토픽명 그룹 전체에 메세지를 발송할 수 있다)
        *
        *  기타 설정 및 참고사항:
        *  - 일단 임시로 ChatFragment로 설정함.
        *  - tools -> android -> SDK manager -> SDK Tools 탭에 들어가서 Google Play services를 설치
        *  - onTokenRefresh에서 뿐만 아니라 어느 시점에서든 String token = FirebaseInstanceId.getInstance().getToken(); 을 사용하면 사용자 기기의 고유 토큰 값을 가져올 수 있다.
        *  - 백그라운드에서도 푸시를 받기 위해서는 다음 사이트를 참고한다:
        *  >> https://trandent.com/board/Android/detail/744
        *  - 세부 알림 설정 사항을 위해서는 다음 사이트를 참고한다 (추가되는 코드들이 있으니 반드시 확인할 것):
        *  >> https://trandent.com/board/Android/detail/745
        * */
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher) )
                .setContentTitle("Push Title ")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255,500,2000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}

