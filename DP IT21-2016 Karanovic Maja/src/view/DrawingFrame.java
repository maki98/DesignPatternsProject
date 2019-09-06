package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class DrawingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel pnlShapes;
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	//dugmad
	private JToggleButton btnPoint;
	private JToggleButton btnLine;
	private JToggleButton btnSquare;
	private JToggleButton btnRectangle;
	private JToggleButton btnCircle;
	private JToggleButton btnHexagon;
	private JToggleButton btnSelect;

	
	private String shapeSelected;
	private JLabel lblContourColor;
	private JButton btnContourColor;
	private JLabel lblInsideColor;
	private JButton btnInsideColor;
	private JToggleButton btnModify;
	private JToggleButton btnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingFrame frame = new DrawingFrame();
					frame.pack();
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
		pnlShapes.setLayout(new MigLayout("", "[grow][]", "[][][][][][][][][][][][][][][][][][][][grow]"));
		
		btnPoint = new JToggleButton("Point");
		btnPoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.unselectShapes(e);
			}
		});
		pnlShapes.add(btnPoint, "cell 0 1,grow");
		
		btnLine = new JToggleButton("Line");
		btnLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.unselectShapes(e);
			}
		});
		pnlShapes.add(btnLine, "cell 1 1,grow");
		
		btnSquare = new JToggleButton("Square");
		btnSquare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.unselectShapes(e);
			}
		});
		pnlShapes.add(btnSquare, "cell 0 2,grow");
		
		btnRectangle = new JToggleButton("Rectangle");
		btnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.unselectShapes(e);
			}
		});
		pnlShapes.add(btnRectangle, "cell 1 2,grow");
		
		btnCircle = new JToggleButton("Circle");
		btnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.unselectShapes(e);
			}
		});
		pnlShapes.add(btnCircle, "cell 0 3,grow");
		
		btnHexagon = new JToggleButton("Hexagon");
		btnHexagon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.unselectShapes(e);
			}
		});
		pnlShapes.add(btnHexagon, "cell 1 3,grow");
		
		btnSelect = new JToggleButton("Select");
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				controller.unselectShapes(arg0);
			}
		});
		pnlShapes.add(btnSelect, "cell 0 8,growx");

		btnModify = new JToggleButton("Modify");
		pnlShapes.add(btnModify, "cell 0 9,growx");
		btnModify.setEnabled(false);
		
		btnDelete = new JToggleButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				controller.removeShapes(e);
			}
		});
		pnlShapes.add(btnDelete, "cell 1 9,growx");
		btnDelete.setEnabled(false);
		
		//group for shapes - allow only one button in a group to be selected at time
		CustomButtonGroup shapesGroup = new CustomButtonGroup();
		shapesGroup.add(btnPoint);
		shapesGroup.add(btnLine);
		shapesGroup.add(btnSquare);
		shapesGroup.add(btnCircle);
		shapesGroup.add(btnRectangle);
		shapesGroup.add(btnHexagon);
		shapesGroup.add(btnSelect);
		
		CustomButtonGroup updateShapesGroup = new CustomButtonGroup();
		updateShapesGroup.add(btnModify);
		updateShapesGroup.add(btnDelete);
		
		lblContourColor = new JLabel("Contour color:");
		pnlShapes.add(lblContourColor, "cell 0 5");
		
				btnContourColor = new JButton("     ");
				btnContourColor.setBackground(Color.BLACK);
				btnContourColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						controller.chooseContourColor();
					}
				});
				
				lblInsideColor = new JLabel("Inside color:");
				pnlShapes.add(lblInsideColor, "cell 1 5");
				pnlShapes.add(btnContourColor, "cell 0 6,grow");
		
		btnInsideColor = new JButton("     ");
		btnInsideColor.setBackground(Color.WHITE);
		btnInsideColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.chooseInsideColor();
			}
		});
		pnlShapes.add(btnInsideColor, "cell 1 6,grow");
		
		
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
				else if(btnSquare.isSelected())
				{
					controller.addSquare(arg0);
				}
				else if(btnRectangle.isSelected()) 
				{
					controller.addRectangle(arg0);
				}
				else if(btnCircle.isSelected())
				{
					controller.addCircle(arg0);
				}
				else if(btnSelect.isSelected()) 
				{
					controller.selectShapes(arg0);
				}
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

	public JToggleButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JToggleButton btnModify) {
		this.btnModify = btnModify;
	}

	public JToggleButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JToggleButton btnDelete) {
		this.btnDelete = btnDelete;
	}
	
	


}