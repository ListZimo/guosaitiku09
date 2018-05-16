package com.example.guosai2018tiku09;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.newland.rfid_demo.HightFrequencyThread;
import com.newland.rfid_demo.HightFrequencyThread.GetDataCallBack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button kaimenButton,dukaButton,duka2Button,zhuceButton,zhuce1Button;
	private ImageView paizhaoImageView,menImageView,zhaopianImageView2;
	private EditText xingmingEditText,zhiweiEditText,dianhuaEditText,mimaEditText,kahaoEditText,kahao2EditText;
	private Animation animation;
	private AnimationDrawable aDrawable;
	private TextView xingmingTextView,zhiwuTextView,dianhuaTextView;
	SQLiteDatabase sqLiteDatabase;
	private HightFrequencyThread hThread;
	String kahaoString = "";
	String duqukahaoString="";
//	private SurfaceView surfaceView;
//	private ImageView imageView;
	private Camera camera;
	private Bitmap bitmap;
	int xuanze =0;
	String lujingString = "/mnt/sdcard/";
	String mimaString = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
		Rfid();
		SQLite_it();
	}
	

	private void init() {
		// TODO Auto-generated method stub
		kaimenButton = (Button) findViewById(R.id.kaimen_button3);
		dukaButton = (Button) findViewById(R.id.duka_button2);
		zhuceButton = (Button) findViewById(R.id.zhuce_button1);
		zhaopianImageView2 = (ImageView) findViewById(R.id.zhaopian_imageView3);
		menImageView = (ImageView) findViewById(R.id.men_imageView2);
		aDrawable = (AnimationDrawable) menImageView.getDrawable();
		menImageView.setAnimation(animation);
		aDrawable.stop();
		xingmingTextView = (TextView) findViewById(R.id.xingming_textView5);
		zhiwuTextView = (TextView) findViewById(R.id.zhiwu_textView7);
		dianhuaTextView = (TextView) findViewById(R.id.dianhua_textView9);
		
		mimaEditText = (EditText) findViewById(R.id.mima_editText2);
		kahaoEditText = (EditText) findViewById(R.id.editText1);
		
		dukaButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				xuanze = 0;
				hThread.searchLabel();
			}
		});
		
		zhuceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TableLayout loginLayout = (TableLayout) getLayoutInflater().inflate(R.layout.zhuce, null);
				new AlertDialog.Builder(MainActivity.this).setIcon(null).setView(loginLayout).show();
				xingmingEditText = (EditText) loginLayout.findViewById(R.id.ZhuceXinming1_editText1);
				
				zhiweiEditText = (EditText) loginLayout.findViewById(R.id.ZhuceZhiwei1_editText2);
				
				dianhuaEditText = (EditText) loginLayout.findViewById(R.id.ZhuceDianhua1_editText3);
				
				mimaEditText = (EditText) loginLayout.findViewById(R.id.ZhuceMima1_editText4);
				
				kahao2EditText = (EditText) loginLayout.findViewById(R.id.ZhuceKahao1_editText5);
				zhuce1Button = (Button) loginLayout.findViewById(R.id.ZhuceZhuce1_button2);
				duka2Button = (Button) loginLayout.findViewById(R.id.ZhuceDuka1_button1);
				paizhaoImageView = (ImageView) loginLayout.findViewById(R.id.ZhucePaizhao1_imageView1);
				
				paizhaoImageView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent gentImageIntent = new Intent("android.media.action.IMAGE_CAPTURE");
						startActivityForResult(gentImageIntent,1);
						
					}
				});
				 
				
				duka2Button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						xuanze = 1;
						hThread.searchLabel();
						
						
					}
				});
				
				zhuce1Button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method 
						String xingmingString = xingmingEditText.getText().toString().trim();
						String zhiweiString = zhiweiEditText.getText().toString().trim();
						String dianhuaString = dianhuaEditText.getText().toString().trim();
						String mimaString = mimaEditText.getText().toString().trim();
						String insert = "insert into shuju(name,job,phone,password,card_numver,icon_path)values('"+xingmingString+"','"+zhiweiString+"','"+dianhuaString+"','"+mimaString+"','"+duqukahaoString+"','"+lujingString+"')";
						sqLiteDatabase.execSQL(insert);
						
					}
				});
			}
		});
	}


	private void Rfid() {
		// TODO Auto-generated method stub
		hThread = HightFrequencyThread.getInstance(7, 1, 6);
		if (hThread != null) {
			hThread.setgetDataCallBack(new GetDataCallBack() {
				
				@Override
				public void searchLabel(int arg0, List<String> arg1, int arg2) {
					// TODO Auto-generated method stub
					Message msg = mHandler.obtainMessage();
					msg.obj = arg1.get(0);
					msg.what = xuanze;
					msg.arg1 = arg2;
					mHandler.sendMessage(msg);
				}
				
				@Override
				public void getData(int arg0, String arg1, int arg2) {
					// TODO Auto-generated method stub
					Message msgMessage =mHandler.obtainMessage();
					msgMessage.obj = arg1;
					msgMessage.what = 1;
					msgMessage.arg1 = arg2;
					mHandler.sendMessage(msgMessage);
				}
			});
		}
		
	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			duqukahaoString= (String) msg.obj;
			switch (msg.what) {
			case 0:
				kahaoEditText.setText(duqukahaoString);
				Cursor cursor = sqLiteDatabase.query("shuju", new String[]{"_id","name","job","phone","password","card_numver","icon_path"},"card_numver='"+duqukahaoString+"'" , null, null, null, null, null);
				if  (cursor.moveToNext()) {
				
					String shujukuKahao = cursor.getString(cursor.getColumnIndex("card_numver"));
					String shujukuMingzi = cursor.getString(cursor.getColumnIndex("name"));
					String shujukuZhiwu = cursor.getString(cursor.getColumnIndex("job"));
					String shujukuDianhua = cursor.getString(cursor.getColumnIndex("phone"));
					String shujukuzhaopian = cursor.getString(cursor.getColumnIndex("icon_path"));
						xingmingTextView.setText(shujukuMingzi);
						zhiwuTextView.setText(shujukuZhiwu);
						dianhuaTextView.setText(shujukuDianhua);
//						Toast.makeText(MainActivity.this,shujukuzhaopian+shujukuKahao+".jpg" ,Toast.LENGTH_SHORT).show();
						zhaopianImageView2.setImageBitmap(BitmapFactory.decodeFile(shujukuzhaopian+shujukuKahao+"。jpg"));
						kaimenButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								mimaString = mimaEditText.getText().toString().trim();
								Cursor cursor2 = sqLiteDatabase.query("shuju", new String[]{"_id","name","job","phone","password","card_numver","icon_path"}, "password='"+mimaString+"'", null, null, null, null);
								if (cursor2.moveToNext()) {
									aDrawable.start();
									Intent intent = new Intent(MainActivity.this,Peiyushi.class);
									try {
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									startActivity(intent);
								}
							}
						});
						
					}
				else {
						Toast.makeText(MainActivity.this, "此卡为非法卡", Toast.LENGTH_SHORT).show();
					}
					
				break;
			case 1:
				kahao2EditText.setText(duqukahaoString);
				break;
			}
		};
	};
	


	private void SQLite_it() {
		// TODO Auto-generated method stub
		sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/mnt/sdcard/yonghuxinxi.db", null);
		sqLiteDatabase.execSQL("create table if not exists shuju(_id integer,name text,job text,phone text,password text,card_numver text,icon_path text)");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			try {
				super.onActivityResult(requestCode, resultCode, data);
				Bundle extras = data.getExtras();
				bitmap = (Bitmap) extras.get("data");
				
				File file = new File(lujingString+duqukahaoString+"。jpg");
//				Toast.makeText(MainActivity.this, duqukahaoString, Toast.LENGTH_SHORT).show();
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			paizhaoImageView.setImageBitmap(bitmap);
		}
	}

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
