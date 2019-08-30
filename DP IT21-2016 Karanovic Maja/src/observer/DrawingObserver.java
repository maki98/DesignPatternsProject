package observer;

import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

import view.DrawingFrame;

public class DrawingObserver implements PropertyChangeListener {
	
	DrawingFrame frame;
	
	public DrawingObserver(DrawingFrame frame) {
		this.frame = frame;
	}
	
	public void addListener(JButton button, MouseAdapter adapter) {
		if (button.isEnabled() == false) {
			button.setEnabled(true);
			button.addMouseListener(adapter);
		}
	}
	
	public void removeListener(JButton button, MouseAdapter adapter) {
		if (button.isEnabled()) {
			button.removeMouseListener(adapter);
			button.setEnabled(false);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
