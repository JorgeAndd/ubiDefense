package com.example.ubidefense;

import android.util.SparseArray;

public class ArenaWrapper{
	public final SparseArray<Tower> towers;
	public final SparseArray<Monster> monsters;
	
	public ArenaWrapper(SparseArray<Tower> towers, SparseArray<Monster> monsters)
	{
		this.towers = towers;
		this.monsters = monsters;
	}
}
