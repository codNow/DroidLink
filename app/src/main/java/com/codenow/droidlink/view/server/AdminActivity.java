package com.codenow.droidlink.view.server;

import android.app.ActivityOptions;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.codenow.droidlink.view.MainActivity;

public class AdminActivity extends DeviceAdminReceiver {

    DevicePolicyManager dpm;
    private static final String YOUR_APP_PACKAGE = "com.codenow.droidlink";
    private static final String[] APP_PACKAGES = {YOUR_APP_PACKAGE};

    Context context;


}
