package model;

import java.awt.*;

public class Message {
	private String message;
	private int time;
	private Color c;

	public Message(String message, int time, Color c) {
		this.message = message;
		this.time = time;
		this.c = c;
	}

	public Message(String message, int time) {
		this(message, time, Color.WHITE);
	}

	public Message(String message, Color c) {
		this(message, 100, c);
	}

	public void incTime() {
		time--;
	}

	public int time() {
		return time;
	}

	public String message() {
		return message;
	}

	public Color color() {
		return c;
	}

}
