package com.example.ubidefense;

import com.google.android.gms.maps.model.LatLng;

public class Tower {
	private int id;
	private static int idCounter = 0;
	private int ownerID;
	
	private int battery;
	private int signal;
	
	private LatLng location;
	
	private int nMonsters;
	private static final int RADIUS = 1;
	
	public Tower(int player, int battery, LatLng location, int signal)
	{
		id = idCounter++;
		ownerID = player;
		this.battery = battery;
		this.location = location;
		this.signal = signal;
		nMonsters = 0;
	}
	
	public void update(float lossRate)
	{
		battery -= (lossRate*nMonsters); 
	}
	
	/*
	 * Check if there is a monster near the tower
	 * @param mPos position of the monster which is being checked
	 * @return true if the monster is near, false otherwise
	 */
	public boolean checkMonsters(LatLng mPos)
	{
		
		
		double dx = Math.abs(mPos.longitude - location.longitude);
		double dy = Math.abs(mPos.latitude - location.latitude);
		
		if(dx > RADIUS || dy > RADIUS || (dx+dy) <= RADIUS)
			return false;
		else if(Math.pow(dx, 2) + Math.pow(dy, 2) <= Math.pow(RADIUS, 2))
		{	
			nMonsters++;
			return true;	
		}
		else
			return false;
	}
	
	public LatLng getLocation()
	{
		return location;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return id;
	}
}
