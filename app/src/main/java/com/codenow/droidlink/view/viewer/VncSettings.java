package com.codenow.droidlink.view.viewer;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;


public class VncSettings {
    // Singleton
    private static final VncSettings settings = new VncSettings();

    private static final String VNC_PREF_USERNAME = "vnc_pref_username";
    private static final String VNC_PREF_PASSWORD = "vnc_pref_password";
    private static final String VNC_PREF_PASSWORD_CHECKED = "vnc_pref_password_checked";
    private static final String VNC_PREF_SERVER = "vnc_pref_server";
    private static final String VNC_PREF_PORT = "vnc_pref_port";
    private static final String VNC_PREF_COLOR_DEPTH = "vnc_pref_color_depth";
    private static final String VNC_PREF_MENU_OVERLAY = "vnc_pref_menu_overlay";
    private static final String VNC_PREF_AUTOMATIC_LOGIN = "vnc_pref_automatic_login";

    // Preferences
    private SharedPreferences sharedPref = null;
    private SharedPreferences.Editor prefEditor = null;

    private VncSettings() {

    }

    public static VncSettings getPreferences(Context c) {
        if(settings.sharedPref == null && c != null) {
            try {
                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                settings.sharedPref = EncryptedSharedPreferences.create(
                        "secret_shared_prefs",
                        masterKeyAlias,
                        c,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                settings.prefEditor = settings.sharedPref.edit();
            } catch (Exception e) {

            }
        }
        return settings;
    }

    public void save() {
        prefEditor.commit();
    }


    // Getter
    public String getAddress() {
        return sharedPref.getString(VNC_PREF_SERVER, "127.0.0.1");
    }

    public int getPort() {
        return sharedPref.getInt(VNC_PREF_PORT, 5900);
    }

    public String getUserName() {
        return sharedPref.getString(VNC_PREF_USERNAME, "");
    }

    public String getPassword() {
        return sharedPref.getString(VNC_PREF_PASSWORD, "");
    }

    public String getColorModel() {
        return sharedPref.getString(VNC_PREF_COLOR_DEPTH, COLORMODEL.C24bit.getId());
    }

    public boolean getKeepPassword() {
        return sharedPref.getBoolean(VNC_PREF_PASSWORD_CHECKED, false);
    }

    public boolean getMenuOverlay() {
        return sharedPref.getBoolean(VNC_PREF_MENU_OVERLAY, false);
    }

    public boolean getAutomaticLogin() {
        return sharedPref.getBoolean(VNC_PREF_AUTOMATIC_LOGIN, false);
    }

    public ImageView.ScaleType getScaleMode() {
        return ImageView.ScaleType.FIT_CENTER;
    }

    public boolean getUseImmersive() {
        return true;
    }

    public boolean getFollowMouse() {
        return true;
    }

    public boolean getFollowPan() {
        return false;
    }

    public String getInputMode() {
        return VncCanvasActivity.FIT_SCREEN_NAME;
    }

    public boolean getUseWakeLock() {
        return true;
    }

    public long getForceFull() {
        return BitmapImplHint.AUTO;
    }

    public boolean getUseLocalCursor() {
        return true;
    }


    // Setter
    public void setAddress(String address) {
        prefEditor.putString(VNC_PREF_SERVER, address);
        prefEditor.apply();
    }

    public void setPort(int port) {
        prefEditor.putInt(VNC_PREF_PORT, port);
        prefEditor.apply();
    }

    public void setUserName(String username) {
        prefEditor.putString(VNC_PREF_USERNAME, username);
        prefEditor.apply();
    }

    public void setPassword(String password) {
        prefEditor.putString(VNC_PREF_PASSWORD, password);
        prefEditor.apply();
    }

    public void setKeepPassword(boolean checked) {
        prefEditor.putBoolean(VNC_PREF_PASSWORD_CHECKED, checked);
        prefEditor.apply();
    }

    public void setColorModel(String depth) {
        prefEditor.putString(VNC_PREF_COLOR_DEPTH, depth);
        prefEditor.apply();
    }

    public void setMenuOverlay(boolean showOverlay) {
        prefEditor.putBoolean(VNC_PREF_MENU_OVERLAY, showOverlay);
        prefEditor.apply();
    }

    public void setAutomaticLogin(boolean automaticLogin) {
        prefEditor.putBoolean(VNC_PREF_AUTOMATIC_LOGIN, automaticLogin);
        prefEditor.apply();
    }

    public void setScaleMode(ImageView.ScaleType scaleType) {
    }

    public void setInputMode(String name) {
    }

    public void setFollowMouse(boolean newFollow) {
    }

    public void setFollowPan(boolean newFollowPan) {
    }

    public SharedPreferences getSerializable() {
        return sharedPref;
    }
}
