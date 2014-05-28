package com.projects.riderj.entity;

import com.projects.riderj.level.Level;

public abstract class Mob extends Entity {
	public int maxHealth;
	public int health = maxHealth;
	protected int speed, steps = 0;
	protected String name;
	protected boolean isMoving;
	protected int movDir = 1;
	protected int scale = 1;
	
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move(int xa, int ya){
		if(xa !=0 && ya != 0) {
			move(xa,0);
			move(0,ya);
			steps--;
			return;
		}
		steps++;
		
		if(!hasCollided(xa,ya)){
			if(ya < 0) movDir = 0;
			if(ya > 0) movDir = 1;
			if(xa > 0) movDir = 2;
			if(xa > 0) movDir = 3;
			
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(int xa, int ya);
	
	public String getName(){
		return name;
	}

}
