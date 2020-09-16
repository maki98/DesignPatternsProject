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
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DlgForLine extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtXCoordinateStart;
	private JTextField txtYCoordinateStart;
	private JTextField txtXCoordinateEnd;
	private JTextField txtYCoordinateEnd;
	
	private int xStart, yStart, xEnd, yEnd;
	private Color contour = Color.BLACK;
	private JButton btnContourColor;
	
	private int finished = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgForLine dialog = new DlgForLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgForLine() {
		setResizable(false);
		setTitle("Line");
		setModal(true);
		setBounds(100, 100, 574, 181);
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
			JLabel lblXCoordinateStart = new JLabel("START: X coordinate:");
			GridBagConstraints gbc_lblXCoordinateStart = new GridBagConstraints();
			gbc_lblXCoordinateStart.anchor = GridBagConstraints.EAST;
			gbc_lblXCoordinateStart.insets = new Insets(0, 0, 5, 5);
			gbc_lblXCoordinateStart.gridx = 0;
			gbc_lblXCoordinateStart.gridy = 1;
			contentPanel.add(lblXCoordinateStart, gbc_lblXCoordinateStart);
		}
		{
			txtXCoordinateStart = new JTextField();
			GridBagConstraints gbc_txtXCoordinateStart = new GridBagConstraints();
			gbc_txtXCoordinateStart.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXCoordinateStart.insets = new Insets(0, 0, 5, 5);
			gbc_txtXCoordinateStart.gridx = 1;
			gbc_txtXCoordinateStart.gridy = 1;
			contentPanel.add(txtXCoordinateStart, gbc_txtXCoordinateStart);
			txtXCoordinateStart.setColumns(10);
		}
		{
			JLabel lblYCoordinateStart = new JLabel("START: Y coordinate:");
			GridBagConstraints gbc_lblYCoordinateStart = new GridBagConstraints();
			gbc_lblYCoordinateStart.anchor = GridBagConstraints.EAST;
			gbc_lblYCoordinateStart.insets = new Insets(0, 0, 5, 5);
			gbc_lblYCoordinateStart.gridx = 0;
			gbc_lblYCoordinateStart.gridy = 2;
			contentPanel.add(lblYCoordinateStart, gbc_lblYCoordinateStart);
		}
		{
			txtYCoordinateStart = new JTextField();
			GridBagConstraints gbc_txtYCoordinateStart = new GridBagConstraints();
			gbc_txtYCoordinateStart.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYCoordinateStart.insets = new Insets(0, 0, 5, 5);
			gbc_txtYCoordinateStart.gridx = 1;
			gbc_txtYCoordinateStart.gridy = 2;
			contentPanel.add(txtYCoordinateStart, gbc_txtYCoordinateStart);
			txtYCoordinateStart.setColumns(10);
		}
		
		{
			JLabel lblXCoordinateEnd = new JLabel("END: X coordinate:");
			GridBagConstraints gbc_lblXCoordinateEnd = new GridBagConstraints();
			gbc_lblXCoordinateEnd.anchor = GridBagConstraints.EAST;
			gbc_lblXCoordinateEnd.insets = new Insets(0, 0, 5, 5);
			gbc_lblXCoordinateEnd.gridx = 3;
			gbc_lblXCoordinateEnd.gridy = 1;
			contentPanel.add(lblXCoordinateEnd, gbc_lblXCoordinateEnd);
		}
		{
			txtXCoordinateEnd = new JTextField();
			GridBagConstraints gbc_txtXCoordinateEnd = new GridBagConstraints();
			gbc_txtXCoordinateEnd.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXCoordinateEnd.insets = new Insets(0, 0, 5, 5);
			gbc_txtXCoordinateEnd.gridx = 4;
			gbc_txtXCoordinateEnd.gridy = 1;
			contentPanel.add(txtXCoordinateEnd, gbc_txtXCoordinateEnd);
			txtXCoordinateStart.setColumns(10);
		}
		{
			JLabel lblYCoordinateEnd = new JLabel("END: Y coordinate:");
			GridBagConstraints gbc_lblYCoordinateEnd = new GridBagConstraints();
			gbc_lblYCoordinateEnd.anchor = GridBagConstraints.EAST;
			gbc_lblYCoordinateEnd.insets = new Insets(0, 0, 5, 5);
			gbc_lblYCoordinateEnd.gridx = 3;
			gbc_lblYCoordinateEnd.gridy = 2;
			contentPanel.add(lblYCoordinateEnd, gbc_lblYCoordinateEnd);
		}
		{
			txtYCoordinateEnd = new JTextField();
			GridBagConstraints gbc_txtYCoordinateEnd = new GridBagConstraints();
			gbc_txtYCoordinateEnd.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYCoordinateEnd.insets = new Insets(0, 0, 5, 5);
			gbc_txtYCoordinateEnd.gridx = 4;
			gbc_txtYCoordinateEnd.gridy = 2;
			contentPanel.add(txtYCoordinateEnd, gbc_txtYCoordinateEnd);
			txtYCoordinateStart.setColumns(10);
		}
		{
			JLabel lblContourColor = new JLabel("Color:");
			GridBagConstraints gbc_lblContourColor = new GridBagConstraints();
			gbc_lblContourColor.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblContourColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblContourColor.gridx = 3;
			gbc_lblContourColor.gridy = 3;
			contentPanel.add(lblContourColor, gbc_lblContourColor);
		}
		{
			btnContourColor = new JButton("");
			btnContourColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Color temp = JColorChooser.showDialog(null, "Choose color:", contour);
					if(temp != null)
					{
						contour = temp;
						btnContourColor.setBackground(contour);
					}				
				}
			});
			GridBagConstraints gbc_btnContourColor = new GridBagConstraints();
			gbc_btnContourColor.fill = GridBagConstraints.BOTH;
			gbc_btnContourColor.insets = new Insets(0, 0, 5, 5);
			gbc_btnContourColor.gridx = 4;
			gbc_btnContourColor.gridy = 3;
			contentPanel.add(btnContourColor, gbc_btnContourColor);
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
							xStart = Integer.parseInt(txtXCoordinateStart.getText());
							yStart = Integer.parseInt(txtYCoordinateStart.getText());
							xEnd = Integer.parseInt(txtXCoordinateEnd.getText());
							yEnd = Integer.parseInt(txtYCoordinateEnd.getText());
							if(xStart < 0 || yStart < 0 || xEnd < 0 || yEnd < 0) {
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
	
	public void update(int xStart, int yStart, int xEnd, int yEnd, Color contour) 
	{
		setxStart(xStart);
		setyStart(yStart);
		setxEnd(xEnd);
		setyEnd(yEnd);
		setContour(contour);
		
		txtXCoordinateStart.setText(Integer.toString(xStart));
		txtYCoordinateStart.setText(Integer.toString(yStart));
		txtXCoordinateEnd.setText(Integer.toString(xEnd));
		txtYCoordinateEnd.setText(Integer.toString(yEnd));
		btnContourColor.setBackground(contour);
	}



	public int getxStart() {
		return xStart;
	}

	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	public int getyStart() {
		return yStart;
	}

	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	public int getxEnd() {
		return xEnd;
	}

	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	public int getyEnd() {
		return yEnd;
	}

	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	public Color getContour() {
		return contour;
	}

	public void setContour(Color contour) {
		this.contour = contour;
	}

	public int getFinished() {
		return finished;
	}
	
	public void setFinished(int finished) {
		this.finished = finished;
	}
}