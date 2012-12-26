package com.raimsoft.spacetrader;

import org.usergrid.android.client.callbacks.ApiResponseCallback;
import org.usergrid.java.client.response.ApiResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import bayaba.engine.lib.GameInfo;

import com.kth.baasio.Baasio;
import com.kth.baasio.auth.AuthUtils;

public class SpaceTrader extends Activity implements SensorEventListener
{
	private GLView GLView;
	private Game game;
	public GameInfo gInfo;
	
	private SensorManager sManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);      
        
        Baasio.getInstance().init(this,
        		"https://api.baas.io",
        		"june",
        		"spacetrader");
        
//        AuthUtils.signup(
//        	    this,                        // context
//        	    "raimsoft",                     // username  (애플리케이션 내의 유일한 값)
//        	    "choi jun hyeok",              // full name
//        	    "test@test.com",             // e-mail    (애플리케이션 내의 유일한 값)
//        	    "testtest",                     // password
//        	    new ApiResponseCallback() {
//        	        @Override
//        	        public void onException(Exception e) { }            // Exception 발생
//
//        	        @Override
//        	        public void onResponse(ApiResponse response)
//        	        {
//        	        	if(response==null)
//        	        		ShowAlertDialog("[회원가입]", "회원가입 실패", "확인");
//        	        	else
//        	        		ShowAlertDialog("[회원가입]", response.toString(), "확인");
//        	        }    // 결과
//        	    });
        
        AuthUtils.login(
        	    this,                    // context
        	    "raimsoft",                 // username
        	    "testtest",                 // password
        	    new ApiResponseCallback()
        	    {
        	        @Override
        	        public void onException(Exception e) { }            // Exception 발생

        	        @Override
        	        public void onResponse(ApiResponse response)
        	        {
        	        	if(response==null)
        	        		ShowAlertDialog("[로그인]", "로그인 실패", "확인");
        	        	else
        	        		ShowAlertDialog("[로그인 성공]", response.toString(), "확인");
        	        }    // 결과
        	    });

        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        sManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        
        gInfo = new GameInfo( 480, 800 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        game = new Game( this, gInfo );
        GLView = new GLView( this, game );
        GLView.setRenderer( new SurfaceClass(game) );
        
        setContentView( GLView );
    }
    
    
    @Override
	public void onBackPressed()
	{
    	game.onBackPressed();
	}

	@Override
	public void onSensorChanged( SensorEvent event )
	{
		synchronized (this)
		{
			switch ( event.sensor.getType() )
			{
				case	Sensor.TYPE_ACCELEROMETER:
						GlobalInput.fSensorX = event.values[0];
						break;
			}
		}
	}
    
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    };

	@Override
	public void onResume()
	{
		super.onResume();
		
		sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
	}
	
    @Override
    protected void onPause()
    {
    	super.onPause();
    }
    
	@Override
	public void onStop()
	{
		super.onStop();
		
		sManager.unregisterListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		sManager.unregisterListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}  
	
	
	private void ShowAlertDialog(String strTitle, String strContent, String strButton)
	{
		new AlertDialog.Builder(this)
		.setTitle( strTitle )
		.setMessage( strContent )
		.setPositiveButton( strButton , null)
		.setCancelable(false)
		.create()
		.show();
	}
}