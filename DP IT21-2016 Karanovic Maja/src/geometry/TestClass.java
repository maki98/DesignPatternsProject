package geometry;

import java.awt.Color;

public class TestClass {

	public static void main(String[] args)
	{
		//srediti ove boje
		Point p = new Point(1,1,"Red");
		System.out.println(p);
		
		Line l = new Line(p,new Point(2,1,Color.YELLOW));
		System.out.println(l);
		
		Square s = new Square(p, 12, Color.BLACK, Color.BLUE);
		System.out.println(s);
	}
}
