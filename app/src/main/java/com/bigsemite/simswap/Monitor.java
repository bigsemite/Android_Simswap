package com.bigsemite.simswap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.CellLocation;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.Toast;


public class Monitor extends BroadcastReceiver{

	TelephonyManager tm;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		//String myPhone="";
		String recovery1, recovery2;
			tm = (TelephonyManager) arg0.getSystemService(Context.TELEPHONY_SERVICE);
			
			//myPhone = tm.getLine1Number(); //get the phone number

		//check for bootcompleted
		if(arg1.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){
			myUtil.sched(arg0);
		}
		
		SharedPreferences sp1 = arg0.getSharedPreferences("myPhoneSecure", Context.MODE_PRIVATE);
		String checker = sp1.getString("MyNumber", ".");
		
		GsmCellLocation gs = (GsmCellLocation)tm.getCellLocation();
	
		//int LocId = gs.getCid();
		
		if(tm.getSimSerialNumber().equals(checker)){
			//no issue here
			Toast.makeText(arg0, "SimSwap Monitor is configured on this device. No SIM card changed!", Toast.LENGTH_LONG).show();
		}
		else{
			//check for the stored recovery numbers
			recovery1 = sp1.getString("Phone1", ".");
			recovery2 = sp1.getString("Phone2", ".");
			
			
			
			if (!(recovery1.equals("."))){
				int LocArea = gs.getLac();
				String nNm = tm.getSimOperatorName();
				String cl= "Cell Area code/Operator " + Integer.toString(LocArea) +"/"+ nNm;
				String devId = tm.getDeviceId() + "\nLocation: " + cl;

				SmsManager sender = SmsManager.getDefault();
				
					sender.sendTextMessage(recovery1, null,"Alert! \nSim card change observed on your device. \nIMEI number of your device is: " + devId, null, null);
					
			}
			else{
				Toast.makeText(arg0, "Sim card in Slot1 of this device is changed. No recovery number is configured!", Toast.LENGTH_LONG).show();
				
				/*
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			*/
		}
		
			if (arg1.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			
				recovery1 = sp1.getString("Phone1", ".");
				recovery2 = sp1.getString("Phone2", ".");
				
				if(tm.getLine1Number().equals(checker)){
					//no issue here
					Toast.makeText(arg0, "SimSwap Monitor is configured on this device. No SIM card changed!", Toast.LENGTH_LONG).show();
				}
				else{
					//check for the stored recovery numbers
					//recovery1 = sp1.getString("Phone1", ".");
					//recovery2 = sp1.getString("Phone2", ".");
					int LocArea = gs.getLac();
					String nNm = tm.getSimOperatorName();
					String cl= "Cell Area code/Operator " + Integer.toString(LocArea) +"/"+ nNm;
					String devId = tm.getDeviceId() + "\nLocation: " + cl;

					if (!(recovery1.equals("."))){
						SmsManager sender = SmsManager.getDefault();
						
						
							sender.sendTextMessage(recovery1, null,"Alert! \nSim card change observed on your device. \nIMEI number of your device is: " + devId, null, null);
							
						/*
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						
							//sender.sendTextMessage(recovery2, null,"Alert! \nSim card change observed on your device. \nIMEI number of your device is: " + devId, null, null);
					}
					else{
						Toast.makeText(arg0, "Sim card in Slot1 of this device is changed. No recovery number is configured!", Toast.LENGTH_LONG).show();
						/*
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					*/
				}
			}
		}
	}

	}
}
