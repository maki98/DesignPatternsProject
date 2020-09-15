package command;

import geometry.DrawingModel;
import geometry.Shape;

public class CmdRemoveShape implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public CmdRemoveShape(DrawingModel model, Shape shape)
	{
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.remove(shape); 
	}

	@Override
	public void unexecute() {
		model.add(shape);
	}
	
	@Override 
	public String toString() {
		return "remove:" + shape.toString();
	}
	
}
