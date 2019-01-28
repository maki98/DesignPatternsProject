package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import geometry.DrawingModel;
import geometry.Line;
import geometry.Point;
import view.DrawingFrame;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private Point firstPointOfLine;
	
	public DrawingController(DrawingModel model, DrawingFrame frame)
	{
		this.model = model;
		this.frame = frame;
	}
	
	
	public void addPoint(MouseEvent arg0)
	{
		Point p = new Point(arg0.getX(), arg0.getY(), Color.RED);
		model.add(p);
		frame.getView().repaint();
	}
	
	public void addLine(MouseEvent arg0)
	{
		if(firstPointOfLine == null)
		{
			firstPointOfLine = new Point(arg0.getX(), arg0.getY());
		}
		else
		{
			Point secondPointOfLine = new Point(arg0.getX(), arg0.getY());
			Line l = new Line(firstPointOfLine, secondPointOfLine, Color.RED);
			model.add(l);
			frame.getView().repaint();
			firstPointOfLine = null;
		}
	}
	
	public void addSquare(MouseEvent arg0)
	{
		
	}
}
