package view;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.DrawingModel;
import geometry.Shape;

public class DrawingView extends JPanel {

	private DrawingModel model;
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(model != null)
		{
			Iterator<Shape> it = model.getAll().iterator();
			while(it.hasNext())
			{
				it.next().drawShape(g);
			}
		}
	}
	
	public void setModel(DrawingModel model)
	{
		this.model = model;
	}
}
