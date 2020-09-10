package command;

import geometry.DrawingModel;
import geometry.Shape;

public class CmdSelect implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public CmdSelect(Shape shape)
	{
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		shape.setSelected(true);
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
	}
	
	@Override
	public String toString() {
		return "select->" + shape.toString();
	}
	
}
