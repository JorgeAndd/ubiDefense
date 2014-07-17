package com.example.ubidefense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

public class GameLoop extends AsyncTask<Void, ArenaWrapper, Void> {	
	private Arena arena;
	private boolean running = false;
	private GoogleMap map;
	private Activity activityContext;
	
	private List<Circle> towersCircles = new ArrayList<Circle>();
	private List<Circle> radiusCircles = new ArrayList<Circle>();
	private SparseArray<Marker> monstersCircles = new SparseArray<Marker>();
	
	
	public GameLoop(Activity activity) {
		arena = new Arena();
		activityContext = activity;
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
		
		double SKIP_TICKS = 1000/10.0D;
		int MAX_FRAMESKIP = 3;
		
		double next_game_tick = SystemClock.uptimeMillis();
		int loops;
		
		while (running) {
			loops = 0;
			while(SystemClock.uptimeMillis() > next_game_tick && loops < MAX_FRAMESKIP)
			{
				arena.update();
				//Add just one monster, for test purposes
				if(arena.getMonsters().size() < 1)
					addMonster();
				
				next_game_tick += SKIP_TICKS;
				loops++;
			}
			//Render stuff
			publishProgress(new ArenaWrapper(arena.getTowers(), arena.getMonsters()));
		}
		return null;
	}
	/*
	 * Do every render related operations
	 */
	public void onProgressUpdate(ArenaWrapper... objects) {
		SparseArray<Tower> towers = objects[0].towers;
		SparseArray<Monster> monsters = objects[0].monsters;
		
		//Updates lives
		int lives = arena.getLives();
		TextView livesT= (TextView)activityContext.findViewById(R.id.livesText);
		livesT.setText("Vidas: " + lives);
		
		
		//Move monsters
		for(int i = 0; i < monsters.size();) {
			Monster m = monsters.valueAt(i);
			
			int id = m.getId();
						
			//Check if monster circle is already drawn. If not, draw 
			if(monsters.valueAt(i).drawn == false)
			{
				Marker newCircle = map.addMarker(new MarkerOptions().position(m.getPosition())
																		.icon(BitmapDescriptorFactory.fromAsset("skull.png"))
																		.anchor(0.5f, 0.5f));
				monstersCircles.append(m.getId(), newCircle);
				monsters.valueAt(i).drawn = true;
			}
			
			Marker circle = monstersCircles.get(id);
			
			if(m.isDead())
			{
				arena.removeMonster(id);
				circle.remove();
				monstersCircles.remove(id);
			}else
			{
				circle.setPosition(m.getPosition());
				i++;
			}
		}

		
		//Updates towers	
		for(int i = 0; i < towers.size(); i++)
		{
			Tower t = towers.valueAt(i);
			
			int id = t.getId();
			
			//Check if tower is already drawn	
			if(t.getisDrawn() == false)
			{				
				//Draws tower 
				Circle newCircle = map.addCircle(new CircleOptions().center(t.getPosition()).fillColor(Color.GREEN).strokeColor(Color.TRANSPARENT).radius(0.5d));
				towersCircles.add(id, newCircle);
				
				//Draws tower radius				
				newCircle = map.addCircle(new CircleOptions().center(t.getPosition()).radius(t.getSignal()).strokeColor(Color.TRANSPARENT).fillColor(Color.argb(50, 49, 215, 45)));
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
		arena.addMonster(id);
	}	
	
}
