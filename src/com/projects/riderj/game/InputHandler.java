package com.projects.riderj.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	
	public InputHandler(Game game){
		game.addKeyListener(this);
	}
	
	public class Key{
		int numTimesPressed = 0;
		boolean pressed = false;
		
		public int getNumTimesPressed(){
			return numTimesPressed;
		}
		
		public void togglePressed(boolean isPressed){
			pressed = isPressed;
			if(pressed) numTimesPressed++;
		}
		public boolean isPressed(){
			return pressed;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}


	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}


	public void keyTyped(KeyEvent e) {
		
	}
	
	
	public void toggleKey(int keyCode, boolean isPressed){
		if(keyCode == KeyEvent.VK_W) up.togglePressed(isPressed);
		if(keyCode == KeyEvent.VK_S) down.togglePressed(isPressed);
		if(keyCode == KeyEvent.VK_A) left.togglePressed(isPressed);
		if(keyCode == KeyEvent.VK_D) right.togglePressed(isPressed);
	}

}
