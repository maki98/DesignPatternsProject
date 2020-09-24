package files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controller.DrawingController;
import geometry.DrawingModel;
import view.DrawingFrame;

public class Log implements Files {
	
	private DefaultListModel<String> dlm;  

	public Log(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}
	
	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		JFileChooser saveDrawing = new JFileChooser("C:\\Users\\mkaranovic\\Desktop");
		saveDrawing.setDialogTitle("Choose destination");
		if (saveDrawing.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File drawing = new File(saveDrawing.getSelectedFile().getAbsolutePath() + "_log.txt");

			if (drawing.exists()) {
				JOptionPane.showMessageDialog(null, "Name must be unique!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					PrintWriter pw = new PrintWriter(drawing);
					for (int i = 0; i < dlm.size(); i++) {
						pw.println(dlm.get(i));
					}
					pw.close();
					JOptionPane.showMessageDialog(null, "Successfully saved", "Saving complete",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
