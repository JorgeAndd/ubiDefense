package com.example.ubidefense;

public class Player {
	private String name;
	private int id;
	private static int idCounter = 0;
	private float cooldown;

	public Player(String name)
	{
		this.name = name;
		id = idCounter++;
	}
	
	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
	
	
}
