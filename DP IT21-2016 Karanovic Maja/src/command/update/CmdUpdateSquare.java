package command.update;

import command.Command;
import geometry.Square;

public class CmdUpdateSquare implements Command {

	private Square originalState = new Square();	
	private Square oldState;
	private Square newState;
	
	public CmdUpdateSquare(Square oldState, Square newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		originalState = (Square) oldState.clone();
		
		oldState.getUpperLeft().setX(newState.getUpperLeft().getX());
		oldState.getUpperLeft().setY(newState.getUpperLeft().getY());
		oldState.setPageLength(newState.getPageLength());
		oldState.setColor(newState.getColor());
		oldState.setInsideColor(newState.getInsideColor());
			
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		oldState.getUpperLeft().setX(originalState.getUpperLeft().getX());
		oldState.getUpperLeft().setY(originalState.getUpperLeft().getY());
		oldState.setPageLength(originalState.getPageLength());
		oldState.setColor(originalState.getColor());
		oldState.setInsideColor(originalState.getInsideColor());
		
	}

	public String toString() {
		
		return "update:square->OLD->" + originalState.toString() + ",NEW->square:" + oldState.toString();
	
	}
}
