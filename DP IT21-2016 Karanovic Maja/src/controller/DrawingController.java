package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import geometry.DrawingModel;
import geometry.Point;
import view.DrawingFrame;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	
	public DrawingController(DrawingModel model, DrawingFrame frame)
	{
		this.model = model;
		this.frame = frame;
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
		Point p = new Point(arg0.getX(), arg0.getY(), Color.RED);
		model.add(p);
		frame.getView().repaint();
		
	}
}
