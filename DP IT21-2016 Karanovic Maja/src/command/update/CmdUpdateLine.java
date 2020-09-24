package command.update;

import command.Command;
import geometry.Line;

public class CmdUpdateLine implements Command {

	private Line originalState = new Line();
	private Line oldState;
	private Line newState;

	public CmdUpdateLine(Line oldState, Line newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		originalState = (Line) oldState.clone();
		
		oldState.getFirst().setX(newState.getFirst().getX());
		oldState.getFirst().setY(newState.getFirst().getY());
		oldState.getLast().setX(newState.getLast().getX());
		oldState.getLast().setY(newState.getLast().getY());
		oldState.setColor(newState.getColor());		
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		oldState.getFirst().setX(originalState.getFirst().getX());
		oldState.getFirst().setY(originalState.getFirst().getY());
		oldState.getLast().setX(originalState.getLast().getX());
		oldState.getLast().setY(originalState.getLast().getY());
		oldState.setColor(originalState.getColor());		
		
	}

	public String toString() {
		
		return "update:" + originalState.toString() + "->" + oldState.toString();
	
	}
	

}
