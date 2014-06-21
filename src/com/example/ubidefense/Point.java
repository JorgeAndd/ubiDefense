package com.example.ubidefense;

import com.google.android.gms.maps.model.LatLng;

public class Point {
	public double x;
	public double y;
	
	public Point()	{};
	
	public Point(double longitude, double latitude)
	{
		this.x = longitude;
		this.y = latitude;
	}
	
	public static Point toPoint(LatLng latlng)
	{
		Point point =  new Point(latlng.longitude, latlng.latitude);
		
		return point;
	}
	
	public LatLng toLatLng()
	{
		LatLng coord = new LatLng(y, x);
		return coord;
	}
	
}
