package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import dialogs.DlgForSquare;
import geometry.DrawingModel;
import geometry.Line;
import geometry.Point;
import geometry.Square;
import view.DrawingFrame;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private Point firstPointOfLine;
	
	private Color contourColor = Color.BLACK;
	private Color insideColor = Color.WHITE;
	
	public DrawingController(DrawingModel model, DrawingFrame frame)
	{
		this.model = model;
		this.frame = frame;
	}
	
	public void addPoint(MouseEvent arg0)
	{
		Point p = new Point(arg0.getX(), arg0.getY(), contourColor);
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
			Line l = new Line(firstPointOfLine, secondPointOfLine, contourColor);
			model.add(l);
			frame.getView().repaint();
			firstPointOfLine = null;
		}
	}
	
	public void addSquare(MouseEvent arg0) {
		DlgForSquare dialog = new DlgForSquare();
		dialog.informationGot(arg0.getX(), arg0.getY(), contourColor, insideColor);
		dialog.setVisible(true);
		if(dialog.getFinished() == 1)
		{
			Square s = new Square(new Point(dialog.getX(), dialog.getY()), dialog.getLength(), dialog.getContour(), dialog.getInside());
			System.out.println(s.toString());
			model.add(s);
			frame.getView().repaint();
		}
	}
	
	public void chooseContourColor()
	{
		Color temp = JColorChooser.showDialog(null, "Choose contour color:", contourColor);
		if(temp != null)
		{
			contourColor = temp;
			frame.getBtnContourColor().setBackground(contourColor);
		}
	}
	
	public void chooseInsideColor()
	{
		Color temp = JColorChooser.showDialog(null, "Choose inside color:", insideColor);
		if(temp != null)
		{
			insideColor = temp;
			frame.getBtnInsideColor().setBackground(insideColor);
		}
	}


}
