package com.example.ubidefense;

import com.google.android.gms.maps.model.LatLng;

public class Auxiliar {
	public static double distance(LatLng point1, LatLng point2)
	{
	    double earthRadius = 6371 ;
	    double dLat = Math.toRadians(point2.latitude-point1.latitude);
	    double dLng = Math.toRadians(point2.longitude-point1.longitude);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(point1.latitude)) * Math.cos(Math.toRadians(point2.latitude));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist*1000;
	}
}
