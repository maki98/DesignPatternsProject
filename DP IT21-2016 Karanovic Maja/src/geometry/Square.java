package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends SurfaceShape {
	
	protected Point upperLeft;
	protected int pageLength;
	protected String contourColor;
	protected String insideColor;
	protected String color;
	
	public Square() {}
	
	public Square(Point upperLeft, int pageLength)
	{
		this.upperLeft = upperLeft;
		this.pageLength = pageLength;
	}
	
	public Square(Point upperLeft, int pageLength, Color color)
	{
		this(upperLeft, pageLength);
		setColor(color);
	}
	
	public Square(Point upperLeft, int pageLength, Color contourColor, Color insideColor)
	{
		this(upperLeft, pageLength, contourColor);
		setInsideColor(insideColor);
	}

	@Override
	public void moveOn(int x, int y) {
		upperLeft.moveOn(x, y);
		
	}

	@Override
	public void moveBy(int byX, int byY) {
		upperLeft.moveBy(byX, byY);
		
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Square)
		{
			return (int) (this.area()-((Square) o).area());
		}
		else 
			return 0;
	}

	@Override
	public double area() {
		return pageLength * pageLength;
	}

	@Override
	public double perimeter() {
		return pageLength * 4;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInsideColor());
		g.fillRect(upperLeft.getX()+1, upperLeft.getY()+1, pageLength-1, pageLength-1);
			
	}

	@Override
	public void drawShape(Graphics g) {
		g.setColor(getColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), pageLength, pageLength);
		fill(g);
		if (isSelected())
			selected(g);
		
	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX()+pageLength, getUpperLeft().getY())).selected(g);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY()+pageLength)).selected(g);
		new Line(new Point(getUpperLeft().getX()+pageLength, getUpperLeft().getY()), diagonal().getLast()).selected(g);
		new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY()+pageLength), diagonal().getLast()).selected(g);
	
	}

	@Override
	public boolean contains(int x, int y) {
		if(this.getUpperLeft().getX()<=x 
				&& x<=(this.getUpperLeft().getX() + pageLength)
				&& this.getUpperLeft().getY()<=y 
				&& y<=(this.getUpperLeft().getY() + pageLength))
			return true;
		else 
			return false;
	}
	
	public String toString() 
	{
		return "square->upperLeft:" + upperLeft + ",length:" + pageLength + ",contourColor:" + findColorString(getColor()) + ",insideColor:" + findColorString(getInsideColor());
	}
	
	public Line diagonal()
	{
		Point lowerRight = new Point(upperLeft.getX()+pageLength, upperLeft.getY()+pageLength);
		return new Line(upperLeft, lowerRight);
	}
	
	public boolean equals(Object obj) 
	{
		if(obj instanceof Square) 
		{
			Square helper = (Square) obj;
			if(upperLeft.equals(helper.upperLeft) && pageLength == helper.pageLength)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public Point getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(Point upperLeft) {
		this.upperLeft = upperLeft;
	}

	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}

}
