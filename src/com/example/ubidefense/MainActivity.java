package com.example.ubidefense;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MainActivity extends Activity {
	  private GoogleMap map;
	  private GameLoop game = new GameLoop();
	  private WifiManager mainWifi;
	  private boolean running = true;
	  
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
	    
	    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    
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
	  
	  /*@Override
	  protected void onStart()
	  {
		  
		  //game.setMap(map);
	  }
	  */
	  private void setMapListener()
	  {
		  map.setOnMapClickListener(new OnMapClickListener() {
				
				@Override
				public void onMapClick(LatLng latLng) {
					MarkerOptions marker = new MarkerOptions();
					

					// Setting the position for the marker
					marker.position(latLng);
					
				    // Setting the title for the marker.
	  			    // This will be displayed on taping the marker
				    marker.title(latLng.latitude + " : " + latLng.longitude);
				
				    // Clears the previously touched position
				    //map.clear();
				
				    // Animating to the touched position
				    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
				
				    marker.icon(BitmapDescriptorFactory.fromAsset("portal.png"));
				    // Placing a marker on the touched position
				    map.addMarker(marker);
				  
				    
				    if(!game.getArena().checkArena())
				    {
				    	game.setUpArena(marker.getPosition());
				    	//new AlertDialog.Builder(MainActivity.this).setMessage("Arena ++").show(); 	
				    }
				    else
				    {
				    	int signal = mainWifi.getScanResults().size();
				    	
				    	new AlertDialog.Builder(MainActivity.this).setMessage("# Connections = " + signal).show(); 				    	
				    	 	
				    	//game.setTower(0, battery, location, signal);
				    }
				}
			});		
	  }
}