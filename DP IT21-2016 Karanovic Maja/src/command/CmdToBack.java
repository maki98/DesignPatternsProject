package command;

import java.util.Collections;

import geometry.DrawingModel;
import geometry.Shape;

public class CmdToBack implements Command {

	private static final long serialVersionUID = 1L;
	private Shape shape;
	private DrawingModel model;
	private int index;

	public CmdToBack(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}

	@Override
	public void execute() {
		this.index = model.getAll().indexOf(shape);
		Collections.swap(model.getAll(), index, index - 1);
	}

	@Override
	public void unexecute() {
		this.index = model.getAll().indexOf(shape);
		Collections.swap(model.getAll(), index, index + 1);
	}

	public String toString() {
		return "toback->" + shape.toString();
	}

}