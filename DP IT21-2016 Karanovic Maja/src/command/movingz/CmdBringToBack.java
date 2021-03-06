package command.movingz;

import javax.swing.JOptionPane;

import command.Command;
import geometry.DrawingModel;
import geometry.Shape;

public class CmdBringToBack implements Command {

	private static final long serialVersionUID = 1L;
	private Shape shape;
	private DrawingModel model;
	private int index;

	public CmdBringToBack(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
		this.index = model.getAll().indexOf(shape);
	}

	@Override
	public void execute() {
		
		System.out.print(index);
		
		if(index != 0)
		{
			model.remove(shape);
			model.getAll().add(0, shape);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Element is already on the back");		
		}
		
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.getAll().add(index, shape);
	}

	@Override
	public String toString() {
		return "bringtoback:" + shape.toString();
	}

}