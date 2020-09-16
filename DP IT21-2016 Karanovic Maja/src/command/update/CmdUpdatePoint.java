package command.update;

import command.Command;
import geometry.Point;

public class CmdUpdatePoint implements Command {
	
	private Point originalState = new Point();
	private Point oldState;
	private Point newState;	

	public CmdUpdatePoint(Point oldState, Point newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	    originalState = (Point) oldState.clone();

		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setColor(newState.getColor());		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		oldState.setX(originalState.getX());
		oldState.setY(originalState.getY());
		oldState.setColor(originalState.getColor());
			
	}
	
	public String toString() {
		
		return "update:point->OLD->" + originalState.toString() + ",NEW->point:" + oldState.toString();
	
	}
}
