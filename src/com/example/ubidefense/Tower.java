package com.example.ubidefense;

import com.google.android.gms.maps.model.LatLng;

public class Tower {
	private int id;
	private int circleId;
	private int ownerID;
	
	private int battery;
	private int signal;
	private double radius;
	
	private LatLng location;
	
	private int nMonsters;
	private Boolean isDrawn;
	private static final int RADIUS_RATIO = 1;
	private static final int PROX_RADIUS = 1;
	
	public Tower(int player, int battery, LatLng location, int signal, int id)
	{
		ownerID = player;
		
		this.battery = battery;
		this.location = location;
		this.signal = signal;
		this.id = id;
		
		radius = signal*RADIUS_RATIO;
		nMonsters = 0;
		isDrawn = false;
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
	public boolean checkMonsters(long distance)
	{	

		if(distance <= PROX_RADIUS)
		{
			nMonsters++;
			return true;
		}else
			return false;
	}
	
	public LatLng getPosition()
	{
		return location;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public Boolean getisDrawn()
	{
		return isDrawn;
	}
	
	public void setisDrawn()
	{
		isDrawn = true;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return id;
	}
	
	public Boolean isDead()
	{
		return (battery <= 0) ? true : false;
	}
}
