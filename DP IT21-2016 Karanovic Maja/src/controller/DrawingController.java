package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;

import command.CmdAddShape;
import dialogs.DlgForCircle;
import dialogs.DlgForHexagon;
import dialogs.DlgForRectangle;
import dialogs.DlgForSquare;
import geometry.Circle;
import geometry.DrawingModel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import hexagon.Hexagon;
import view.DrawingFrame;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private Point firstPointOfLine;
	
	private CmdAddShape cmdAddShape;
	
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
		cmdAddShape = new CmdAddShape(model, p);
		cmdAddShape.execute();
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
			cmdAddShape = new CmdAddShape(model, l);
			cmdAddShape.execute();
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
			cmdAddShape = new CmdAddShape(model, s);
			cmdAddShape.execute();
			frame.getView().repaint();
		}
	}

	public void addRectangle(MouseEvent arg0) {
		DlgForRectangle dialog = new DlgForRectangle();
		dialog.informationGot(arg0.getX(), arg0.getY(), contourColor, insideColor);
		dialog.setVisible(true);
		if(dialog.getFinished() == 1)
		{
			Rectangle r = new Rectangle(new Point(dialog.getX(), dialog.getY()), dialog.getLength(), dialog.getMyHeight(), dialog.getContour(), dialog.getInside());
			cmdAddShape = new CmdAddShape(model, r);
			cmdAddShape.execute();
			frame.getView().repaint();
		}	
	}
	
	public void addCircle(MouseEvent arg0) {
		DlgForCircle dialog = new DlgForCircle();
		dialog.informationGot(arg0.getX(), arg0.getY(), contourColor, insideColor);
		dialog.setVisible(true);
		if(dialog.getFinished() == 1)
		{
			Circle c = new Circle(new Point(dialog.getX(), dialog.getY()), dialog.getRadius(), dialog.getContour(), dialog.getInside());
			cmdAddShape = new CmdAddShape(model, c);
			cmdAddShape.execute();
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
