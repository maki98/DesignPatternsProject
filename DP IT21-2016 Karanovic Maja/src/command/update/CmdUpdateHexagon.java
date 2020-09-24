package command.update;

import adapter.HexagonAdapter;
import command.Command;
import geometry.Circle;
import hexagon.Hexagon;
import view.DrawingFrame;

public class CmdUpdateHexagon implements Command {
	
	private HexagonAdapter originalState = new HexagonAdapter();
	private HexagonAdapter oldState;
	private HexagonAdapter newState;
			
	public CmdUpdateHexagon(HexagonAdapter oldState, HexagonAdapter newState) {
		super();
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		originalState = oldState.clone();
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY()); 
		oldState.setR(newState.getR());
		oldState.setColor(newState.getColor());
		oldState.setInsideColor(newState.getInsideColor());

	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		
		oldState.setX(originalState.getX());
		oldState.setY(originalState.getY());
		oldState.setR(originalState.getR());
		oldState.setColor(originalState.getColor());
		oldState.setInsideColor(originalState.getInsideColor());
		
	}

	public String toString() {
		
		return "update:" + originalState.toString() + "->" + oldState.toString();
	
	}
}
