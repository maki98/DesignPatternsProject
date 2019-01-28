package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import controller.DrawingController;
import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class DrawingFrame extends JFrame {

	private JPanel contentPane;
	private JPanel pnlShapes;
	private JPanel pnlColors;
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	//dugmad
	private JToggleButton btnPoint;
	private JToggleButton btnLine;
	private JToggleButton btnSquare;
	private JToggleButton btnRectangle;
	private JToggleButton btnCircle;
	
	private String shapeSelected;
	private JLabel lblContourColor;
	private JButton btnContourColor;
	private JLabel lblInsideColor;
	private JButton btnInsideColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingFrame frame = new DrawingFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DrawingFrame() {
		setTitle("Maja Karanovic IT21/2016");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		

		//ubacivanje view-a
		view.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		view.setBackground(Color.WHITE);
		contentPane.add(view, BorderLayout.CENTER);
		
		//ubacivanje panela koji sadrzi dugmice za odabir oblika
		pnlShapes = new JPanel();
		pnlShapes.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Shapes:", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		contentPane.add(pnlShapes, BorderLayout.WEST);
		pnlShapes.setLayout(new MigLayout("", "[grow]", "[][][][][][][][][][][][][][][][][][][][grow]"));
		
		btnPoint = new JToggleButton("Point");
		pnlShapes.add(btnPoint, "cell 0 1, grow");
		
		btnLine = new JToggleButton("Line");
		
		pnlShapes.add(btnLine, "cell 0 2, grow");
		
		btnSquare = new JToggleButton("Square");
		pnlShapes.add(btnSquare, "cell 0 3, grow");
		
		btnRectangle = new JToggleButton("Rectangle");
		pnlShapes.add(btnRectangle, "cell 0 4,, grow");
		
		btnCircle = new JToggleButton("Circle");
		pnlShapes.add(btnCircle, "cell 0 5, grow");
		
		lblContourColor = new JLabel("Contour color:");
		pnlShapes.add(lblContourColor, "cell 0 14");
		
		//automatski untoggluje ostale buttone kada je jedan toggleovan
		ButtonGroup group = new ButtonGroup();
		group.add(btnPoint);
		group.add(btnLine);
		group.add(btnSquare);
		group.add(btnCircle);
		group.add(btnRectangle);
		
		btnContourColor = new JButton("     ");
		btnContourColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.chooseContourColor();
			}
		});
		pnlShapes.add(btnContourColor, "cell 0 15, grow");
		
		lblInsideColor = new JLabel("Inside color:");
		pnlShapes.add(lblInsideColor, "cell 0 16");
		
		btnInsideColor = new JButton("     ");
		btnInsideColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseInsideColor();
			}
		});
		pnlShapes.add(btnInsideColor, "cell 0 17, grow");
		
		//mouse event listener
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnPoint.isSelected()) 
				{
					controller.addPoint(arg0);
					
				}
				else if(btnLine.isSelected()) 
				{
					controller.addLine(arg0);
				}
				else if(btnSquare.isSelected());
				else if(btnRectangle.isSelected());
				else if(btnCircle.isSelected());
			}
		});
		
		
	}
	
	public DrawingView getView()
	{
		return view;
	}
	
	public void setController(DrawingController controller)
	{
		this.controller = controller;
	}

	public String getShapeSelected() {
		return shapeSelected;
	}

	public void setShapeSelected(String shapeSelected) {
		this.shapeSelected = shapeSelected;
	}

	public JButton getBtnContourColor() {
		return btnContourColor;
	}

	public void setBtnContourColor(JButton btnContourColor) {
		this.btnContourColor = btnContourColor;
	}

	public JButton getBtnInsideColor() {
		return btnInsideColor;
	}

	public void setBtnInsideColor(JButton btnInsideColor) {
		this.btnInsideColor = btnInsideColor;
	}
	


}