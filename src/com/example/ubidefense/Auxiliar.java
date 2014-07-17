package com.example.ubidefense;

import com.google.android.gms.maps.model.LatLng;

public class Auxiliar {
	/*
	 * Returns the distance between two points in meters
	 */
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
	
	/*
	 * Returns the angle between two points in degrees
	 */
	public static double angle(LatLng point1, LatLng point2)
	{
		double longitude1 = point1.longitude;
		double longitude2 = point2.longitude;
		
		double latitude1 = Math.toRadians(point1.latitude);
		double latitude2 = Math.toRadians(point2.latitude);
		
		double longDiff = Math.toRadians(longitude2 - longitude1);
		
		double y= Math.sin(longDiff)*Math.cos(latitude2);
		double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);

		return (Math.toDegrees(Math.atan2(y, x))+360)%360;
	}
}
