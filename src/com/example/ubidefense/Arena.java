package com.example.ubidefense;

import java.util.ArrayList;
import java.util.List;

public class Arena {
	private Point[] limits = new Point[4];
	private Point start, end;
	private List<Tower> towers = new ArrayList<Tower>();
	private List<Monster> monsters = new ArrayList<Monster>();
	private List<Player> players = new ArrayList<Player>();
	private int timer;	
	private static final int RATE = 600; 
	private boolean startSetted = false;
	private boolean setted = false;
		
	public void setArenaLimit(Point limit)
	{		
		if(!startSetted)
		{
			start = limit;
			startSetted = true;
		}else
		{
			end = limit;
			
			//Set limits of the arena square, with start as the first point and end as the last third.
			//The points are set in a clock-wise orientation
			limits[0] = start;
			limits[2] = end;
			
			limits[1] = new Point(end.x, start.y);			
			limits[3] = new Point(start.x, end.y);
			
			setted = true;
		}

	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
	}
	
	//Check if each monsters is on a tower, than set its course
	public void checkMonstersTowers()
	{
		//For each tower...
		for(Tower t : towers)
		{
			//For each monster...
			for(Monster m : monsters)
			{
				//Check if the monster is near the tower
				if(t.checkMonsters(m.getPosition()))
				{
					m.setOnTower(t.getId());
					m.setTarget(t.getLocation());
				}
				else
					m.removeTarget();
					
			}
		}
	}
	
	public void update(int dt)
	{
		checkMonstersTowers();
		
		timer += dt;
		
		if(timer >= RATE)
		{
			timer = 0;
			
			monsters.add(new Monster(start, 3600, end));
		}
	}
	
	public void createTower(int player, int battery, Point location, int signal)
	{		
		towers.add(new Tower(player, battery, location, signal));	
	}
	
	/*
	 * Return array of arena limits
	 */
	public Point[] getLimits()
	{
		return limits;
	}
	
	/*
	 * Check if the arena is already setted
	 */
	public boolean checkArena()
	{
		return setted;
	}
}
