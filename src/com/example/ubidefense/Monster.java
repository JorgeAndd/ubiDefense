package com.example.ubidefense;

public class Monster {
	private int strenght;
	private Point position;
	private boolean onTower = false;
	private int onTowerid;
	private Point target;
	private static final float SPEED = 0.01f;
	
	public Monster(Point position, int strenght, Point target)
	{
		this.position = position;
		this.strenght = strenght;
		this.target = target;
	}
	
	
	/**Check if monster. If the monster is dead, return the tower the monster were attacking, if it weren't on any tower return -1.
	 *Otherwise return -1
	 *@param dt delta time
	 */
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
		if(!onTower)
		{
			position.x += (target.x - position.x)*SPEED;
			position.y += (target.y - position.y)*SPEED;
		}
	}
	
	/*
	 * remove monster's current target
	 */
	public void removeTarget()
	{
		onTower = false;
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
	public void setTarget(Point target)
	{
		this.target = target;
	}
}
