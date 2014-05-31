package com.example.ubidefense;

public class Monster {
	private int strenght;
	private Point position;
	private int onTowerid;
	private Point target;
	private static final float SPEED = 0.01f;
	
	public Monster(Point position, int strenght, Point target)
	{
		this.position = position;
		this.strenght = strenght;
		this.target = target;
	}
	
	//Updates monsters
	//If the monster is dead, return the tower the monster were attacking, if it weren't on any tower return -1.
	//Otherwise return -1
	public int update(int dt)
	{
		move();
		
		strenght -= dt;
		
		//Check if monster is alive
		if(strenght <= 0)
			return onTowerid;
		else
			return -1;
	}
	
	public void move()
	{
		//Check if monster is not attacking a tower
		if(onTowerid == 0)
		{
			position.x += (target.x - position.x)*SPEED;
			position.y += (target.y - position.y)*SPEED;
		}
	}
	
	//
	//Getters
	//
	public Point getPosition()
	{
		return position;
	}
	
	//
	//Setters
	//
	public void setOnTower(int towerId)
	{
		onTowerid = towerId;
	}
	
	public void setTarget(Point target)
	{
		this.target = target;
	}
}
