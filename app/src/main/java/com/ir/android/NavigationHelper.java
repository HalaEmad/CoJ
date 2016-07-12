package com.ir.android;

import android.content.Context;
import android.content.Intent;

import com.ir.android.login.LoginScreen;
import com.ir.android.main.MainScreen;

/**
 * Created by bassam on 09-07-2016.
 */
public class NavigationHelper {

    public static void showLogin(Context context) {
        Intent intent = new Intent(context, LoginScreen.class);
        context.startActivity(intent);
    }

    public static void showMain(Context context) {
        Intent intent = new Intent(context, MainScreen.class);
        context.startActivity(intent);
    }

//    public static void showWalkthrough(Context context) {
//        Intent intent = new Intent(context, WalkthroughScreen.class);
//        context.startActivity(intent);
//    }
//
//    public static void showIncidents(Context context) {
//        Intent intent = new Intent(context, IncidentScreen.class);
//        context.startActivity(intent);
//    }
//
//    public static void showActiveCall(Context context) {
//        Intent intent = new Intent(context, ActiveCallScreen.class);
//        context.startActivity(intent);
//    }
}
