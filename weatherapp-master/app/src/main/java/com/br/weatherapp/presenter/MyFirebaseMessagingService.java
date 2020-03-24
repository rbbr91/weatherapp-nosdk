package com.br.weatherapp.presenter;

import com.br.weatherapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Getting the Notification Data
        final Map<String, String> data = remoteMessage.getData();

        if (data != null) {

        }
    }

    //This method should only be overriden if you are using Firebase Cloud Messaging 17.1.0 or greater. If you are using a lower version, override the onTokenRefresh of the FirebaseInstanceIdService
    @Override
    public void onNewToken(String firebaseToken) {

    }
}
