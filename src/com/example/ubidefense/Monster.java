package com.example.ubidefense;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;

public class Monster {
	private int id;
	private int strenght;
	private LatLng position;
	private boolean onTower = false;
	private int onTowerid;
	private LatLng target;
	
	private static final float SPEED = 0.1f;
	private static final float TIME_STEP = 1.f/60.f;
	
	public Monster(LatLng position, int strenght, LatLng target, int id)
	{
		this.position = position;
		this.strenght = strenght;
		this.target = target;
		this.id = id;
	}
	
	
	/**If the monster is dead, return the tower the monster were attacking, if it weren't on any tower return -1.
	 *Otherwise return -1
	 *@param dt delta time
	 */
	public int update()
	{
		move();
		
		//strenght -= 1;
		
		//Check if monster is alive
		if(strenght <= 0)
			return onTowerid;
		else
			return -1;
		
	}
	
	public void move()
	{
		//Check if monster is not attacking a tower
		if(!onTower)
		{
			
			double dx = target.longitude - position.longitude;
			double dy = target.latitude - position.latitude;
			
			double lenght = Math.sqrt((dx*dx) + (dy*dy));
			
			//Normalize d "vector"
			dx /= lenght;
			dy /= lenght;
			
			dx *= 0.000001;
			dy *= 0.000001;
			
			position = new LatLng(position.latitude + dy, 
									position.longitude + dx);
		}
	}
	
	/*
	 * remove monster's current target
	 */
	public void removeTarget()
	{
		onTower = false;
	}
	
	/*
	 * set monster strenght to 0
	 */
	public void kill()
	{
		strenght = 0;
	}
	
	//
	//Getters
	//
	public LatLng getPosition()
	{
		return position;
	}
	
	public int getId()
	{
		return id;
	}
	
	//
	//Setters
	//
	
	/*
	 * set the tower the monster is attacking
	 * @param towerId id of the tower the monster is attacking now
	 */
	public void setOnTower(int towerId)
	{
		onTowerid = towerId;
	}
	
	/*
	 * set the target tower or location
	 * @param target point to which the monster will go
	 */
	public void setTarget(LatLng target)
	{
		this.target = target;
	}
	
	public boolean isDead()
	{
		return false;
		
		//return (strenght <= 0) ? true : false;
	}
}
