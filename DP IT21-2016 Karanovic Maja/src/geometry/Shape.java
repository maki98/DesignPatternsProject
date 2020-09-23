package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

public abstract class Shape extends Observable implements Moveable, Comparable<Object> {
	
	private Color color;
	private String colorString;
	private boolean selected;
	
	public Shape() 
	{
		
	}
	
	public Shape(Color color)
	{
		this.color = color;
	}
	
	public static Color findColor(String color)
	{
		if(color.equalsIgnoreCase("White"))
			return Color.WHITE;
		else if(color.equalsIgnoreCase("Blue"))
			return Color.BLUE;
		else if(color.equalsIgnoreCase("Green"))
			return Color.GREEN;
		else if(color.equalsIgnoreCase("Red"))
			return Color.RED;
		else if(color.equalsIgnoreCase("Yellow"))
			return Color.YELLOW;
		else if(color.equalsIgnoreCase("Pink"))
			return Color.PINK;
		else
			return Color.BLACK;
	}
	
	public static String findColorString(Color color)
	{
		if(color == Color.white)
			return "White";
		else if (color == Color.BLUE)
			return "Blue";
		else if(color == Color.GREEN)
			return "Green";
		else if(color == Color.RED)
			return "Red";
		else if(color == Color.YELLOW)
			return "Yellow";
		else if(color == Color.PINK)
			return "Pink";
		else
			return "Black";
	}
	
	public String colorToRgb(Color color) {
		
		return "[" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "]";		
	}
	
	public abstract void drawShape(Graphics g);
	public abstract void selected(Graphics g);
	public abstract boolean contains(int x, int y);

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		setChanged();
		notifyObservers();
	}
}
