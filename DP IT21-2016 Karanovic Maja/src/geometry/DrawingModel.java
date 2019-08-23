package geometry;

import java.util.ArrayList;

import hexagon.Hexagon;

public class DrawingModel {
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public ArrayList<Shape> getAll()
	{
		return shapes;
	}
	
	public Shape get(int i)
	{
		return shapes.get(i);
	}
	
	public void add(Shape s)
	{
		shapes.add(s);
	}
	
	public void remove(Shape s)
	{
		shapes.remove(s);
	}

}
