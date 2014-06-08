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
	
	public Point toPoint(LatLng latlng)
	{
		this.x = latlng.longitude;
		this.y = latlng.latitude;
		
		return this;
	}
	
	public LatLng toLatLng()
	{
		LatLng coord = new LatLng(y, x);
		return coord;
	}
	
}
