package com.projects.riderj.entity;

import com.projects.riderj.game.InputHandler;
import com.projects.riderj.gfx.Colors;
import com.projects.riderj.gfx.Screen;
import com.projects.riderj.level.Level;


public class Player extends Mob{
	private InputHandler input;
	public Player(Level level,int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1);
		this.input = input;
	}
	

	public void tick() {
		int xa = 0;
		int ya = 0;
		
		if(input.up.isPressed()) ya--;
		if(input.down.isPressed()) ya++;
		if(input.left.isPressed()) xa--;
		if(input.right.isPressed()) xa++;
		
		if(xa !=0 || ya != 0){
			move(xa,ya);
			isMoving = true;
		}else{
			isMoving = false;
		}
		
	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier /2;
		int yOffset = y - modifier /2 - 4;
		
		screen.render(xOffset , yOffset, (xTile)+(yTile*32), Colors.get(-1,015,430,220));
		screen.render(xOffset + modifier, yOffset, (xTile+1) + (yTile * 32), Colors.get(-1,015,430,220));
		screen.render(xOffset , yOffset + modifier, (xTile)+((yTile+1)*32), Colors.get(-1,015,430,220));
		screen.render(xOffset + modifier, yOffset + modifier, (xTile+1)+((yTile+1)*32), Colors.get(-1,015,430,220));
	}

	public boolean hasCollided(int xa, int ya) {
		System.out.println(x +"   "+y);
		if(x >= 512 || y >= 512) return true;
		if(x < 1 || y < 1) return true;
		return false;
	}
}
