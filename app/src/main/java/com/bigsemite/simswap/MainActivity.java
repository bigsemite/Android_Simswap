package com.bigsemite.simswap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends AppCompatActivity {

	EditText phone1, phone2;
	TextView tv1, tv2;
	Button b1, b2;
	SharedPreferences sp;
	
	String myPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page1);

		if(checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
			requestPermissions(new String[] {Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},11);
		}
		phone1 = (EditText) findViewById(R.id.editText1);
		phone2 = (EditText) findViewById(R.id.editText2);
		tv1 = (TextView) findViewById(R.id.textView1);
		b1 = (Button) findViewById(R.id.button1);
		tv2 = (TextView)findViewById(R.id.textView2);
		sp = this.getSharedPreferences("myPhoneSecure", Context.MODE_PRIVATE);
		b2 = (Button) findViewById(R.id.button2);

		/*
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		
		myPhone = tm.getSimSerialNumber(); //get the phone number
		
		showAlert("The Serial number of SIM card in Line 1 is: " + myPhone +" and this will be monitored for removal");
		*/
		String p1 = sp.getString("Phone1", ".");
		
		if (p1.equals(".")){
			phone1.setVisibility(View.VISIBLE);
			//phone2.setVisibility(View.VISIBLE);
			b1.setVisibility(View.VISIBLE);
			tv1.setVisibility(View.VISIBLE);
			tv2.setVisibility(View.INVISIBLE);
		}
		
		
		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}

	//@SuppressLint("NewApi")
	public void secure(View v){
		if(phone1.getText().toString().isEmpty()){
			showAlert("At least, 1 Phone number is required");
		}
		else{
			SharedPreferences.Editor editor = sp.edit();

			TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

			myPhone = tm.getSimSerialNumber(); //get the phone number
			editor.putString("MyNumber", myPhone);
			editor.putString("Phone1", phone1.getText().toString());
			if (phone2.getText().toString().isEmpty()){
				editor.putString("Phone2", phone2.getText().toString());
			}
			editor.commit();
			
			phone1.setVisibility(View.INVISIBLE);
			//phone2.setVisibility(View.INVISIBLE);
			b1.setVisibility(View.INVISIBLE);
			tv1.setVisibility(View.INVISIBLE);
			tv2.setVisibility(View.VISIBLE);
		}


		showAlert("The Serial number of SIM card in Line 1 is: " + myPhone +" and this will be monitored for removal");
		myUtil.sched(getApplicationContext());
		
	}
	
	public void about(View v){
		String story = "About this App:\n" +
				"This App protects your devices against theft by storing the current status of the existing SIM card and monitoring it to know when it is replaced with another one." +
				"User of the device will be advised to use another person(s) phone number(s) as the recovery numbers. For example, use the number of your next of kin, or spouse or family friends as the recovery numbers." +
				"\n HOW IT WORKS: \n" +
				"This App requires only one time setup. After then, the App. runs on the background. In order to conserve the battery life of the device, this App only monitors the device 'Power-up completed. \n" +
				"\n1. Enter the recovery phone number1 (Compulsory)" +
				"\n2. Enter the recovery phone number2 (Optional)" +
				"\n3. Click 'Secure this device' button." +
				"\nAfter that, be rest assure that your device is been monitored for any change of SIM card." +
				"\nCHANGE RECOVERY NUMBER" +
				"\nIn case of personal need to replace SIM card or changing the recovery phone number," +
				"\nGoto Settings>Apps>Simswap and chose Clear Data" +
				"\nThis App can be used along with 'Car Tracker with SMS' by bigsemite in order to detect the current location of the device";
		showAlert(story);
	}
	
	public void showAlert(String issue){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		
		alertBuilder.setCancelable(false);
		alertBuilder.setTitle("SIM Monitor");
		alertBuilder.setMessage(issue);
		alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
			}
		});
		
		AlertDialog alert = alertBuilder.create();
		alert.show();
	}//end sub
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
