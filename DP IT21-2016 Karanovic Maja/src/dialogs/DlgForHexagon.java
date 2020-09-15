package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DlgForHexagon extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoordinate;
	private JTextField txtYCoordinate;
	private JTextField txtRadius;
	private JButton btnContourColor, btnInsideColor;
	
	private int x, y, radius;
	private Color contour, inside;
	
	private int finished = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgForHexagon dialog = new DlgForHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgForHexagon() {
		setTitle("Hexagon");
		setModal(true);
		setBounds(100, 100, 485, 217);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{29, 76, 99, 40, 79, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{16, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblXCoordinate = new JLabel("X Coordinate:");
			GridBagConstraints gbc_lblXCoordinate = new GridBagConstraints();
			gbc_lblXCoordinate.anchor = GridBagConstraints.EAST;
			gbc_lblXCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXCoordinate.gridx = 1;
			gbc_lblXCoordinate.gridy = 1;
			contentPanel.add(lblXCoordinate, gbc_lblXCoordinate);
		}
		{
			txtXCoordinate = new JTextField();
			GridBagConstraints gbc_txtXCoordinate = new GridBagConstraints();
			gbc_txtXCoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtXCoordinate.gridx = 2;
			gbc_txtXCoordinate.gridy = 1;
			contentPanel.add(txtXCoordinate, gbc_txtXCoordinate);
			txtXCoordinate.setColumns(10);
		}
		{
			JLabel lblYCoordinate = new JLabel("Y Coordinate:");
			GridBagConstraints gbc_lblYCoordinate = new GridBagConstraints();
			gbc_lblYCoordinate.anchor = GridBagConstraints.EAST;
			gbc_lblYCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYCoordinate.gridx = 1;
			gbc_lblYCoordinate.gridy = 2;
			contentPanel.add(lblYCoordinate, gbc_lblYCoordinate);
		}
		{
			txtYCoordinate = new JTextField();
			GridBagConstraints gbc_txtYCoordinate = new GridBagConstraints();
			gbc_txtYCoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtYCoordinate.gridx = 2;
			gbc_txtYCoordinate.gridy = 2;
			contentPanel.add(txtYCoordinate, gbc_txtYCoordinate);
			txtYCoordinate.setColumns(10);
		}
		{
			JLabel lblContourColor = new JLabel("Contour color:");
			GridBagConstraints gbc_lblContourColor = new GridBagConstraints();
			gbc_lblContourColor.anchor = GridBagConstraints.EAST;
			gbc_lblContourColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblContourColor.gridx = 4;
			gbc_lblContourColor.gridy = 2;
			contentPanel.add(lblContourColor, gbc_lblContourColor);
		}
		{
			btnContourColor = new JButton("");
			GridBagConstraints gbc_btnContourColor = new GridBagConstraints();
			gbc_btnContourColor.fill = GridBagConstraints.BOTH;
			gbc_btnContourColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnContourColor.gridx = 5;
			gbc_btnContourColor.gridy = 2;
			contentPanel.add(btnContourColor, gbc_btnContourColor);
		}
		{
			JLabel lblLength = new JLabel("Radius:");
			GridBagConstraints gbc_lblLength = new GridBagConstraints();
			gbc_lblLength.anchor = GridBagConstraints.EAST;
			gbc_lblLength.insets = new Insets(0, 0, 5, 5);
			gbc_lblLength.gridx = 1;
			gbc_lblLength.gridy = 3;
			contentPanel.add(lblLength, gbc_lblLength);
		}
		{
			txtRadius = new JTextField();
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRadius.insets = new Insets(0, 0, 5, 5);
			gbc_txtRadius.gridx = 2;
			gbc_txtRadius.gridy = 3;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			JLabel lblInsideColor = new JLabel("Inside color:");
			GridBagConstraints gbc_lblInsideColor = new GridBagConstraints();
			gbc_lblInsideColor.anchor = GridBagConstraints.EAST;
			gbc_lblInsideColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblInsideColor.gridx = 4;
			gbc_lblInsideColor.gridy = 3;
			contentPanel.add(lblInsideColor, gbc_lblInsideColor);
		}
		{
			btnInsideColor = new JButton("");
			GridBagConstraints gbc_btnInsideColor = new GridBagConstraints();
			gbc_btnInsideColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnInsideColor.fill = GridBagConstraints.BOTH;
			gbc_btnInsideColor.gridx = 5;
			gbc_btnInsideColor.gridy = 3;
			contentPanel.add(btnInsideColor, gbc_btnInsideColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							x = Integer.parseInt(txtXCoordinate.getText());
							y = Integer.parseInt(txtYCoordinate.getText());
							radius = Integer.parseInt(txtRadius.getText());
							if(x < 0 || y < 0 || radius < 2) {
								JOptionPane.showMessageDialog(null, "Numbers are too small");
							}
							else 
							{
								finished = 1;
								dispose();
							};
						}
						catch (NumberFormatException e1)
						{
							JOptionPane.showMessageDialog(null, "Incorrect number format");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void informationGot(int x, int y, Color contour, Color inside)
	{
		setX(x);
		setY(y);
		setContour(contour);
		setInside(inside);
		
		txtXCoordinate.setText(Integer.toString(x));
		txtYCoordinate.setText(Integer.toString(y));
		btnContourColor.setBackground(contour);
		btnInsideColor.setBackground(inside);
		
		txtXCoordinate.setEditable(false);
		txtYCoordinate.setEditable(false);
		btnContourColor.setEnabled(false);
		btnInsideColor.setEnabled(false);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Color getContour() {
		return contour;
	}

	public void setContour(Color contour) {
		this.contour = contour;
	}

	public Color getInside() {
		return inside;
	}

	public void setInside(Color inside) {
		this.inside = inside;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

}
