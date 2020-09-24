package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import javax.sound.sampled.ReverbType;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
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
import dialogs.DlgForLogs;
import dialogs.DlgForPoint;
import dialogs.DlgForRectangle;
import dialogs.DlgForSquare;
import files.Manager;
import files.Drawing;
import files.Log;
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

public class DrawingController implements Serializable {

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
				else if (model.get(i).isSelected() == true)
				{
					CmdSelect cmdSelect = new CmdSelect(model.get(i));
					cmdSelect.unexecute();

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
			CmdSelect cmdSelect = new CmdSelect(shape);
			cmdSelect.unexecute();
		}
	}
	
	public void removeSelection() {
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
	
	public void removeAll() {
		
		if(frame.getBtnRemoveAll().isEnabled())
		{
			if(JOptionPane.showConfirmDialog(null, "Are you sure you want to discard this drawing?", "Warning!", JOptionPane.YES_NO_OPTION) == 0) {
				model.getAll().clear();
				listOfCommands.clear();
				frame.getDlm().clear();
				undoStack.clear();
				redoStack.clear();
			}
		}
		frame.getBtnRemoveAll().setSelected(false);
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
					Circle newc = (Circle) ((Circle) s).clone();
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
					Rectangle newc = (Rectangle) ((Rectangle) s).clone();
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
					Square newc = (Square) ((Square) s).clone();
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
					HexagonAdapter old = (HexagonAdapter) ((HexagonAdapter) s).clone();
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
					Point old = (Point) s;
					Point newc = (Point) ((Point) s).clone();
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

		
		frame.getView().repaint();
		
		undoStack.push(c);
		redoStack.clear();
		
        System.out.println("--------------");
        System.out.println("Size of Undo Stack : " + undoStack.size());
        System.out.println("Size of Redo Stack : " + redoStack.size());

		
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		
	}
	
	public void undo() {
		
		removeSelection();

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
		
		removeSelection();
		
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

	
	public void save(int option) { 

		if (option == JOptionPane.YES_OPTION) { 
			Manager logMng = new Manager(new Log(frame.getDlm()));
			logMng.save();
		} else if (option == JOptionPane.NO_OPTION) {
			Manager drawingMng = new Manager(new Drawing(model.getAll()));
			drawingMng.save();
		}
	}
	
	public void open() {
		try {
			JFileChooser fileChooser = new JFileChooser("C:\\Users\\mkaranovic\\Desktop");

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File f = (fileChooser.getSelectedFile());

				if (f.getAbsolutePath().endsWith("_log.txt")) {

					model.getAll().clear();
					listOfCommands.clear();
					frame.getDlm().clear();
					undoStack.clear();
					redoStack.clear();

					FileReader fr = new FileReader(f);
					BufferedReader bf = new BufferedReader(fr);

					DlgForLogs dlgForLogs = new DlgForLogs(DrawingController.this, bf);
					dlgForLogs.setVisible(true);

				} else {
					FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);
					model.getAll().clear();
					listOfCommands.clear();
					ArrayList<Shape> inputList = (ArrayList<Shape>) ois.readObject();

					for (Shape s : inputList) {
						model.add(s);
					}

					ois.close();
					fis.close();
				}
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error while opening file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void loadNext(String line) {

		//array: [add,point,(96,69,[0,0,0])]
		//values: 96,69,0,0,0
		removeSelection();
		String[] array = line.split(":");
		
			if (array[0].equals("add")) {

				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");


				if (array[1].equals("point")) {

					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
					
					cmdAddShape = new CmdAddShape(model, p);
					executeCmd(cmdAddShape);
					frame.addToLog(line);
				} else if (array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					cmdAddShape = new CmdAddShape(model, l);
					executeCmd(cmdAddShape);
					frame.addToLog(line);

				} else if (array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square s = new Square(upperLeft, length, contourColor, insideColor);
					cmdAddShape = new CmdAddShape(model, s);
					executeCmd(cmdAddShape);
					frame.addToLog(line);

				} else if (array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					cmdAddShape = new CmdAddShape(model, c);
					executeCmd(cmdAddShape);
					frame.addToLog(line);

				} else if (array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					cmdAddShape = new CmdAddShape(model, r);
					executeCmd(cmdAddShape);
					frame.addToLog(line);

				}	
				
				//TO DO
				else if (array[1].equals("hexagon")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					cmdAddShape = new CmdAddShape(model, r);
					executeCmd(cmdAddShape);
				
				}	
			} else if (array[0].equals("update")) {
				System.out.println(array[2]);
				System.out.println(array[3]);
				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				String newValuesLine = array[3].replaceAll("[^0-9,.]", "");
				String[] newValues = newValuesLine.split(",");
				
				if (array[1].equals("point")) {

					Point oldState = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
							parseColor(values[2], values[3], values[4]));
					Point newState = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
							parseColor(newValues[2], newValues[3], newValues[4]));
					
					System.out.println(oldState.toString());
					System.out.println(newState.toString());

					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Point)
						{	
							if(oldState.compareTo((Point) s) == 0)
								cmdUpdatePoint = new CmdUpdatePoint((Point) s, newState);
								executeCmd(cmdUpdatePoint);
								frame.addToLog(cmdUpdatePoint.toString());
								removeSelection();
						}
					}
				} else if (array[1].equals("line")) {

					Line oldState = new Line(new Point(Integer.parseInt(values[0]), 
							Integer.parseInt(values[1])), new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3])),
							parseColor(values[4], values[5], values[6]));
					Line newState = new Line(new Point(Integer.parseInt(newValues[0]), 
							Integer.parseInt(newValues[1])), new Point(Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3])),
							parseColor(newValues[4], newValues[5], newValues[6]));
					
					System.out.println(oldState.toString());
					System.out.println(newState.toString());

					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Line)
						{	
							if(oldState.compareTo((Line) s) == 0)
								cmdUpdateLine = new CmdUpdateLine((Line) s, newState);
								executeCmd(cmdUpdateLine);
								frame.addToLog(cmdUpdateLine.toString());
								removeSelection();
						}
					}
				} else if (array[1].equals("circle")) {

					Circle oldState = new Circle(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])), 
							Integer.parseInt(values[2]), 
							parseColor(values[3], values[4], values[5]),
							parseColor(values[6], values[7], values[8]));
					Circle newState = new Circle(new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])), 
							Integer.parseInt(newValues[2]), 
							parseColor(newValues[3], newValues[4], newValues[5]),
							parseColor(newValues[6], newValues[7], newValues[8]));		
					
					System.out.println(oldState.toString());
					System.out.println(newState.toString());

					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Circle)
						{	
							if(oldState.compareTo((Circle) s) == 0)
								cmdUpdateCircle = new CmdUpdateCircle((Circle) s, newState);
								executeCmd(cmdUpdateCircle);
								frame.addToLog(cmdUpdateCircle.toString());
								removeSelection();

						}
					}
				} else if (array[1].equals("square")) {

					Square oldState = new Square(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]),
							parseColor(values[3], values[4], values[5]),
							parseColor(values[6], values[7], values[8]));
					Square newState = new Square(new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]),
							parseColor(newValues[3], newValues[4], newValues[5]),
							parseColor(newValues[6], newValues[7], newValues[8]));

					System.out.println(oldState.toString());
					System.out.println(newState.toString());

					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Square)
						{	
							if(oldState.compareTo((Square) s) == 0)
								cmdUpdateSquare = new CmdUpdateSquare((Square) s, newState);
								executeCmd(cmdUpdateSquare);
								frame.addToLog(cmdUpdateSquare.toString());
								removeSelection();

						}
					}
				} else if (array[1].equals("rectangle")) {

					Rectangle oldState = new Rectangle(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]),
							Integer.parseInt(values[3]),
							parseColor(values[4], values[5], values[6]),
							parseColor(values[7], values[8], values[9]));
					Rectangle newState = new Rectangle(new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]),
							Integer.parseInt(newValues[3]),
							parseColor(newValues[4], newValues[5], newValues[6]),
							parseColor(newValues[7], newValues[8], newValues[9]));
					
					System.out.println(oldState.toString());
					System.out.println(newState.toString());

					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Rectangle)
						{	
							if(oldState.compareTo((Rectangle) s) == 0)
								cmdUpdateRectangle = new CmdUpdateRectangle((Rectangle) s, newState);
								executeCmd(cmdUpdateRectangle);
								frame.addToLog(cmdUpdateRectangle.toString());
								removeSelection();

						}
					}
				}
			} else if (array[0].equals("select")) {

				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				if (array[1].equals("point")) {

					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
					
					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Point)
						{	
							if(p.compareTo((Point) s) == 0)
								cmdSelect = new CmdSelect((Point) s);
								executeCmd(cmdSelect);
								frame.addToLog(cmdSelect.toString());

						}
					}
				} else if (array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					
					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Line)
						{	
							if(l.compareTo((Line) s) == 0)
								cmdSelect = new CmdSelect((Line) l);
								executeCmd(cmdSelect);
								frame.addToLog(cmdSelect.toString());
						}
					}

				} else if (array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square sq = new Square(upperLeft, length, contourColor, insideColor);
					
					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Square)
						{	
							if(sq.compareTo((Square) s) == 0)
								cmdSelect = new CmdSelect((Square) s);
								executeCmd(cmdSelect);
								frame.addToLog(cmdSelect.toString());

						}
					}

				} else if (array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					
					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Circle)
						{	
							if(c.compareTo((Circle) s) == 0)
								cmdSelect = new CmdSelect((Circle) s);
								executeCmd(cmdSelect);
								frame.addToLog(cmdSelect.toString());

						}
					}

				} else if (array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					
					Iterator<Shape> it = model.getAll().iterator();
					while(it.hasNext()) {
						Shape s = it.next();
								
						if(s instanceof Rectangle)
						{	
							if(r.compareTo((Rectangle) s) == 0)
								cmdSelect = new CmdSelect((Rectangle) s);
								executeCmd(cmdSelect);
								frame.addToLog(cmdSelect.toString());

						}
					}

				}	
				
			} else if ((array[0].equals("undo"))) {
				
				String logCommand = line.substring(5);
				
				//obican for zbog CurrentModificationException
				for(int i = 0; i < undoStack.size(); i++) {
					if(undoStack.get(i).toString().compareTo(logCommand) == 0)
					{
						this.undo();
					}
				}
			} else if ((array[0].equals("redo"))) {
				
				String logCommand = line.substring(5);
				
				//obican for zbog ConcurrentModificationException
				for(int i = 0; i < redoStack.size(); i++) {
					if(redoStack.get(i).toString().compareTo(logCommand) == 0)
					{
						this.redo();
					}
				}
			} else if (array[0].equals("remove")) {
				
				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				
				if(array[1].equals("point")) {
						
					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
						
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Point)
						{
							if(((Point) model.getAll().get(i)).compareTo(p) == 0)
							{
								cmdRemoveShape = new CmdRemoveShape(model, model.getAll().get(i));
								executeCmd(cmdRemoveShape);
								frame.addToLog(cmdRemoveShape.toString());
								removeSelection();

								
							}
						}
					}		
				} else if(array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Line)
						{
							if(((Line) model.getAll().get(i)).compareTo(l) == 0)
							{
								cmdRemoveShape = new CmdRemoveShape(model, model.getAll().get(i));
								executeCmd(cmdRemoveShape);
								frame.addToLog(cmdRemoveShape.toString());
								removeSelection();

							}
						}
					}	
				} else if(array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Circle)
						{
							if(((Circle) model.getAll().get(i)).compareTo(c) == 0)
							{
								cmdRemoveShape = new CmdRemoveShape(model, model.getAll().get(i));
								executeCmd(cmdRemoveShape);
								frame.addToLog(cmdRemoveShape.toString());
								removeSelection();

							}
						}
					}	
				} else if(array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square s = new Square(upperLeft, length, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Square)
						{
							if(((Square) model.getAll().get(i)).compareTo(s) == 0)
							{
								cmdRemoveShape = new CmdRemoveShape(model, model.getAll().get(i));
								executeCmd(cmdRemoveShape);
								frame.addToLog(cmdRemoveShape.toString());
								removeSelection();

							}
						}
					}	
				} else if(array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Rectangle)
						{
							if(((Rectangle) model.getAll().get(i)).compareTo(r) == 0)
							{
								cmdRemoveShape = new CmdRemoveShape(model, model.getAll().get(i));
								executeCmd(cmdRemoveShape);
								frame.addToLog(cmdRemoveShape.toString());
								removeSelection();
							}
						}
					}	
				}
				
			} else if (array[0].equals("toback")) {
				
				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				
				if(array[1].equals("point")) {
					
					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
						
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Point)
						{
							if(((Point) model.getAll().get(i)).compareTo(p) == 0)
							{
								cmdToBack = new CmdToBack(model, model.getAll().get(i));
								executeCmd(cmdToBack);
								frame.addToLog(cmdToBack.toString());
								removeSelection();

								
							}
						}
						break;
					}		
				} else if(array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Line)
						{
							if(((Line) model.getAll().get(i)).compareTo(l) == 0)
							{
								cmdToBack = new CmdToBack(model, model.getAll().get(i));
								executeCmd(cmdToBack);
								frame.addToLog(cmdToBack.toString());
								removeSelection();

							}
						}
						break;
					}	
				} else if(array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Circle)
						{
							if(((Circle) model.getAll().get(i)).compareTo(c) == 0)
							{
								cmdToBack = new CmdToBack(model, model.getAll().get(i));
								executeCmd(cmdToBack);
								frame.addToLog(cmdToBack.toString());
								removeSelection();

							}
						}
						break;
					}	
				} else if(array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square s = new Square(upperLeft, length, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Square)
						{
							if(((Square) model.getAll().get(i)).compareTo(s) == 0)
							{
								cmdToBack = new CmdToBack(model, model.getAll().get(i));
								executeCmd(cmdToBack);
								frame.addToLog(cmdToBack.toString());
								removeSelection();

							}
						}
						break;
					}	
				} else if(array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Rectangle)
						{
							if(((Rectangle) model.getAll().get(i)).compareTo(r) == 0)
							{
								cmdToBack = new CmdToBack(model, model.getAll().get(i));
								executeCmd(cmdToBack);
								frame.addToLog(cmdToBack.toString());
								removeSelection();
							}
						}
						break;
					}	
				}
				
			} else if (array[0].equals("bringtoback")) {
				
				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				
				if(array[1].equals("point")) {
					
					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
						
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Point)
						{
							if(((Point) model.getAll().get(i)).compareTo(p) == 0)
							{
								cmdBringToBack = new CmdBringToBack(model, model.getAll().get(i));
								executeCmd(cmdBringToBack);
								frame.addToLog(cmdBringToBack.toString());
								removeSelection();
								
							}
						}
						break;
					}		
				} else if(array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Line)
						{
							if(((Line) model.getAll().get(i)).compareTo(l) == 0)
							{
								cmdBringToBack = new CmdBringToBack(model, model.getAll().get(i));
								executeCmd(cmdBringToBack);
								frame.addToLog(cmdBringToBack.toString());
								removeSelection();

							}
						}
						break;
					}	
				} else if(array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Circle)
						{
							if(((Circle) model.getAll().get(i)).compareTo(c) == 0)
							{
								cmdBringToBack = new CmdBringToBack(model, model.getAll().get(i));
								executeCmd(cmdBringToBack);
								frame.addToLog(cmdBringToBack.toString());
								removeSelection();
							}
						}
						break;
					}	
				} else if(array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square s = new Square(upperLeft, length, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Square)
						{
							if(((Square) model.getAll().get(i)).compareTo(s) == 0)
							{
								cmdBringToBack = new CmdBringToBack(model, model.getAll().get(i));
								executeCmd(cmdBringToBack);
								frame.addToLog(cmdBringToBack.toString());
								removeSelection();
							}
						}
						break;
					}	
				} else if(array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Rectangle)
						{
							if(((Rectangle) model.getAll().get(i)).compareTo(r) == 0)
							{
								
								cmdBringToBack = new CmdBringToBack(model, model.getAll().get(i));
								executeCmd(cmdBringToBack);
								frame.addToLog(cmdBringToBack.toString());
								removeSelection();
							}
						}
						break;
					}	
				}
				
			} else if (array[0].equals("tofront")) {
				
				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				
				if(array[1].equals("point")) {
					
					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
						
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Point)
						{
							if(((Point) model.getAll().get(i)).compareTo(p) == 0)
							{
								cmdToFront = new CmdToFront(model, model.getAll().get(i));
								executeCmd(cmdToFront);
								frame.addToLog(cmdToFront.toString());
								removeSelection();
								
							}
						}
						break;
					}		
				} else if(array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Line)
						{
							if(((Line) model.getAll().get(i)).compareTo(l) == 0)
							{
								cmdToFront = new CmdToFront(model, model.getAll().get(i));
								executeCmd(cmdToFront);
								frame.addToLog(cmdToFront.toString());
								removeSelection();
							}
						}
						break;
					}	
				} else if(array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Circle)
						{
							if(((Circle) model.getAll().get(i)).compareTo(c) == 0)
							{
								cmdToFront = new CmdToFront(model, (Circle) model.getAll().get(i));
								executeCmd(cmdToFront);
								frame.addToLog(cmdToFront.toString());
								removeSelection();
							}
						}
						break;
					}	
				} else if(array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square s = new Square(upperLeft, length, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Square)
						{
							if(((Square) model.getAll().get(i)).compareTo(s) == 0)
							{
								System.out.println(model.getAll().size());
								System.out.println(model.getAll().get(i));
								cmdToFront = new CmdToFront(model, model.getAll().get(i));
								executeCmd(cmdToFront);
								frame.addToLog(cmdToFront.toString());
								removeSelection();
							}
						}
						break;
					}	
				} else if(array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Rectangle)
						{
							if(((Rectangle) model.getAll().get(i)).compareTo(r) == 0)
							{
								System.out.println(model.getAll().size());
								System.out.println(model.getAll().get(i));
								cmdToFront = new CmdToFront(model, model.getAll().get(i));
								executeCmd(cmdToFront);
								frame.addToLog(cmdToFront.toString());
								removeSelection();
							}
						}
						break;
					}	
				}
				
			} else if (array[0].equals("bringtofront")) {
				
				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				
				if(array[1].equals("point")) {
					
					Color contourColor = parseColor(values[2], values[3], values[4]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), contourColor);
						
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Point)
						{
							if(((Point) model.getAll().get(i)).compareTo(p) == 0)
							{
								cmdBringToFront = new CmdBringToFront(model, model.getAll().get(i));
								executeCmd(cmdBringToFront);
								frame.addToLog(cmdBringToFront.toString());
								removeSelection();
								
							}
						}
						break;
					}		
				} else if(array[1].equals("line")) {
					
					Point first = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point last = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color contourColor = parseColor(values[4], values[5], values[6]);
					
					Line l = new Line(first, last, contourColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Line)
						{
							if(((Line) model.getAll().get(i)).compareTo(l) == 0)
							{
								cmdBringToFront = new CmdBringToFront(model, model.getAll().get(i));
								executeCmd(cmdBringToFront);
								frame.addToLog(cmdBringToFront.toString());
								removeSelection();

							}
						}
						break;
					}	
				} else if(array[1].equals("circle")) {
					
					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer radius = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Circle c = new Circle(center, radius, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Circle)
						{
							if(((Circle) model.getAll().get(i)).compareTo(c) == 0)
							{
								cmdBringToFront = new CmdBringToFront(model, model.getAll().get(i));
								executeCmd(cmdBringToFront);
								frame.addToLog(cmdBringToFront.toString());
								removeSelection();
							}
							break;
						}
					}	
				} else if(array[1].equals("square")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Color contourColor = parseColor(values[3], values[4], values[5]);
					Color insideColor = parseColor(values[6], values[7], values[8]);

					Square s = new Square(upperLeft, length, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Square)
						{
							if(((Square) model.getAll().get(i)).compareTo(s) == 0)
							{
								cmdBringToFront = new CmdBringToFront(model, model.getAll().get(i));
								executeCmd(cmdBringToFront);
								frame.addToLog(cmdBringToFront.toString());
								removeSelection();
							}
						}
						break;
					}	
				} else if(array[1].equals("rectangle")) {
					
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Integer length = Integer.parseInt(values[2]);
					Integer height = Integer.parseInt(values[3]);

					Color contourColor = parseColor(values[4], values[5], values[6]);
					Color insideColor = parseColor(values[7], values[8], values[9]);

					Rectangle r = new Rectangle(upperLeft, length, height, contourColor, insideColor);
					
					for(int i = 0; i < model.getAll().size(); i++)
					{
						if(model.getAll().get(i) instanceof Rectangle)
						{
							if(((Rectangle) model.getAll().get(i)).compareTo(r) == 0)
							{
								
								cmdBringToFront = new CmdBringToFront(model, model.getAll().get(i));
								executeCmd(cmdBringToFront);
								frame.addToLog(cmdBringToFront.toString());
								removeSelection();
							}
						}
						break;
					}	
				}
			}
		}
				
	


	public Color parseColor(String r, String g, String b) {
		Color color = new Color(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b));
		return color;
	}
}




