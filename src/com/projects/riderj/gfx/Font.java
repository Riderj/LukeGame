package com.projects.riderj.gfx;

public class Font {
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      "+
								  "0123456789.,!?'\"-+=/\\%()<>:;$      ";
	
	public static void render(String message, Screen screen, int x, int y, int color){
		message = message.toUpperCase();
		
		for(int i= 0; i < message.length(); i ++){
			int index = chars.indexOf(message.charAt(i));
			
			if(index >= 0) screen.render(x + i*8, y,index+30*32, color); //30*32 will give us the 960th tile out of 1024(exclusive) this will result in A, we add the index to get the appropriate letter.
		}
	}
}
