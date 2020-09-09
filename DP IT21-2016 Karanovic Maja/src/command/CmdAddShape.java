package command;

import geometry.DrawingModel;
import geometry.Shape;

public class CmdAddShape implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public CmdAddShape(DrawingModel model, Shape shape)
	{
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
		System.out.println(this.toString());
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	@Override
	public String toString() {
		return "add->" + shape.toString();
	}
	
}
