package com.example.ubidefense;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class Arena {
	private LatLng[] limits = new LatLng[4];
	private LatLng start, end;
	private SparseArray<Tower> towers = new SparseArray<Tower>();
	private SparseArray<Monster> monsters = new SparseArray<Monster>();

	private List<Player> players = new ArrayList<Player>();
	private float timer;	
	private static final int RATE = 10; 
	private boolean startSetted = false;
	private boolean setted = false;
	private int lives = 20;
	
	private boolean needMonsters = false;
		
	public void setArenaLimit(LatLng limit)
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
			
			LatLng c = new LatLng((start.latitude + end.latitude)/2.f, (start.longitude + end.longitude)/2.f);
			LatLng d = new LatLng((start.latitude - end.latitude)/2.f, (start.longitude - end.longitude)/2.f);
			
			limits[1] = new LatLng(c.latitude + d.longitude, c.longitude - d.latitude);			
			limits[3] = new LatLng(c.latitude - d.longitude, c.longitude + d.latitude);
			
			timer = RATE;
			
			setted = true;
		}

	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
	}

	
	/*
	 * updates arena components: monsters
	 * 
	 * @return true if a monster need to be created
	 */
	public void update()
	{	
		//Updates towers
		for(int i = 0; i < towers.size(); i++)
			towers.valueAt(i).update();
		
		//Updates monsters positions
		for(int i = 0; i < monsters.size(); i++)
		{
			Monster m = monsters.valueAt(i);
			
			double distance;
			double minDistance = 0;
			Tower closestTower = null;
			
			monsters.valueAt(i).update();
			
			int onTower = m.getTowerId(); 
			//Check if monster is attracted to a dead tower
			if(onTower != -1 && towers.get(onTower) == null)
			{
				m.setOnTower(-1);
				m.setTarget(end);
			}		
			//Check if monster is attracted to tower
			for(int j = 0; j < towers.size(); j++)
			{
				Tower t = towers.valueAt(j);
				
				//Check distance between monster and tower
				distance = Auxiliar.distance(m.getPosition(), t.getPosition());
				
				//Check if monster is inside tower's radius
				if(distance <= t.getSignal())
				{
					if(distance < minDistance || closestTower == null)
					{
						minDistance = distance;
						closestTower = t;
					}
				}	
			}
			
			
			if(closestTower != null && m.getTowerId() != closestTower.getId())
			{
				m.setTarget(closestTower.getPosition());
				if(closestTower.getPosition().equals(m.getPosition()))
				{
					m.setOnTower(closestTower.getId());
					closestTower.addMonster();
				}
			}
			
			//Check if monster reached end of arena
			if(m.getPosition().equals(end))
			{			
				m.kill();
				
				lives--;
			}
		}		
		
		
		//Create monster
		timer += 1.f/30.f;
		
		if(timer >= RATE)
		{
			timer = 0;
			
			needMonsters = true;
		}		

	}
	
	public void addTower(int player, int battery, LatLng location, int signal, int id)
	{		
		towers.append(id, new Tower(player, battery, location, signal, id));	
	}
	
	/*
	 * Add monster to monster list
	 * @param id monster id
	 * @return new monster created
	 */
	public Monster addMonster(int id)
	{
		Monster m = new Monster(start, 10, end, id);
		
		monsters.append(id, m);
		
		needMonsters = false;
		
		return m;
	}
	
	/*
	 * Return array of arena limits
	 */
	public LatLng[] getLimits()
	{
		return limits;
	}
	
	public SparseArray<Monster> getMonsters() {
		return monsters;
	}
	
	public SparseArray<Tower> getTowers()
	{
		return towers;
	}
		
	/*
	 * Check if the arena is already setted
	 */
	public boolean checkArena()
	{
		return setted;
	}
	
	public void removeTower(int Towerid)
	{		
		towers.remove(Towerid);	
	}
	
	public void removeMonster(int Monsterid)
	{	
		monsters.remove(Monsterid);
	}
	
	public boolean needMonster()
	{
		return needMonsters;
	}
	
	public int getLives()
	{
		return lives;
	}
}
