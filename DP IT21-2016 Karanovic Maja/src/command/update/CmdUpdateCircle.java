package command.update;

import command.Command;
import geometry.Circle;

public class CmdUpdateCircle implements Command {

	private Circle originalState = new Circle();	
	private Circle oldState;
	private Circle newState;
	
	public CmdUpdateCircle(Circle oldState, Circle newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		originalState = (Circle) oldState.clone();
		
		oldState.getCenter().setX(newState.getCenter().getX());
		oldState.getCenter().setY(newState.getCenter().getY());
		oldState.setRadius(newState.getRadius());
		oldState.setColor(newState.getColor());
		oldState.setInsideColor(newState.getInsideColor());
			
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		oldState.getCenter().setX(originalState.getCenter().getX());
		oldState.getCenter().setY(originalState.getCenter().getY());
		oldState.setRadius(originalState.getRadius());
		oldState.setColor(originalState.getColor());
		oldState.setInsideColor(originalState.getInsideColor());
		
	}

	public String toString() {
		
		return "update:" + originalState.toString() + "->" + oldState.toString();
	
	}
}
