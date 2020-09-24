package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import controller.DrawingController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgForLogs extends JDialog {
	
	private static final long serialVersionUID = 1L;

	public DlgForLogs(DrawingController controller, BufferedReader bf) {

		setSize(212, 74);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Load");

		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNextCmd = new JButton("Next command");
		btnNextCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String line;
				try {
					if ((line = bf.readLine()) != null) {  
						controller.loadNext(line);
					} else {
						dispose();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		mainPanel.add(btnNextCmd);
	}
}