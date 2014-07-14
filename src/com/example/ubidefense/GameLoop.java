package com.example.ubidefense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.SparseArray;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class GameLoop extends AsyncTask<Void, Void, Void> {
	private Arena arena;
	private boolean running = false;
	private GoogleMap map;
	private int needMonster = 0;
	
	private List<Circle> towersCircles = new ArrayList<Circle>();
	private List<Circle> radiusCircles = new ArrayList<Circle>();
	private SparseArray<Circle> monstersCircles = new SparseArray();
	
	public GameLoop() {
		arena = new Arena();
	}

	public Boolean setUpArena(LatLng location) {

		arena.setArenaLimit(location);

		// Arena is set
		if (arena.checkArena()) {
			drawArena();

			running = true;
			return true;
		}
		return false;
	}

	protected Void doInBackground(Void... params) {
		
		float accumulator, lastTime;
		float dt = 1.f/60.f;
		
		lastTime = SystemClock.uptimeMillis()/1000.f;
		accumulator = 0.f;
		
		while (running) {
			float nowTime = SystemClock.uptimeMillis()/1000.f;
			float frameTime = nowTime - lastTime;
			lastTime = nowTime;
			accumulator += frameTime;
			
			//Update stuff
			if(accumulator >= dt)
			{
				if(arena.update(map))
					needMonster++;
				
				accumulator -= dt;
			}
			//Render stuff
			publishProgress();
		}

		return null;

	}

	/*
	 * Do every render related operations
	 */
	public void onProgressUpdate(Void... params) {
		//Move monsters
		
		for(int i = 0; i < arena.getMonsters().size();) {
			Monster m = arena.getMonsters().valueAt(i);
			
			int id = m.getId();
			Circle circle = monstersCircles.get(id);
			
			if(m.isDead())
			{
				arena.removeMonster(id);
				circle.remove();
				monstersCircles.remove(id);
			}else
			{
				circle.setCenter(m.getPosition());
				i++;
			}
		}
		
		//Draw monsters
		while(needMonster > 0)
		{
			addMonster();
			
			needMonster--;
		}
		
		//Update towers	
		for(Tower t : arena.getTowers())
		{
			int id = t.getId();
			
			//Check if tower is already drawn	
			if(t.getisDrawn() == false)
			{				
				//Draws tower 
				Circle newCircle = map.addCircle(new CircleOptions().center(t.getPosition()).fillColor(Color.GREEN).strokeColor(Color.TRANSPARENT).radius(0.5d));
				towersCircles.add(id, newCircle);
				
				//Draws tower radius				
				newCircle = map.addCircle(new CircleOptions().center(t.getPosition()).radius(t.getRadius()).strokeColor(Color.TRANSPARENT).fillColor(Color.argb(50, 49, 215, 45)));
				radiusCircles.add(id, newCircle);
				
				t.setisDrawn();
			}
			
			//Check if tower is dead
			if(t.isDead())
			{
				Circle circle = towersCircles.get(id);
				circle.remove();
				
				circle = radiusCircles.get(id);
				circle.remove();
				
				arena.removeTower(id);
			}		
		}
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	void drawArena() {
		LatLng limits[] = arena.getLimits();

		PolygonOptions poly = new PolygonOptions()
				.add(limits[0], limits[1],
						limits[2], limits[3])
				.strokeColor(Color.CYAN).strokeWidth(2);

		map.addPolygon(poly);
	}

	public Arena getArena() {
		return arena;
	}

	public void addTower(int player, int battery, LatLng location, int signal) {
		arena.addTower(player, battery, location, signal, towersCircles.size());
	}
	
	public void addMonster()
	{
		int id = monstersCircles.size();
		Monster m = arena.addMonster(id);
		
		Circle newCircle = map.addCircle(new CircleOptions().center(m.getPosition()).fillColor(Color.YELLOW).strokeColor(Color.TRANSPARENT).radius(0.5d));
		monstersCircles.append(id, newCircle);
	}	
	
}
