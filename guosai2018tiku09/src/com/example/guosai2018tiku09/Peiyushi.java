package com.example.guosai2018tiku09;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Peiyushi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_peiyushi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.peiyushi, menu);
		return true;
	}

}
