package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape implements Moveable {

	private int x;
	private int y;
	
	public Point()
	{
		
	}
	
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, String color)
	{
		this(x, y);
		setColorString(color);
	}
	
	public Point(int x, int y, Color color)
	{
		this(x, y);
		setColor(color);
	}

	@Override
	public void moveOn(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public void moveBy(int byX, int byY) 
	{
		this.x += byX;
		this.y += byY;
	}

	@Override
	public int compareTo(Object o) 
	{
		if(o instanceof Point) {
			Point first = new Point(0, 0);
			return (int) (this.distance(first) - ((Point) o).distance(first));
		}
		else
			return 0;
	}

	@Override
	public void drawShape(Graphics g) {
		g.setColor(getColor());
		g.drawLine(x-2, y, x+2, y);
		g.drawLine(x, y+2, x, y-2);
		
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(x-3, y-3, 6, 6);
		
	}

	@Override
	public boolean contains(int x, int y) {
		if(this.distance(new Point(x, y))<=2)
			return true;
		return false;
	}
	
	public double distance(Point p)
	{
		int dX = x - p.x;
		int dY = p.y - y;
		double d = Math.sqrt(dX*dX + dY*dY);
		return d;
	}
	
	public String toString()
	{
		return "point->(" + x + ", " + y + ", " +  this.getColorString() + ")";
	}
	
	@Override 
	public Shape clone() {
		Point point = new Point(this.getX(), this.getY(), this.getColor());
		return point;
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}
	
	
}
