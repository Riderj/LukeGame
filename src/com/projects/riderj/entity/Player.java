package com.projects.riderj.entity;

import com.projects.riderj.game.InputHandler;
import com.projects.riderj.gfx.Colors;
import com.projects.riderj.gfx.Font;
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

		int color = Colors.get(-1,555,055,222);
		int xTile = 0;
		int yTile = 28;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier /2;
		int yOffset = y - modifier /2 - 4;
		
		screen.render(xOffset , yOffset, (xTile)+(yTile*32), color);
		screen.render(xOffset + modifier, yOffset, (xTile+1) + (yTile * 32), color);
		screen.render(xOffset , yOffset + modifier, (xTile)+((yTile+1)*32), color);
		screen.render(xOffset + modifier, yOffset + modifier, (xTile+1)+((yTile+1)*32), color);
		
		Font.render("Coords:("+x+","+y+")", screen, screen.xOffset, screen.yOffset, Colors.get(-1, -1, -1, 555));
	}

	public boolean hasCollided(int xa, int ya) {
		System.out.println("X: "+x +", Y:"+y+" \t level.height: "+level.height);
		if((x+xa)-4 < 0 || (y+ya)-6 < 0) return true;
		if((x+xa) > (level.width*8)-12 || (y+ya) > (level.height*8)-6) return true;
		System.out.println(x +"   "+y);
		return false;
	}
}
