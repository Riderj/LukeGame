package com.projects.riderj.gfx;

public class Screen {
	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH-1;
	
	public int[] pixels;
	
	public int xOffset;
	public int yOffset;
	public int width;
	public int height;
	
	public static final byte BIT_MIRROR_X= 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	int thing = 0;
	public SpriteSheet sheet;
	
	public Screen(int width, int height, SpriteSheet sheet){
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		
		pixels = new int[width*height];
	}

	
	public void render(int xPos, int yPos, int tile, int color, int mirrorDir){
		xPos -= xOffset; //0
		yPos -= yOffset; // 0
		int xTile = tile % 32;// The x tile will be any value from 0-31, when it equals 32%32 it will start rendering from the 2nd row y will equal 1.
		int yTile = tile / 32; // We divide by 32 to determine the vertical row, since anything less then 32/32 is less than 1, it will only count row 0. Once it divides by 32 it will equal 1 which is the next row, this will happen for every 32 increment.
		int tileOffset = ((xTile <<3) + ((yTile <<3) * sheet.width)); //REMEMBER: (THIS IS THE SPRITESHEET TILE OFFSET); We shift it over 3 because 2 to the 3rd power is 8, which is the width of one sprite block. We add ((yTile <<3) * sheet.width) because the next vertical row will be at x+(y*WIDTH) since each vertical row will be the width of the screen + x; X being a value from 0 - screen.width; The next y Pixel will start at 256 there is a total of 2048 pixels in one 32 bit row of the spritesheet. 65536 pixels in the whole spritesheet
		//System.out.println("Tile: "+tile+",  Tile Offset: "+tileOffset);
		
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		
		for (int y = 0; y < 8; y ++){
			if(y+yPos < 0 || y + yPos >= height) continue; //As long as it's in bounds continue with the loop, if it's not (less than 0 or greater than height) continue with the loop, but do not execute anything after the statement that continue is in until conditions are valid once again.
			int ySheet = y;
			if(mirrorY) ySheet = 7-y;
			for (int x = 0; x < 8; x ++){
				if(x+xPos < 0 || x + xPos >= width) continue;
				thing++;
				int xSheet = x;
				if(mirrorX) xSheet = 7-x;
				int col = (color >> (sheet.pixels[xSheet + (ySheet * sheet.width) + tileOffset]*8)&255); //REMEMBER: (THIS IS THE SPRITESHEET); We get the color by figuring out which color it is part of 0,1,2,3 (Colors.get(0,1,2,3)). Then we bitshift it by the respective numbers 24,16,8,0, with the number we recieve we then need to bitshift right (divide by 2^(24,16,8)) to set the colorCode for the rendering in the game class.
				if(col < 255) pixels[(x + xPos)+(y + yPos)*width] = col;
				
				//System.out.println((xSheet + (ySheet * sheet.width) + tileOffset)+":"+color+" : "+(color >>(sheet.pixels[xSheet + (ySheet * sheet.width) + tileOffset]*8))+" "+(color >> (sheet.pixels[xSheet + (ySheet * sheet.width) + tileOffset]*8)&255) + " " + ((sheet.pixels[xSheet + (ySheet * sheet.width) + tileOffset]*8)&255));

			}
		}
	}
	public void render(int xPos, int yPos, int tile, int color){
		render(xPos,yPos,tile,color,0);
	}


	public void setOffset(int xOffset2, int yOffset2) {
		xOffset = xOffset2;
		yOffset = yOffset2;
	}
}
