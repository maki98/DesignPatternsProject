package geometry;

import java.awt.Color;
import java.awt.Graphics;

public abstract class SurfaceShape extends Shape {

	private String insideColorString;
	private Color insideColor;
	
	public abstract double area();
	public abstract double perimeter();
	public abstract void fill(Graphics g);
	
	//radi sa stringovima
	public String getInsideColorString() {
		return insideColorString;
	}
	public void setInsideColorString(String insideColorString) {
		this.insideColorString = insideColorString;
	}
	
	//radi sa color
	public Color getInsideColor() {
		return insideColor;
	}
	public void setInsideColor(Color insideColor) {
		this.insideColor = insideColor;
	}
	
	
}
