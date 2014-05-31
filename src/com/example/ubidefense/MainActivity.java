package com.example.ubidefense;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
	  static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	  static final LatLng KIEL = new LatLng(53.551, 9.993);
	  private GoogleMap map;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	        .getMap();
	    
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
	  }

}