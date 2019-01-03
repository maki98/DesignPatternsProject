package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Square {

	private int height;
	
	public Rectangle() {}
	
	public Rectangle(Point upperLeft, int length, int height)
	{
		super.upperLeft = upperLeft;
		this.pageLength = length;
		this.height = height;
	}
	
	public Rectangle(Point upperLeft, int length, int height, String color) 
	{
		this(upperLeft, length, height);
		setColorString(color);
	}
	
	public Rectangle(Point upperLeft, int length, int height, Color contour, Color inside)
	{
		this(upperLeft, length, height);
		setColor(contour);
		setInsideColor(inside);
	}
	
	public double area()
	{
		return pageLength * height;
	}
	
	public double perimeter()
	{
		return pageLength * 2 + height * 2;
	}
	
	public String toString()
	{
		return "Upper left point = " + upperLeft + ", length = " + pageLength + ", height= " + height;		
	}
	
	public boolean equals(Object obj) 
	{
		if(obj instanceof Rectangle) 
		{
			Rectangle helper = (Rectangle) obj;
			if(upperLeft.equals(helper.upperLeft) && 
					pageLength == helper.pageLength && 
							height == helper.height)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	

	public Line diagonal() 
	{
		Point lowerRight = new Point(upperLeft.getX()+pageLength, upperLeft.getY()+height);
		return new Line(upperLeft, lowerRight);
	}
	
	public void drawShape(Graphics g) 
	{
		g.setColor(getColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), pageLength, height);
		fill(g);
		if (isSelected())
			selected(g);
	}

	public void selected(Graphics g) 
	{
		g.setColor(Color.BLUE);
		//OVO ISPRAVITI TREBA
		new Line(getUpperLeft(), new Point(getUpperLeft().getX()+pageLength, getUpperLeft().getY())).selected(g);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY()+height)).selected(g);
		new Line(new Point(getUpperLeft().getX()+pageLength, getUpperLeft().getY()), diagonal().getLast()).selected(g);
		new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY()+height), diagonal().getLast()).selected(g);
	}
	
	public void fill(Graphics g) 
	{
		g.setColor(getInsideColor());
		g.fillRect(upperLeft.getX()+1, upperLeft.getY()+1, pageLength-1, height-1);
	}
	

	public boolean contains(int x, int y) 
	{
		if(this.getUpperLeft().getX()<=x 
				&& x<=(this.getUpperLeft().getX() + height)
				&& this.getUpperLeft().getY()<=y 
				&& y<=(this.getUpperLeft().getY() + pageLength))
			return true;
		else 
			return false;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}

}
