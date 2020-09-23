package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape implements Moveable {
	
	private Point center;
	private int radius;
	
	public Circle() {}
	public Circle(Point center, int radius)
	{
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(Point center, int radius, String color)
	{
		this(center, radius);
		setColorString(color);
	}
	
	public Circle(Point center, int radius, Color color)
	{
		this(center, radius);
		setColor(color);
	}
	
	public Circle(Point center, int radius, Color contour, Color inside)
	{
		this(center, radius, contour);
		setInsideColor(inside);
	}
	@Override
	public void moveOn(int x, int y) {
		center.moveOn(x, y);
	}
	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
		
	}
	@Override
	public int compareTo(Object o) {
		if(o instanceof Circle)
		{
			return (int) (this.radius-((Circle) o).radius);
		}
		else 
			return 0;
	}
	
	public double area() {
		return radius*radius*Math.PI;
		
	}
	
	public String toString() 
	{
		return "circle->center:" + center.toStringWithoutColor() + ",radius:" + radius + ",contourColor:" + colorToRgb(getColor()) + ",insideColor:" + colorToRgb(getInsideColor());
	}
	
	public double perimeter() {
		return 2*radius*Math.PI;
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInsideColor());
		g.fillOval(center.getX()-radius+1, center.getY()-radius+1, 2*radius-2, 2*radius-2);
	}
	
	@Override
	public void drawShape(Graphics g) {
		g.setColor(getColor());
		g.drawOval(center.getX()-radius, center.getY()-radius, 2*radius, 2*radius);
		fill(g);
		if(isSelected())
			selected(g);
	}
	
	@Override
	public void selected(Graphics g) {
		new Line(new Point(center.getX(), center.getY()-radius), 
				new Point(center.getX(), center.getY() + radius)).selected(g);
		new Line(new Point(center.getX()-radius, center.getY()), 
				new Point(center.getX()+radius, center.getY())).selected(g);
	}
	
	@Override
	public boolean contains(int x, int y) {

		if(new Point(x, y).distance(getCenter()) <= radius)
			return true;
		else
			return false;
	}
	
	public boolean equals(Object obj) 
	{
		if(obj instanceof Circle) 
		{
			Circle helper = (Circle) obj;
			if(center.equals(helper.center) && radius == helper.radius)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	@Override
	public Shape clone() {
		Circle circle = new Circle(new Point(this.getCenter().getX(), this.getCenter().getY()), this.getRadius(), this.getColor(), this.getInsideColor());
		return circle;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
}
