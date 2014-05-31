package com.example.ubidefense;

public class Tower {
	private int battery;
	private Point location;
	private int signal;
	private int nMonsters;
	private static final int RADIUS = 1;
	
	public Tower(int battery, Point location, int signal)
	{
		this.battery = battery;
		this.location = location;
		this.signal = signal;
		nMonsters = 0;
	}
	
	public void update(float lossRate)
	{
		battery -= (lossRate*nMonsters); 
	}
	
	public boolean checkMonsters(Point mPos)
	{
		int dx = Math.abs(mPos.x - location.x);
		int dy = Math.abs(mPos.y - location.y);
		
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
	
	public Point getLocation()
	{
		return location;
	}
}
