package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.sound.sampled.ReverbType;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import command.CmdAddShape;
import command.CmdRemoveShape;
import command.CmdSelect;
import dialogs.DlgForCircle;
import dialogs.DlgForRectangle;
import dialogs.DlgForSquare;
import geometry.Circle;
import geometry.DrawingModel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import observer.DrawingObserver;
import view.DrawingFrame;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private Point firstPointOfLine;
	
	private CmdAddShape cmdAddShape;
	private CmdRemoveShape cmdRemoveShape;
	private CmdSelect cmdSelect;
	
	private Color contourColor = Color.BLACK;
	private Color insideColor = Color.WHITE;
	
	private ArrayList<Shape> selected = new ArrayList<Shape>();
		
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
		frame.addToLog(cmdAddShape.toString());
		p.addObserver(new DrawingObserver(model, frame));
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
			frame.addToLog(cmdAddShape.toString());
			firstPointOfLine = null;
			
			l.addObserver(new DrawingObserver(model, frame));

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
			frame.addToLog(cmdAddShape.toString());
			
			s.addObserver(new DrawingObserver(model, frame));

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
			frame.addToLog(cmdAddShape.toString());
			
			r.addObserver(new DrawingObserver(model, frame));

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
			frame.addToLog(cmdAddShape.toString());
			
			c.addObserver(new DrawingObserver(model, frame));

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

	public void selectShapes(MouseEvent arg0) {
		for(int i = model.getAll().size()-1;i>=0;i--)
		{
			if(model.get(i).contains(arg0.getX(), arg0.getY()))
			{
				if(model.get(i).isSelected() == false)
				{
					CmdSelect cmdSelect = new CmdSelect(model.get(i));
					cmdSelect.execute();
					frame.addToLog(cmdSelect.toString());

					//model.get(i).setSelected(true);
					break;
				}
				else if (model.get(i).isSelected() == true)
				{
					CmdSelect cmdSelect = new CmdSelect(model.get(i));
					cmdSelect.unexecute();
					//model.get(i).setSelected(false);
					break;
				}
			}
		}
	}
	
	public void unselectShapes(MouseEvent arg0) {
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext())
		{
			Shape shape = it.next();
			shape.setSelected(false);
		}
	}

	public void removeShapes(MouseEvent arg0) {
		
		if(frame.getBtnDelete().isEnabled())
		{
			if(JOptionPane.showConfirmDialog(null, "Are you sure you want to remove selected shape?", "Warning!", JOptionPane.YES_NO_OPTION) == 0) {
				Iterator<Shape> it = model.getAll().iterator();
				while(it.hasNext()) {
					Shape s = it.next();
					
					if(s.isSelected())
					{
						s.setSelected(false);
						cmdRemoveShape = new CmdRemoveShape(model, s);
						it.remove();
	
						cmdRemoveShape.execute();
						frame.addToLog(cmdRemoveShape.toString());
					}	
				}
				frame.getBtnSelect().setSelected(false);
			}
			frame.getBtnDelete().setSelected(false);

		}
		
	}
	
		
	}




