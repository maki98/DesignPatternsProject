package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import javax.sound.sampled.ReverbType;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import adapter.HexagonAdapter;
import command.CmdAddShape;
import command.CmdRemoveShape;
import command.CmdSelect;
import command.Command;
import command.movingz.CmdBringToBack;
import command.movingz.CmdBringToFront;
import command.movingz.CmdToBack;
import command.movingz.CmdToFront;
import command.update.CmdUpdateCircle;
import command.update.CmdUpdateHexagon;
import command.update.CmdUpdateLine;
import command.update.CmdUpdatePoint;
import command.update.CmdUpdateRectangle;
import command.update.CmdUpdateSquare;
import dialogs.DlgForCircle;
import dialogs.DlgForHexagon;
import dialogs.DlgForLine;
import dialogs.DlgForPoint;
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
import observer.DrawingObserver;
import view.DrawingFrame;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private Point firstPointOfLine;
	
	private CmdAddShape cmdAddShape;
	private CmdRemoveShape cmdRemoveShape;
	private CmdSelect cmdSelect;
	private CmdToBack cmdToBack;
	private CmdToFront cmdToFront;
	private CmdBringToBack cmdBringToBack;
	private CmdBringToFront cmdBringToFront;
	private CmdUpdateCircle cmdUpdateCircle;
	private CmdUpdateSquare cmdUpdateSquare;
	private CmdUpdateRectangle cmdUpdateRectangle;
	private CmdUpdateHexagon cmdUpdateHexagon;
	private CmdUpdatePoint cmdUpdatePoint;
	private CmdUpdateLine cmdUpdateLine;
	
	private Color contourColor = Color.BLACK;
	private Color insideColor = Color.WHITE;
	
	private ArrayList<Shape> selected = new ArrayList<Shape>();
	
	private Stack<Command> redoStack = new Stack<Command>();
	private Stack<Command> undoStack = new Stack<Command>();

	private ArrayList<Command> listOfCommands = new ArrayList<Command>();
		
	public DrawingController(DrawingModel model, DrawingFrame frame)
	{
		this.model = model;
		this.frame = frame;
	}
	
	public void addPoint(MouseEvent arg0)
	{
		
		Point p = new Point(arg0.getX(), arg0.getY(), contourColor);
		cmdAddShape = new CmdAddShape(model, p);
		executeCmd(cmdAddShape);
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
			executeCmd(cmdAddShape);
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
			executeCmd(cmdAddShape);
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
			executeCmd(cmdAddShape);
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
			executeCmd(cmdAddShape);
			frame.getView().repaint();
			frame.addToLog(cmdAddShape.toString());
			
			c.addObserver(new DrawingObserver(model, frame));

		}
	}
	
	
	public void addHexagon(MouseEvent arg0)
	{
		DlgForHexagon dialog = new DlgForHexagon();
		dialog.informationGot(arg0.getX(), arg0.getY(), contourColor, insideColor);
		dialog.setVisible(true);
		if(dialog.getFinished() == 1)
		{
			Hexagon h = new Hexagon(dialog.getX(), dialog.getY(), dialog.getRadius());
			h.setBorderColor(contourColor);
			h.setAreaColor(insideColor);
			HexagonAdapter ha = new HexagonAdapter(h);
			ha.setColor(h.getBorderColor());
			ha.setInsideColor(h.getAreaColor());
			cmdAddShape = new CmdAddShape(model, ha);
			executeCmd(cmdAddShape);
			frame.getView().repaint();
			frame.addToLog(cmdAddShape.toString());
						
			ha.addObserver(new DrawingObserver(model, frame));
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
					executeCmd(cmdSelect);
					frame.addToLog(cmdSelect.toString());

					break;
				}
				/*else if (model.get(i).isSelected() == true)
				{
					CmdSelect cmdSelect = new CmdSelect(model.get(i));
					cmdSelect.unexecute();
					break;
				}*/
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
	
						executeCmd(cmdRemoveShape);
						frame.addToLog(cmdRemoveShape.toString());
					}	
				}
				frame.getBtnSelect().setSelected(false);
			}

		}
		frame.getBtnDelete().setSelected(false);
		
	}
	
	public void toBack() {
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext()) {
			Shape s = it.next();
					
			if(s.isSelected())
			{
				s.setSelected(false);
				cmdToBack = new CmdToBack(model, s);
	
				try {
					executeCmd(cmdToBack);
					frame.addToLog(cmdToBack.toString());
				}				//ako nema sa cim da se zameni
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Element is already on the back");	
					s.setSelected(false);
				}
			}	
		}
		frame.getBtnSelect().setSelected(false);
	}
	

	public void toFront() {
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext()) {
			Shape s = it.next();
					
			if(s.isSelected())
			{
				s.setSelected(false);
				cmdToFront = new CmdToFront(model, s);
	
				try {
					executeCmd(cmdToFront);
					frame.addToLog(cmdToFront.toString());
				}				//ako nema sa cim da se zameni
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Element is already on the front");		
					s.setSelected(false);
				}
			}	
		}
		frame.getBtnSelect().setSelected(false);
	}

	public void bringToBack() {
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext()) {
			Shape s = it.next();
					
			if(s.isSelected())
			{
				s.setSelected(false);
				cmdBringToBack = new CmdBringToBack(model, s);
					
				try {
					executeCmd(cmdBringToBack);
					frame.addToLog(cmdBringToBack.toString());
				} 				//ako nema sa cim da se zameni
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Element is already on the back");		
					s.setSelected(false);
				}
				
				break;
			}	
		}
		frame.getBtnSelect().setSelected(false);
	}

	public void bringToFront() {
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext()) {
			Shape s = it.next();
					
			if(s.isSelected())
			{
				s.setSelected(false);
				cmdBringToFront = new CmdBringToFront(model, s);
					
				try {
					executeCmd(cmdBringToFront);
					frame.addToLog(cmdBringToFront.toString());
				} 				//ako nema sa cim da se zameni
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Element is already on the front");		
					s.setSelected(false);
				}
				
				break;
			}	
		}
		frame.getBtnSelect().setSelected(false);
	}

	public void updateShape(MouseEvent e) {
		
		Iterator<Shape> it = model.getAll().iterator();
		while(it.hasNext()) {
			Shape s = it.next();
					
			if(s.isSelected())
			{
				s.setSelected(false);

				if(s instanceof Circle) {
					DlgForCircle dialog = new DlgForCircle();
					Circle old = (Circle) s;
					Circle newc = (Circle) s;
					dialog.update(newc.getCenter().getX(), newc.getCenter().getY(), newc.getRadius(), newc.getColor(), newc.getInsideColor());
					dialog.setVisible(true);
					if(dialog.getFinished() == 1) {
						newc.getCenter().setX(dialog.getX());
						newc.getCenter().setY(dialog.getY());
						newc.setRadius(dialog.getRadius());
						newc.setColor(dialog.getContour());
						newc.setInsideColor(dialog.getInside());
						
						cmdUpdateCircle = new CmdUpdateCircle(old, newc);
						executeCmd(cmdUpdateCircle);
						frame.addToLog(cmdUpdateCircle.toString());
					}
				}
				else if(s instanceof Rectangle) {
					DlgForRectangle dialog = new DlgForRectangle();
					Rectangle old = (Rectangle) s;
					Rectangle newc = (Rectangle) s;
					dialog.update(newc.getUpperLeft().getX(), newc.getUpperLeft().getY(), newc.getPageLength(), newc.getHeight(), newc.getColor(), newc.getInsideColor());
					dialog.setVisible(true);
					if(dialog.getFinished() == 1) {
						newc.getUpperLeft().setX(dialog.getX());
						newc.getUpperLeft().setY(dialog.getY());
						newc.setPageLength(dialog.getLength());
						newc.setHeight(dialog.getMyHeight());
						newc.setColor(dialog.getContour());
						newc.setInsideColor(dialog.getInside());
						
						cmdUpdateRectangle = new CmdUpdateRectangle(old, newc);
						executeCmd(cmdUpdateRectangle);
						frame.addToLog(cmdUpdateRectangle.toString());
					}
				}
				else if(s instanceof Square) {
					DlgForSquare dialog = new DlgForSquare();
					Square old = (Square) s;
					Square newc = (Square) s;
					dialog.update(newc.getUpperLeft().getX(), newc.getUpperLeft().getY(), newc.getPageLength(), newc.getColor(), newc.getInsideColor());
					dialog.setVisible(true);
					if(dialog.getFinished() == 1) {
						newc.getUpperLeft().setX(dialog.getX());
						newc.getUpperLeft().setY(dialog.getY());
						newc.setPageLength(dialog.getLength());
						newc.setColor(dialog.getContour());
						newc.setInsideColor(dialog.getInside());
						
						cmdUpdateSquare = new CmdUpdateSquare(old, newc);
						executeCmd(cmdUpdateSquare);
						frame.addToLog(cmdUpdateSquare.toString());
					}
				}
				else if(s instanceof HexagonAdapter) {
					DlgForHexagon dialog = new DlgForHexagon();
					HexagonAdapter old = (HexagonAdapter) s;
					dialog.update(old.getX(), old.getY(), old.getR(), old.getColor(), old.getInsideColor());
					dialog.setVisible(true);
					if(dialog.getFinished() == 1) {
						
						Hexagon newh = new Hexagon(dialog.getX(), dialog.getY(), dialog.getRadius());
						newh.setBorderColor(dialog.getContour());
						newh.setAreaColor(dialog.getInside());
						
						HexagonAdapter newc = new HexagonAdapter(newh);
						newc.setColor(dialog.getContour());
						newc.setInsideColor(dialog.getInside());
												
						cmdUpdateHexagon = new CmdUpdateHexagon(old, newc);
						executeCmd(cmdUpdateHexagon);
						frame.addToLog(cmdUpdateHexagon.toString());
					}
				}
				else if(s instanceof Point) {
					DlgForPoint dialog = new DlgForPoint();
					Point old = (Point) ((Point) s).clone();
					Point newc = (Point) s;
					dialog.update(newc.getX(), newc.getY(), newc.getColor());
					dialog.setVisible(true);
					if(dialog.getFinished() == 1) {
						newc.setX(dialog.getX());
						newc.setY(dialog.getY());
						newc.setColor(dialog.getContour());
						System.out.println("old: " + old.toString());
						System.out.println("new: " + newc.toString());
						cmdUpdatePoint = new CmdUpdatePoint(old, newc);
						executeCmd(cmdUpdatePoint);
						frame.addToLog(cmdUpdatePoint.toString());
					}
				}
				else if(s instanceof Line) {
					DlgForLine dialog = new DlgForLine();
					Line old = (Line) s;
					Line newc = (Line) s;
					dialog.update(newc.getFirst().getX(), newc.getFirst().getY(), newc.getLast().getX(), newc.getLast().getY(), newc.getColor());
					dialog.setVisible(true);
					if(dialog.getFinished() == 1) {
						newc.getFirst().setX(dialog.getxStart());
						newc.getFirst().setY(dialog.getyStart());
						newc.getLast().setX(dialog.getxEnd());
						newc.getLast().setY(dialog.getyEnd());
						newc.setColor(dialog.getContour());
						
						cmdUpdateLine = new CmdUpdateLine(old, newc);
						executeCmd(cmdUpdateLine);
						frame.addToLog(cmdUpdateLine.toString());
					}
				}

			}	
		}
		frame.getBtnSelect().setSelected(false);
	}
	
	public void executeCmd(Command c) {
		c.execute();
		
		undoStack.push(c);
		redoStack.clear();
		
        System.out.println("--------------");
        System.out.println("Size of Undo Stack : " + undoStack.size());
        System.out.println("Size of Redo Stack : " + redoStack.size());

		
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
	}
	
	public void undo() {

		frame.getBtnRedo().setEnabled(true);
		
		frame.addToLog("undo:" + undoStack.peek().toString());
				
		redoStack.push(undoStack.peek());
		undoStack.peek().unexecute();
		undoStack.pop();
		
        System.out.println("--------------");
        System.out.println("Size of Undo Stack : " + undoStack.size());
        System.out.println("Size of Redo Stack : " + redoStack.size());
        
		if(undoStack.isEmpty())
			frame.getBtnUndo().setEnabled(false);
	}
	
	public void redo() {
		
		frame.getBtnUndo().setEnabled(true);
		
		frame.addToLog("redo:" + redoStack.peek().toString());
		
		undoStack.push(redoStack.peek());
		redoStack.peek().execute();
		redoStack.pop();
		
        System.out.println("--------------");
        System.out.println("Size of Undo Stack : " + undoStack.size());
        System.out.println("Size of Redo Stack : " + redoStack.size());
		
		if(redoStack.isEmpty())
			frame.getBtnRedo().setEnabled(false);
	}

	
}




