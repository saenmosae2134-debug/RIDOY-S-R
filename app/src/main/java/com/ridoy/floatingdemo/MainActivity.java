package com.ridoy.floatingdemo;
import android.app.Activity; import android.content.Intent; import android.net.Uri; import android.os.Bundle; import android.provider.Settings; import android.view.Gravity; import android.view.View; import android.widget.Button; import android.widget.LinearLayout; import android.widget.TextView;
public class MainActivity extends Activity {
 @Override public void onCreate(Bundle b){ super.onCreate(b); LinearLayout l=new LinearLayout(this); l.setOrientation(1); l.setGravity(Gravity.CENTER); l.setPadding(32,32,32,32); l.setBackgroundColor(0xff111827);
 TextView t=new TextView(this); t.setText("Floating Signal Demo\n\nThis is a random demo signal generator—not a prediction of live game results."); t.setTextColor(0xffffffff); t.setTextSize(20); l.addView(t);
 Button start=new Button(this); start.setText("Enable floating button"); l.addView(start); start.setOnClickListener(v->{ if(!Settings.canDrawOverlays(this)){Intent i=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));startActivity(i);} else {startService(new Intent(this,OverlayService.class)); finish();}});
 Button stop=new Button(this); stop.setText("Stop overlay"); l.addView(stop); stop.setOnClickListener(v->stopService(new Intent(this,OverlayService.class))); setContentView(l);
 }
 @Override protected void onResume(){super.onResume(); if(Settings.canDrawOverlays(this)) startService(new Intent(this,OverlayService.class));}
}
