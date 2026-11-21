package com.ridoy.floatingdemo;
import android.app.Service; import android.content.Intent; import android.graphics.PixelFormat; import android.os.IBinder; import android.view.Gravity; import android.view.MotionEvent; import android.view.View; import android.view.WindowManager; import android.widget.Button; import android.widget.LinearLayout; import android.widget.TextView; import java.util.Random;
public class OverlayService extends Service {
 WindowManager wm; View bubble; WindowManager.LayoutParams bp; View panel; WindowManager.LayoutParams pp; Random rng=new Random(); int n=0; String[] picks={"1","2","5","10","Coin Flip (bonus)","Pachinko (bonus)","Cash Hunt (bonus)","Crazy Time (bonus)"};
 public IBinder onBind(Intent i){return null;}
 public void onCreate(){super.onCreate(); wm=(WindowManager)getSystemService(WINDOW_SERVICE); showBubble();}
 int type(){return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;}
 void showBubble(){ if(bubble!=null)return; Button b=new Button(this); b.setText("SIG"); b.setTextColor(0xffffffff); b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xff0f766e)); bubble=b;
 bp=new WindowManager.LayoutParams(WRAP,WRAP,type(),WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT); bp.gravity=Gravity.TOP|Gravity.START; bp.x=40;bp.y=260; wm.addView(b,bp);
 b.setOnClickListener(v->togglePanel()); b.setOnTouchListener(new View.OnTouchListener(){float sx,sy;int ox,oy; public boolean onTouch(View v,MotionEvent e){if(e.getAction()==0){sx=e.getRawX();sy=e.getRawY();ox=bp.x;oy=bp.y;return false;} if(e.getAction()==2){bp.x=ox+(int)(e.getRawX()-sx);bp.y=oy+(int)(e.getRawY()-sy);wm.updateViewLayout(bubble,bp);return Math.abs(e.getRawX()-sx)>8||Math.abs(e.getRawY()-sy)>8;}return false;}});
 }
 static final int WRAP=WindowManager.LayoutParams.WRAP_CONTENT;
 void togglePanel(){if(panel!=null){wm.removeView(panel);panel=null;return;} LinearLayout box=new LinearLayout(this); box.setOrientation(1);box.setPadding(28,24,28,24);box.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xff202938));box.setBackgroundColor(0xff202938);
 TextView title=new TextView(this);title.setText("SIGNAL DEMO  •  #"+(++n));title.setTextColor(0xffffffff);title.setTextSize(18);box.addView(title);
 TextView disclaimer=new TextView(this);disclaimer.setText("Random demo only. Not a forecast.");disclaimer.setTextColor(0xffcbd5e1);box.addView(disclaimer);
 TextView result=new TextView(this);result.setText(picks[rng.nextInt(picks.length)]);result.setTextColor(0xfffbbf24);result.setTextSize(28);result.setPadding(0,22,0,22);box.addView(result);
 Button next=new Button(this);next.setText("NEW SIGNAL");box.addView(next);next.setOnClickListener(v->{n++;title.setText("SIGNAL DEMO  •  #"+n);result.setText(picks[rng.nextInt(picks.length)]);});
 Button close=new Button(this);close.setText("CLOSE");box.addView(close);close.setOnClickListener(v->{wm.removeView(panel);panel=null;});
 panel=box;pp=new WindowManager.LayoutParams(WRAP,WRAP,type(),WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);pp.gravity=Gravity.CENTER;wm.addView(panel,pp);}
 public void onDestroy(){if(panel!=null)wm.removeView(panel);if(bubble!=null)wm.removeView(bubble);super.onDestroy();}
}
