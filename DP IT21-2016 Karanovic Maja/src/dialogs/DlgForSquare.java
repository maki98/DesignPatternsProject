package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class DlgForSquare extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoordinate;
	private JTextField txtYCoordinate;
	private JTextField txtLength;
	
	private int x, y, length;
	private Color contour = Color.BLACK;
	private Color inside = Color.WHITE;
	private JButton btnContourColor, btnInsideColor;
	
	private int finished = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgForSquare dialog = new DlgForSquare();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgForSquare() {
		setModal(true);
		setBounds(100, 100, 468, 181);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{102, 103, 40, 87, 94, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{18, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblXCoordinate = new JLabel("X coordinate:");
			GridBagConstraints gbc_lblXCoordinate = new GridBagConstraints();
			gbc_lblXCoordinate.anchor = GridBagConstraints.EAST;
			gbc_lblXCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXCoordinate.gridx = 0;
			gbc_lblXCoordinate.gridy = 1;
			contentPanel.add(lblXCoordinate, gbc_lblXCoordinate);
		}
		{
			txtXCoordinate = new JTextField();
			GridBagConstraints gbc_txtXCoordinate = new GridBagConstraints();
			gbc_txtXCoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtXCoordinate.gridx = 1;
			gbc_txtXCoordinate.gridy = 1;
			contentPanel.add(txtXCoordinate, gbc_txtXCoordinate);
			txtXCoordinate.setColumns(10);
		}
		{
			JLabel lblYCoordinate = new JLabel("Y coordinate:");
			GridBagConstraints gbc_lblYCoordinate = new GridBagConstraints();
			gbc_lblYCoordinate.anchor = GridBagConstraints.EAST;
			gbc_lblYCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYCoordinate.gridx = 0;
			gbc_lblYCoordinate.gridy = 2;
			contentPanel.add(lblYCoordinate, gbc_lblYCoordinate);
		}
		{
			txtYCoordinate = new JTextField();
			GridBagConstraints gbc_txtYCoordinate = new GridBagConstraints();
			gbc_txtYCoordinate.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_txtYCoordinate.gridx = 1;
			gbc_txtYCoordinate.gridy = 2;
			contentPanel.add(txtYCoordinate, gbc_txtYCoordinate);
			txtYCoordinate.setColumns(10);
		}
		{
			JLabel lblContourColor = new JLabel("Contour color:");
			GridBagConstraints gbc_lblContourColor = new GridBagConstraints();
			gbc_lblContourColor.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblContourColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblContourColor.gridx = 3;
			gbc_lblContourColor.gridy = 2;
			contentPanel.add(lblContourColor, gbc_lblContourColor);
		}
		{
			btnContourColor = new JButton("");
			btnContourColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			GridBagConstraints gbc_btnContourColor = new GridBagConstraints();
			gbc_btnContourColor.fill = GridBagConstraints.BOTH;
			gbc_btnContourColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnContourColor.gridx = 4;
			gbc_btnContourColor.gridy = 2;
			contentPanel.add(btnContourColor, gbc_btnContourColor);
		}
		{
			JLabel lblLength = new JLabel("Length:");
			GridBagConstraints gbc_lblLength = new GridBagConstraints();
			gbc_lblLength.anchor = GridBagConstraints.EAST;
			gbc_lblLength.insets = new Insets(0, 0, 5, 5);
			gbc_lblLength.gridx = 0;
			gbc_lblLength.gridy = 3;
			contentPanel.add(lblLength, gbc_lblLength);
		}
		{
			txtLength = new JTextField();
			GridBagConstraints gbc_txtLength = new GridBagConstraints();
			gbc_txtLength.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtLength.insets = new Insets(0, 0, 5, 5);
			gbc_txtLength.gridx = 1;
			gbc_txtLength.gridy = 3;
			contentPanel.add(txtLength, gbc_txtLength);
			txtLength.setColumns(10);
		}
		{
			JLabel lblInsideColor = new JLabel("Inside color:");
			GridBagConstraints gbc_lblInsideColor = new GridBagConstraints();
			gbc_lblInsideColor.anchor = GridBagConstraints.EAST;
			gbc_lblInsideColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblInsideColor.gridx = 3;
			gbc_lblInsideColor.gridy = 3;
			contentPanel.add(lblInsideColor, gbc_lblInsideColor);
		}
		{
			btnInsideColor = new JButton("");
			btnInsideColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			GridBagConstraints gbc_btnInsideColor = new GridBagConstraints();
			gbc_btnInsideColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnInsideColor.fill = GridBagConstraints.BOTH;
			gbc_btnInsideColor.gridx = 4;
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
							length = Integer.parseInt(txtLength.getText());
							if(x < 0 || y < 0 || length < 4) {
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
					public void actionPerformed(ActionEvent arg0) {
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
		System.out.println("ovde sam");
		
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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
