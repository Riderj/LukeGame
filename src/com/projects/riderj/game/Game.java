package com.projects.riderj.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.projects.riderj.entity.Player;
import com.projects.riderj.gfx.Screen;
import com.projects.riderj.gfx.SpriteSheet;
import com.projects.riderj.level.Level;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "Test";
	public static final int WIDTH = 160;
	public static final int HEIGHT = (WIDTH/12)*9; //12:9 aspect ratio
	public static final int SCALE = 3;
	public static JFrame frame;
	public static boolean running = true;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public int[]colors = new int[6*6*6];
	private int frames = 0;
	public Player player;
	
	
	private Screen screen;
	
	public InputHandler input;
	
	public Level level;
	
	public Game(){
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void init(){
		int index = 0;
		
		/*
		 * This loop for the RGB values is the second most important, it will populate the colors array to actually give us color ints, this will then represent an RGB value such as (51,153,51) or Colors.get(-1, 131, 141, -1) 51 comes from 255/5   there are 5 colors we can choose from in the colors.get
		 */
		for(int r=0; r<6; r++){
			for(int g=0; g<6; g++){
				for(int b=0; b<6; b++){
					int rr = (r*255/5); //We take the color values 0-5 and multiply them by 51 (255/5). We do 255/5 because we can only have 5 colors 0-5.
					int gg = (g*255/5); //This method will not give us complete freedom of colors, it will only generate 5 different shades of red,green,blue. These are 51,102,153,204,and 255.
					int bb = (b*255/5);
					
					//if((rr<<16 | gg << 8 | bb) == 3381555) System.out.println(rr+" "+gg+" "+bb); // This is an example of getting the RGB value from the color int
					
					colors[index++] = rr<<16 | gg << 8 | bb; //We shift by 8 bits each time, bb = 8, gg = 16, rr = 24, aa = 32 (Java always stores colors as a 32 bits AARRGGBB)
				}
			}
		}
		screen = new Screen(WIDTH,HEIGHT,new SpriteSheet("/sprites1.png"));
		input = new InputHandler(this);
		player = new Player(level,5,5,input);
		level = new Level(64,64);
		
		level.addEntity(player);
	}
	private synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	private synchronized void stop() {
		running = false;
	}
	
	public void run(){
		long lastNanoTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double unprocessedNS = 0;
		
		init();
		
		while(running){
			long now = System.nanoTime();
			unprocessedNS += (now-lastNanoTime)/nsPerTick;
			lastNanoTime = now;
			

			boolean shouldRender = true;
			
			while(unprocessedNS >= 1){
				ticks++;
				tick();
				unprocessedNS-=1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender){
				frames++;
				render();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
				lastTimer += 1000;
				
				System.out.println("Tick: "+ticks+", Frames: "+frames);
				
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	private int x = 0, y = 0;
	public void tick() {
		tickCount++;

		//System.out.println(pixels.length); pixel length is Width*Height

		
		level.tick();
	}
	
	public int getFrames(){
		return frames;
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		String msg = "This is our game!";
		//Font.render(msg, screen,screen.xOffset + screen.width/2 - (msg.length()*8)/2,screen.yOffset + screen.height/2 - 4, Colors.get(000, 000, 000, 500));
		
		int xOffset = player.x - (screen.width/2);
		int yOffset = player.y - (screen.height/2);
		
		level.renderTiles(screen, xOffset, yOffset);
		
		level.renderEntities(screen);
		
		//Font.render("Adam likes milfs", screen, screen.xOffset, screen.yOffset, Colors.get(-1,-1,-1,005));
		
		/*for(int y = 0; y < 32; y ++){
			for(int x = 0; x < 32; x ++){
				screen.render(x<<3, y<<3, 0, Colors.get(111, 222, 333, 444));
			}
		}
		*/
		
		
		/*
		 * This block of code is the most important, it renders the color data on the screen. Without this the pixels[] array will not be populated, giving us a black screen.
		 */
		for(int y = 0; y < screen.height; y++){
			for(int x = 0; x < screen.width;  x++){
				int colorCode = screen.pixels[x+(y*screen.width)]; //This will get the current pixel and return its colorCode (The value that was calculated by the screen class's render method); We get the current pixel by adding the x to the y*WIDTH (We do y*WIDTH because every 1 pixel down is WIDTH across).
				if(colorCode < 255) pixels[x+(y*screen.width)] = colors[colorCode]; //Using the colorCode value we set the current pixel to the color int generated by the RGB loop above. We can take this color int and calculate the RGB by doing the opposite of what the loop does above.
				
				//System.out.println(colors[55]);
			}
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.ORANGE);
		g.drawRect(0,0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args){
		Game game = new Game();
		game.start();
	}

}
