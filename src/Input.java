/*
 * Copyright (c) 2014, Benny Bobaganoosh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Stores the current state of any user input devices, and updates them with new
 * input events.
 * 
 * @author Benny Bobaganoosh (thebennybox@gmail.com)
 */
public class Input implements KeyListener, FocusListener,
		MouseListener, MouseMotionListener {
	private boolean[] keys = new boolean[65536];
	private boolean[] mouseButtons = new boolean[4];
	private int mouseX = 0;
	private int mouseY = 0;

	/** Updates state when the mouse is dragged */
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/** Updates state when the mouse is moved */
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/** Updates state when the mouse is clicked */
	public void mouseClicked(MouseEvent e) {
	}

	/** Updates state when the mouse enters the screen */
	public void mouseEntered(MouseEvent e) {
	}

	/** Updates state when the mouse exits the screen */
	public void mouseExited(MouseEvent e) {
	}

	/** Updates state when a mouse button is pressed */
	public void mousePressed(MouseEvent e) {
		int code = e.getButton();
		if (code > 0 && code < mouseButtons.length)
			mouseButtons[code] = true;
	}

	/** Updates state when a mouse button is released */
	public void mouseReleased(MouseEvent e) {
		int code = e.getButton();
		if (code > 0 && code < mouseButtons.length)
			mouseButtons[code] = false;
	}

	/** Updates state when the window gains focus */
	public void focusGained(FocusEvent e) {
	}

	/** Updates state when the window loses focus */
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
		for (int i = 0; i < mouseButtons.length; i++)
			mouseButtons[i] = false;
	}

	/** Updates state when a key is pressed */
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length)
			keys[code] = true;
	}

	/** Updates state when a key is released */
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length)
			keys[code] = false;
	}

	/** Updates state when a key is typed */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Gets whether or not a particular key is currently pressed.
	 * 
	 * @param key The key to test
	 * @return Whether or not key is currently pressed.
	 */
	public boolean GetKey(int key) {
		return keys[key];
	}

	/**
	 * Gets whether or not a particular mouse button is currently pressed.
	 * 
	 * @param button The button to test
	 * @return Whether or not the button is currently pressed.
	 */
	public boolean GetMouse(int button) {
		return mouseButtons[button];
	}

	/**
	 * Gets the location of the mouse cursor on x, in pixels.
	 * @return The location of the mouse cursor on x, in pixels
	 */
	public int GetMouseX() {
		return mouseX;
	}

	/**
	 * Gets the location of the mouse cursor on y, in pixels.
	 * @return The location of the mouse cursor on y, in pixels
	 */
	public int GetMouseY() {
		return mouseY;
	}
}
