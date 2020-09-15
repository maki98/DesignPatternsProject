package command.movingz;

import java.util.Collections;

import javax.swing.JOptionPane;

import command.Command;
import geometry.DrawingModel;
import geometry.Shape;

public class CmdBringToFront implements Command {

	private static final long serialVersionUID = 1L;
	private Shape shape;
	private DrawingModel model;
	private int index;

	public CmdBringToFront(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
		this.index = model.getAll().indexOf(shape);
	}

	@Override
	public void execute() {

		System.out.print(index);
		
		if(index != model.getAll().size() - 1)
		{
			model.remove(shape);
			model.add(shape);	
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Element is already on the front");		
		}

	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.getAll().add(index, shape);
	}

	@Override
	public String toString() {
		return "bringtofront:" + shape.toString();
	}

}