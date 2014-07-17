package com.example.ubidefense;

import com.google.android.gms.maps.model.LatLng;

public class Tower {
	private int id;
	private int circleId;
	private int ownerID;
	
	private int battery;
	private int signal;
	private float lossRate = 0.1f;
	private double radius;
	
	private LatLng location;
	
	private int nMonsters;
	private Boolean isDrawn;
	
	public Tower(int player, int battery, LatLng location, int signal, int id)
	{
		ownerID = player;
		
		this.battery = battery;
		this.location = location;
		this.signal = signal;
		this.id = id;
		
		nMonsters = 0;
		isDrawn = false;
	}
	
	public void update()
	{
		if(nMonsters > 0)
			battery -= (lossRate*nMonsters);			
	}
	
	public void addMonster()
	{
		nMonsters++;
	}
	
	
	public LatLng getPosition()
	{
		return location;
	}
	
	public double getSignal()
	{
		return signal;
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
	
	public Boolean getisDrawn()
	{
		return isDrawn;
	}
}
