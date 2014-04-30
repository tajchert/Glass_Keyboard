package pl.tajchert.glassware.keyboard;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class KeyboardActivity extends Activity implements
		SensorEventListener {

	private TextView mOneLeft;
	private TextView mOneMiddle;
	private TextView mOneRight;
	
	private TextView mTwoLeft;
	private TextView mTwoMiddle;
	private TextView mTwoRight;
	
	private TextView mThreeLeft;
	private TextView mThreeMiddle;
	private TextView mThreeRight;
	
	private TextView mFourLeft;
	private TextView mFourMiddle;
	private TextView mFourRight;
	
	private TextView mTop;
	
	private SensorManager mSensorManager;
	private Sensor mOrientation;
	private AudioManager audio;
	
	private boolean firstRun = true;
	private int firstAngleValH; // Horizontal
	private int angleValH = 0; // Horizontal
	private int firstAngleValV; // Vertical
	private int angleValV = 0; // Vertical
	//private String currentMiddle;

	Float azimuth_angle;
	Float pitch_angle;
	Float roll_angle;

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
			end();
			/*Log.i(Tools.AWESOME_TAG, "KEYCODE_DPAD_CENTER");
			if(Tools.saved.length() < Tools.inputLength){
				Tools.saved += currentMiddle;
				//mTop.setText(Tools.saved);
			}
			if(Tools.saved.length() == Tools.inputLength){
				audio.playSoundEffect(Sounds.SUCCESS);
				end();
			}else{
				audio.playSoundEffect(Sounds.TAP);
			}*/
			return true;
		}
		return false;
	}
	
	private void end(){
		
		mSensorManager.unregisterListener(this);
		this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.select_number);
		
		setTextFields();
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mOrientation, 800000);
		//mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		int azimuth_angle = (int) event.values[0];
		int pitch_angle = (int) event.values[1];
		if(firstRun){
			firstAngleValH = azimuth_angle;
			firstAngleValV = pitch_angle;
			firstRun = false;
		}
		angleValH = (azimuth_angle - firstAngleValH);
		angleValV = (pitch_angle - firstAngleValV);
		//Log.d(Tools.AWESOME_TAG, "angleValH: " + angleValH);
		//Log.d(Tools.AWESOME_TAG, "angleValV: " + angleValV+" - "+ angleValV +"/" + firstAngleValV);
		if(Math.abs(angleValV)> Tools.maxVerticalAngle){
			firstAngleValV = (pitch_angle - Tools.sliceSizeV);
			angleValV = (pitch_angle - firstAngleValV);
		}
		if(Math.abs(angleValH)>Tools.maxHorizontalAngle){
			Log.d(Tools.AWESOME_TAG, "angleValH: " + angleValH);
			firstAngleValH = (azimuth_angle - Tools.sliceSizeH);
			angleValH = (azimuth_angle - firstAngleValH);
		}
		new GetSymbol().execute(angleValH + "", ""+ angleValV);
	}

	private class GetSymbol extends AsyncTask<String, Void, String> {
		private int angle_horizontal;
		private int angle_vertical;
		private int rowNumber;
		private String [] rowContent;

		@Override
		protected String doInBackground(String... params) {
			angle_horizontal = Integer.parseInt(params[0]);
			angle_vertical = Integer.parseInt(params[1]);
			rowNumber = Tools.getRow(angle_vertical);
			
			rowContent = Tools.getRowContent(angle_horizontal, rowNumber);
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {
			//Log.d(Tools.AWESOME_TAG, "rowNumber: " + rowNumber);
			//Log.d(Tools.AWESOME_TAG, rowContent[0] + "," + rowContent[1] +"," + rowContent[2]);
			resetTextFields();
			switch(rowNumber){
			case 0:
				mOneLeft.setText(rowContent[0]);
				mOneMiddle.setText(rowContent[1]);
				mOneMiddle.setVisibility(View.VISIBLE);
				mOneRight.setText(rowContent[2]);
				break;
			case 1:
				mTwoLeft.setText(rowContent[0]);
				mTwoMiddle.setText(rowContent[1]);
				mTwoMiddle.setVisibility(View.VISIBLE);
				mTwoRight.setText(rowContent[2]);
				break;
			case 2:
				mThreeLeft.setText(rowContent[0]);
				mThreeMiddle.setText(rowContent[1]);
				mThreeMiddle.setVisibility(View.VISIBLE);
				mThreeRight.setText(rowContent[2]);
				break;
			case 3:
				mFourLeft.setText(rowContent[0]);
				mFourMiddle.setText(rowContent[1]);
				mFourMiddle.setVisibility(View.VISIBLE);
				mFourRight.setText(rowContent[2]);
				break;
				
			}
			//TODO update layout
			//mLeft.setText(rowContent[0] + "");
			//mMiddle.setText(rowContent[1] + "");
			//mRight.setText(rowContent[2] + "");
			//currentMiddle = rowContent[1];
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
	
	private void resetTextFields(){
		mOneLeft.setText(Tools.rows[0]);
		mOneMiddle.setText("");
		mOneMiddle.setVisibility(View.GONE);
		mOneRight.setText("");
		
		mTwoLeft.setText(Tools.rows[1]);
		mTwoMiddle.setText("");
		mTwoMiddle.setVisibility(View.GONE);
		mTwoRight.setText("");
		
		mThreeLeft.setText(Tools.rows[2]);
		mThreeMiddle.setText("");
		mThreeMiddle.setVisibility(View.GONE);
		mThreeRight.setText("");
		
		mFourLeft.setText(Tools.rows[3]);
		mFourMiddle.setText("");
		mFourMiddle.setVisibility(View.GONE);
		mFourRight.setText("");
	}
	
	private void setTextFields(){
		mOneLeft = (TextView) findViewById(R.id.oneleft);
		mOneMiddle = (TextView) findViewById(R.id.onemiddle);
		mOneRight = (TextView) findViewById(R.id.oneright);
		
		mTwoLeft = (TextView) findViewById(R.id.twoleft);
		mTwoMiddle = (TextView) findViewById(R.id.twomiddle);
		mTwoRight = (TextView) findViewById(R.id.tworight);
		
		mThreeLeft = (TextView) findViewById(R.id.threeleft);
		mThreeMiddle = (TextView) findViewById(R.id.threemiddle);
		mThreeRight = (TextView) findViewById(R.id.threeright);
		
		mFourLeft = (TextView) findViewById(R.id.fourleft);
		mFourMiddle = (TextView) findViewById(R.id.fourmiddle);
		mFourRight = (TextView) findViewById(R.id.fourright);
		
		//mTop = (TextView) findViewById(R.id.inputed); //TODO
	}
}
