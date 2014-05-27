package com.projects.riderj.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public String path;
	public int width;
	public int height;
	public int[] pixels;
	
	public SpriteSheet(String path){
		this.path = path;
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(image == null){
			return;
		}
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		for(int i=0; i<pixels.length; i++){
			pixels[i] = (pixels[i]&0xff / 64); //255/4 rounded up
		}
		
		for(int i=0; i < 8; i ++){
			//System.out.println(pixels[i]);
		}
	}
}
