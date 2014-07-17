package com.example.ubidefense;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MainActivity extends Activity {
	  private GoogleMap map;
	  private GameLoop game = new GameLoop(this);
	  private WifiManager mainWifi;
	  private int signal = 0;
	  private LocationManager locationManager;
	  
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
		mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	        .getMap();
	    
	    map.getUiSettings().setZoomControlsEnabled(false);
		map.getUiSettings().setZoomGesturesEnabled(false);
		map.getUiSettings().setMyLocationButtonEnabled(false);

	    map.setMyLocationEnabled(true);
	    
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    
	    Criteria criteria = new Criteria();
	    Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
	    
	    if(location != null)
	    {
	    	LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
	    	
	    	map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 30));
	    }else
	    {
	    	new AlertDialog.Builder(this).setMessage("Nao foi possivel acessar sua localizacao").show(); 	
	    }
	    
	    setMapListener();
	    game.setMap(map);
	  }
	  
	  
	  
	  private void setMapListener()
	  {
		  map.setOnMapLongClickListener(new OnMapLongClickListener() {
				
				@Override
				public void onMapLongClick(LatLng latLng) {					
					//Check if arena is already setted
				    if(!game.getArena().checkArena())
				    {
				    	//Do this when click if arena is not setted
				    	map.addCircle(new CircleOptions().center(latLng).fillColor(Color.GRAY).strokeColor(Color.TRANSPARENT).radius(0.5d));
				    	
				    	if(game.setUpArena(latLng))
				    	{
				    		Button button = (Button)findViewById(R.id.towerButton);
				    		
				    		button.setClickable(true);
				    		startWorker();
				    	}
				    }
				    else
				    {
				    	//Do this when click if arena is setted
				    	
						  //int signal = mainWifi.getScanResults().size();
						  
						  signal++;		    	

						  game.addTower(0, 500, latLng, signal);
						  
				    }
				}
			});		
	  }
	  
	  public void towerButtonClk(View view)
	  {
		  Criteria criteria = new Criteria();
		  
		  Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
		  
		  game.addTower(0, 50, new LatLng(location.getLatitude(), location.getLongitude()), signal);
	  }
	  
	  private void startWorker()
	  {
		  game.execute();
	  }
	  
	  
}