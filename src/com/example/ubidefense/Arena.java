package com.example.ubidefense;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class Arena {
	private LatLng[] limits = new LatLng[4];
	private LatLng start, end;
	private List<Tower> towers = new ArrayList<Tower>();
	private SparseArray<Monster> monsters = new SparseArray();

	private List<Player> players = new ArrayList<Player>();
	private float timer;	
	private static final int RATE = 6; 
	private boolean startSetted = false;
	private boolean setted = false;
	private int lives = 20;
		
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
	public Boolean update(GoogleMap map)
	{		
		//Updates monsters positions
		for(int i = 0; i < monsters.size(); i++)
		{
			Monster m = monsters.valueAt(i);
			
			double distance;
			double minDistance = 0;
			Tower closestTower = null;
			
			m.update();
			/*
			//Check if monster is atracted to tower
			for(Tower t : towers)
			{
				//Check distance between monster and tower
				Point diff = new Point((m.getPosition().longitude - t.getPosition().longitude), (m.getPosition().latitude - t.getPosition().latitude));
				distance = Math.sqrt((diff.x*diff.x) + (diff.y*diff.y));
				
				//Check if monster is inside tower's radius
				if(distance <= t.getRadius())
				{
					if(distance < minDistance || closestTower == null)
					{
						minDistance = distance;
						closestTower = t;
					}
				}	
			}
			m.setTarget(closestTower.getPosition());
			if(closestTower.checkMonsters(minDistance))
				m.setOnTower(closestTower.getId());
			
			*/
			//Check if monster reached end of arena
			Point diff = new Point((m.getPosition().longitude - end.longitude), (m.getPosition().latitude - end.latitude));
			double lenght = Math.sqrt((diff.x*diff.x) + (diff.y*diff.y));
			
			if(lenght <= 0.000001)
			{			
				m.kill();
				
				lives--;
			}
		}		
		
		
		//Create monster
		timer += 1.f/60.f;
		
		if(timer >= RATE)
		{
			timer = 0;
			
			return true;
		}
		
		return false;
		

	}
	
	public void addTower(int player, int battery, LatLng location, int signal, int id)
	{		
		towers.add(id, new Tower(player, battery, location, signal, id));	
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
	
	public List<Tower> getTowers()
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
}
