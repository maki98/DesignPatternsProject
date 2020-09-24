package command.update;

import command.Command;
import geometry.Rectangle;
import geometry.Square;

public class CmdUpdateRectangle implements Command {
	
	private Rectangle originalState = new Rectangle();	
	private Rectangle oldState;
	private Rectangle newState;
	
	public CmdUpdateRectangle(Rectangle oldState, Rectangle newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		originalState = (Rectangle) oldState.clone();
		
		oldState.getUpperLeft().setX(newState.getUpperLeft().getX());
		oldState.getUpperLeft().setY(newState.getUpperLeft().getY());
		oldState.setPageLength(newState.getPageLength());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInsideColor(newState.getInsideColor());
			
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		oldState.getUpperLeft().setX(originalState.getUpperLeft().getX());
		oldState.getUpperLeft().setY(originalState.getUpperLeft().getY());
		oldState.setPageLength(originalState.getPageLength());
		oldState.setHeight(originalState.getHeight());
		oldState.setColor(originalState.getColor());
		oldState.setInsideColor(originalState.getInsideColor());
		
	}

	public String toString() {
		
		return "update:" + originalState.toString() + "->" + oldState.toString();
	
	}
}
