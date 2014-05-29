package com.projects.riderj.menu;

import com.projects.riderj.gfx.Colors;
import com.projects.riderj.gfx.Screen;

public class TestMenu extends BasicGui{
	private static int guiColor = Colors.get(-1, 500, 200, -1);
	public TestMenu(Screen screen, int x, int y, int w, int h) {
		super(screen, x, y,w,h, "");
	}

	public void tick() {
	}

	public void render(Screen screen) {
		if(h > 2) {
			for(int hy = 1; hy < h-1; hy++) {
				screen.render(screen.xOffset+x,screen.yOffset+y+(8*hy),(4*32)+2, guiColor,0);
				for(int hw=1; hw < w; hw ++) {
					screen.render((screen.xOffset+x)+((hw)*8),screen.yOffset+y+(8*hy),(4*32)+3, guiColor,0);
				}
				screen.render((screen.xOffset+x)+((w-1)*8),screen.yOffset+y+(8*hy),(4*32)+2, guiColor,3);
			}
			screen.render(screen.xOffset+x,screen.yOffset+y+((h-1)*8),4*32, guiColor,2); 
			
			for(int i = 1; i < w-1; i++) {
				screen.render((screen.xOffset+x)+((i)*8),(screen.yOffset+y)+((h-1)*8),(4*32)+1, guiColor,2); 
			}
			
			screen.render(screen.xOffset+x+((w-1)*8),screen.yOffset+y+((h-1)*8),4*32, guiColor,3); 
		}else {
			screen.render(screen.xOffset+x,screen.yOffset+y+8,4*32, guiColor,2); 
			screen.render(screen.xOffset+x+8,screen.yOffset+y+8,4*32, guiColor,3);
		}
		
		
		if(w > 2) {
			screen.render(screen.xOffset+x,screen.yOffset+y,4*32, guiColor,0); 
			
			for(int i = 1; i < w-1;i++) {
				int psudoW = i*8;
				screen.render((screen.xOffset+x)+psudoW,(screen.yOffset+y),(4*32)+1, guiColor,0); 
			}
			
			screen.render((screen.xOffset+x)+((w-1)*8),screen.yOffset+y,4*32, guiColor,1); //w-1 because it starts from 0
		}else {
			screen.render(screen.xOffset+x,screen.yOffset+y,4*32, guiColor); 
			screen.render(screen.xOffset+x+8,screen.yOffset+y,4*32, guiColor,1); 
		}
		
		//screen.render(screen.xOffset+x,screen.yOffset+y,4*32, guiColor);
	}

}
