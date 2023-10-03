package com.codenow.droidlink.view.viewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.codenow.droidlink.R;

public class ConnectionListActivity extends AppCompatActivity {

    VncSettings vncSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_list);

       /* Cursor mCursor = this.database.getReadableDatabase().query(AbstractConnectionBean.GEN_TABLE_NAME, new String[]{"_id", AbstractConnectionBean.GEN_FIELD_NICKNAME, AbstractConnectionBean.GEN_FIELD_USERNAME, AbstractConnectionBean.GEN_FIELD_ADDRESS, AbstractConnectionBean.GEN_FIELD_PORT, AbstractConnectionBean.GEN_FIELD_REPEATERID}, "KEEPPASSWORD <> 0", null, null, null, AbstractConnectionBean.GEN_FIELD_NICKNAME);
        startManagingCursor(mCursor);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.connection_list, mCursor, new String[]{AbstractConnectionBean.GEN_FIELD_NICKNAME, AbstractConnectionBean.GEN_FIELD_ADDRESS, AbstractConnectionBean.GEN_FIELD_PORT, AbstractConnectionBean.GEN_FIELD_REPEATERID}, new int[]{R.id.list_text_nickname, R.id.list_text_address, R.id.list_text_port, R.id.list_text_repeater});
        setListAdapter(adapter);*/
    }
/*
    @Override // android.app.ListActivity
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ConnectionBean connection = new ConnectionBean();
        if (connection.Gen_read(this.database.getReadableDatabase(), id)) {
            Intent.ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.droid_link_icon);
            Intent intent = new Intent();
            Intent launchIntent = new Intent(this, VncCanvasActivity.class);
            Uri.Builder builder = new Uri.Builder();
            builder.authority("android.androidVNC.CONNECTION:" + connection.get_Id());
            builder.scheme("vnc");
            launchIntent.setData(builder.build());
            intent.putExtra("android.intent.extra.shortcut.INTENT", launchIntent);
            intent.putExtra("android.intent.extra.shortcut.NAME", connection.getNickname());
            intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", icon);
            setResult(-1, intent);
        } else {
            setResult(0);
        }
        finish();
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        this.database.close();
        super.onDestroy();
    }*/
}