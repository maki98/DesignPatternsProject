package adapter;

import java.awt.Graphics;

import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	private Hexagon hexagon;
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	@Override
	public void moveOn(int x, int y) {
		hexagon.setX(x);
		hexagon.setY(y);
		
	}

	@Override
	public void moveBy(int byX, int byY) {
	
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) 
			return hexagon.getR() - ((HexagonAdapter) o).getR();
		return 0;
	}
	
	public HexagonAdapter makeHexagon() {
		Hexagon h = new Hexagon(hexagon.getX(), hexagon.getY(), hexagon.getR());
		h.setBorderColor(getColor());
		h.setAreaColor(getInsideColor());
		return new HexagonAdapter(h);
	}

	public int getR() {
		return hexagon.getR();
	}
	
	public void setR(int r) {
		hexagon.setR(r);
	}
	

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double perimeter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawShape(Graphics g) {
		hexagon.paint(g);
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	
}
