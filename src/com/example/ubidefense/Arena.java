package com.example.ubidefense;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Arena {
	private LatLng[] limits = new LatLng[4];
	private LatLng start, end;
	private List<Tower> towers = new ArrayList<Tower>();
	private List<Monster> monsters = new ArrayList<Monster>();
	private List<Marker> obsoleteMarkers = new ArrayList<Marker>();

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
	
	public void addMonster(Marker marker)
	{
		Monster monster = new Monster(start, 10, end, marker);
		
		monsters.add(monster);
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
	
	/*
	 * updates arena components: monsters
	 * 
	 * @return true if a monster need to be created
	 */
	public Boolean update(GoogleMap map)
	{
		//checkMonstersTowers();
		
		//Updates monsters positions
		for(Monster m : monsters)
		{
			m.update();
			
			//Check if monster reached end of arena
			
			Point diff = new Point((m.getPosition().longitude - end.longitude), (m.getPosition().latitude - end.latitude));
			double lenght = Math.sqrt((diff.x*diff.x) + (diff.y*diff.y));
			
			if(lenght <= 0.000001)
			{
				Marker marker = m.getMarker();
				obsoleteMarkers.add(marker);				
				monsters.remove(m);
				
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
	
	public void createTower(int player, int battery, LatLng location, int signal)
	{		
		towers.add(new Tower(player, battery, location, signal));	
	}
		
	/*
	 * Clear the list of obsolete markers
	 */
	public void clearObsolete()
	{
		obsoleteMarkers.clear();
	}
	
	/*
	 * Return array of arena limits
	 */
	public LatLng[] getLimits()
	{
		return limits;
	}
	
	public List<Monster> getMonsters() {
		return monsters;
	}
	
	/*
	 * Get list of obsolete markers
	 */
	public List<Marker> getObsoletMarkers()
	{
		return obsoleteMarkers;
	}
	
	/*
	 * Check if the arena is already setted
	 */
	public boolean checkArena()
	{
		return setted;
	}
}
