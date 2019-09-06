package observer;

import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import geometry.DrawingModel;
import view.DrawingFrame;

public class DrawingObserver implements Observer {
	
	private DrawingModel model;
	private DrawingFrame frame;
	
	public DrawingObserver (DrawingModel model, DrawingFrame frame) 
	{
		this.model=model;
		this.frame=frame;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {

		int count = 0;
		for(geometry.Shape shape : model.getAll()) 
		{
			if(shape.isSelected())
			{
				count++;
			}
		}
		
		if(count == 0) {
			frame.getBtnModify().setEnabled(false);
			frame.getBtnDelete().setEnabled(false);
		}
		else {
			frame.getBtnModify().setEnabled(true);
			frame.getBtnDelete().setEnabled(true);
		}
	}
	

}


