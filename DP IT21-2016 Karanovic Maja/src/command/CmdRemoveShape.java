package command;

import java.util.ArrayList;
import java.util.Iterator;

import adapter.HexagonAdapter;
import geometry.Circle;
import geometry.DrawingModel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;

public class CmdRemoveShape implements Command {

	private DrawingModel model;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public CmdRemoveShape(DrawingModel model, ArrayList<Shape> shapes)
	{
		this.model = model;
		this.shapes = shapes;
	}
	
	
	//proci kroz listu svih iz modela, naci na kom je indeksu element, pa po indeksu brisati
	@Override
	public void execute() {
		
		for (Shape s : shapes) {
			for(int i = 0; i < model.getAll().size(); i++)
			{
				if(s instanceof Point && model.get(i) instanceof Point)
				{
					if(((Point) model.get(i)).compareTo((Point) s) == 0) 
					{
						System.out.println("here2");
						model.remove(model.get(i));
					}
				}
				else if(s instanceof Line && model.get(i) instanceof Line)
				{
					if(((Line) model.get(i)).compareTo((Line) s) == 0) 
					{
						model.remove(model.get(i));
					}
				}
				else if(s instanceof Square && model.get(i) instanceof Square)
				{
					if(((Square) model.get(i)).compareTo((Square) s) == 0) 
					{
						model.remove(model.get(i));
					}
				}
				else if(s instanceof Rectangle && model.get(i) instanceof Rectangle)
				{
					if(((Rectangle) model.get(i)).compareTo((Rectangle) s) == 0) 
					{
						model.remove(model.get(i));
					}
				}
				else if(s instanceof Circle && model.get(i) instanceof Circle)
				{
					if(((Circle) model.get(i)).compareTo((Circle) s) == 0) 
					{
						model.remove(model.get(i));
					}
				}
				else if(s instanceof HexagonAdapter && model.get(i) instanceof HexagonAdapter)
				{
					if(((HexagonAdapter) model.get(i)).equals((HexagonAdapter) s)) 
					{
						model.remove(model.get(i));
					}
				}
			}
			
			System.out.println(s.toString());
		}
		
		System.out.println("AFTER");
		for(int i = 0; i < shapes.size(); i++) {
			System.out.println(i + ": " + shapes.get(i).toString());
		}
	}

	@Override
	public void unexecute() {
		for (Shape s : shapes) {
			model.add(s);
		}
	}
	
	@Override 
	public String toString() {
		
		if (shapes.size() == 1) {
			return "remove:" + shapes.get(0).toString();
			
		} 
		else {
			String text = "remove:";

			for (Shape s : shapes) {
				if (shapes.indexOf(s) == shapes.size() - 1) {
					text = text + s.toString();
				} else {
					text = text + s.toString() + ":";
				}
			}
			return text;
		}
	}
	
}
