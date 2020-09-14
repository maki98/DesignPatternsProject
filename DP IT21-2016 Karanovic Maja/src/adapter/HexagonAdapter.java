package adapter;
import java.awt.Color;
import java.awt.Graphics;

import geometry.Circle;
import geometry.Line;
import geometry.Point;
import geometry.Square;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	private Hexagon hexagon;
	
	public HexagonAdapter () {
		super();
	}
	
	public HexagonAdapter (Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public HexagonAdapter (Hexagon hexagon, Color contour, Color inside) {
		super();
		this.setColor(contour);
		this.setInsideColor(inside);
	}
	
	@Override
	public void moveOn(int x, int y) {
		// TODO Auto-generated method stub
		hexagon.setX(x);
		hexagon.setY(y);
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		hexagon.setX(hexagon.getX() + byX);
		hexagon.setY(hexagon.getY() + byY);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof HexagonAdapter)
		{
			return (int) (this.getR()-((HexagonAdapter) o).hexagon.getR());
		}
		else 
			return 0;
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawShape(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(this.getColor());
		hexagon.paint(g);
		fill(g);
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
	
	public boolean equals(Object obj) 
	{
		if(obj instanceof HexagonAdapter) 
		{
			Hexagon helper = ((HexagonAdapter) obj).getHexagon();
			if(hexagon.getX() == helper.getX() && hexagon.getY() == helper.getY() && hexagon.getR() == helper.getR())
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public int getR() {
		return hexagon.getR();
	}
	
	public void setR(int r) {
		hexagon.setR(r);
	}
	
	public int getX() {
		return hexagon.getX();
	}
	
	public void setX(int x) {
		hexagon.setX(x);
	}
	
	public int getY() {
		return hexagon.getY();
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public void setY(int y) {
		hexagon.setY(y);
	}
	
}
