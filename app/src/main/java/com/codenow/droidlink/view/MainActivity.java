/*
 * DroidVNC-NG main activity.
 *
 * Author: Christian Beier <info@christianbeier.net
 *
 * Copyright (C) 2020 Kitchen Armor.
 *
 * You can redistribute and/or modify this program under the terms of the
 * GNU General Public License version 2 as published by the Free Software
 * Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place Suite 330, Boston, MA 02111-1307, USA.
 */

package com.codenow.droidlink.view;

import static android.os.Build.VERSION.SDK_INT;
import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codenow.droidlink.R;

import com.codenow.droidlink.view.server.AdminActivity;
import com.codenow.droidlink.view.server.ServerActivity;
import com.codenow.droidlink.view.viewer.AndroidLinkActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_ENABLE_ADMIN = 77;

    private Button start_button, permission_button, admin_button;

    private LinearLayout connect_button, server_button, permission_layout, admin_layout;
    private ImageView message_icon;
    ComponentName adminComponent;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect_button = findViewById(R.id.connect_button);
        server_button = findViewById(R.id.server_button);
        permission_layout = findViewById(R.id.permission_layout);
        permission_button = findViewById(R.id.permission_button);
        message_icon = findViewById(R.id.message_icon);
        admin_layout = findViewById(R.id.admin_layout);
        admin_button = findViewById(R.id.admin_button);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        adminComponent = new ComponentName(this, AdminActivity.class);

        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (!dpm.isAdminActive(adminComponent)) {

            //addAdminAccount();


        } else {
            
        }



        connect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AndroidLinkActivity.class));

            }
        });

        server_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ServerActivity.class));

            }
        });

        //isStoragePermissionGranted();


    }

    private void addAdminAccount() {
        admin_layout.setVisibility(View.VISIBLE);
        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Add Admin");
                requestDeviceAdminLauncher.launch(intent);
                admin_layout.setVisibility(View.GONE);
            }
        });
    }

    private final ActivityResultLauncher<Intent> requestDeviceAdminLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(this, "Permission Granted !!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "This permission is necessary to use this app", Toast.LENGTH_SHORT).show();
                }
            });


    @Override
    protected void onResume() {
        super.onResume();
       /* // First, confirm that this package is allowlisted to run in lock task mode.
        if (dpm.isLockTaskPermitted(getPackageName())) {
            startLockTask();
        } else {
            // Because the package isn't allowlisted, calling startLockTask() here
            // would put the activity into screen pinning mode.
        }*/
    }

    public boolean isStoragePermissionGranted() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!Environment.isExternalStorageManager() && permission != PackageManager.PERMISSION_GRANTED) {
                // Request the permission

                permission_layout.setVisibility(View.VISIBLE);
                permission_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        permission_layout.setVisibility(View.GONE);
                    }
                });

                return false;
            } else {
              ;
                return true;
            }
        } else {
            // For Android versions prior to R
            if (permission == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted
                return true;
            } else {
                // Request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);
                return false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}