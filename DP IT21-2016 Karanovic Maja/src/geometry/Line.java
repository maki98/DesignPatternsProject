package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape implements Moveable {

	private Point first;
	private Point last;
	
	public Line() {
		
	}
	
	public Line(Point first, Point last)
	{
		this.first = first;
		this.last = last;
	}
	
	public Line(Point first, Point last, String color)
	{
		this(first, last);
		setColorString(color);
	}
	
	public Line(Point first, Point last, Color color)
	{
		this(first, last);
		setColor(color);
	}

	@Override
	public void moveOn(int x, int y) {
		first.moveOn(x, y);
		last.moveOn(x, y);
	}

	@Override
	public void moveBy(int byX, int byY) {
		first.moveBy(byX, byY);
		last.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Line)
		{
			return (int) (this.length()-((Line) o).length());
		}
		else 
			return 0;
	}

	@Override
	public void drawShape(Graphics g) {
		g.setColor(getColor());
		g.drawLine(first.getX(), first.getY(), last.getX(), last.getY());
		if(isSelected())
			selected(g);
		
	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		first.selected(g);
		last.selected(g);
		middle().selected(g);
	}

	@Override
	public boolean contains(int x, int y) {
		Point helper = new Point(x, y);
		if((first.distance(helper)+last.distance(helper))-length()<=1)
			return true;
		else
			return false;
	}
	
	public String toString() 
	{
		return "line->first:" + first.toStringWithoutColor() + ",last:" + last.toStringWithoutColor() + ",color:" + this.colorToRgb(getColor());
	}
	
	public double length()
	{
		return first.distance(last);
	}
	
	public Point middle()
	{
		int xMid = (first.getX() + last.getX()) / 2;
		int yMid = (first.getY() + last.getY()) / 2;
		Point midPoint = new Point(xMid, yMid);
		return midPoint;
	}
	
	@Override 
	public Shape clone() {
		Line line = new Line(new Point(this.getFirst().getX(), this.getFirst().getY()), new Point(this.getLast().getX(), this.getLast().getY()), this.getColor());
		return line;
	}

	public Point getFirst() {
		return first;
	}

	public void setFirst(Point first) {
		this.first = first;
	}

	public Point getLast() {
		return last;
	}

	public void setLast(Point last) {
		this.last = last;
	}
}
