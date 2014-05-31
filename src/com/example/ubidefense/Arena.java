package com.example.ubidefense;

import java.util.ArrayList;
import java.util.List;

public class Arena {
	private Point[] limits = new Point[4];
	private Point start, end;
	private List<Tower> towers = new ArrayList<Tower>();
	private List<Monster> monsters = new ArrayList<Monster>();
	private int timer;	
	private static final int RATE = 600; 
	
	public Arena(Point start, Point end)
	{
		timer = 0;
		
		this.start = start;
		this.end = end;
		
		//Set limits of the arena square, with start as the first point and end as the last third.
		//The points are set in a clock-wise orientation
		limits[0] = start;
		
		limits[1].x = end.x;
		limits[1].y = start.y;	
		
		limits[2] = end;
		
		limits[3].x = start.x;
		limits[3].y = end.y;
	}
	
	public void checkMonstersTowers()
	{
		for(Tower t : towers)
		{
			for(Monster m : monsters)
			{
				if(t.checkMonsters(m.getPosition()))
				{
					m.setOnTower(true);
					m.setTarget(t.getLocation());
				}
				else
					m.setOnTower(false);
					
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
	
	public void createTower(int battery, Point location, int signal)
	{		
		towers.add(new Tower(battery, location, signal));	
	}
	
	public Point[] getLimits()
	{
		return limits;
	}
}
