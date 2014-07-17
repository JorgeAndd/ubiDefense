package com.example.ubidefense;

import android.location.Location;

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
	public boolean drawn = false;
	
	private static final float SPEED = 1f;
	private static final float TIME_STEP = 1.f/30.f;
	
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
			
			double lenght = Auxiliar.distance(target, position);
			
			if(lenght > SPEED * TIME_STEP)
			{
				dx *= (SPEED * TIME_STEP)/lenght;
				dy *= (SPEED * TIME_STEP)/lenght;
			}
			
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
	
	public int getTowerId()
	{
		return onTowerid;
	}
	
	//
	//Setters
	//
	
	/*
	 * set the tower the monster is attacking
	 * @param towerId id of the tower the monster is attacking now. -1 if is not in any tower
	 */
	public void setOnTower(int towerId)
	{
		if(towerId == -1)
			onTower = false;
		
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
		return (strenght <= 0) ? true : false;
	}
}
