package files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import geometry.Shape;

public class Drawing implements Files {
	
	private ArrayList<Shape> listOfShapes;

	public Drawing(ArrayList<Shape> listOfShapes) {
		this.listOfShapes = listOfShapes;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		JFileChooser saveDrawing = new JFileChooser("C:\\Users\\mkaranovic\\Desktop");
		saveDrawing.setDialogTitle("Choose destination");
		if (saveDrawing.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
			File drawing = new File(saveDrawing.getSelectedFile().getAbsolutePath());

			if (drawing.exists()) { 
				JOptionPane.showMessageDialog(null, "Name must be unique!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				FileOutputStream fos;
				ObjectOutputStream oos;
				try { 
					fos = new FileOutputStream(drawing + ".ser");
					oos = new ObjectOutputStream(fos);

					oos.writeObject(listOfShapes);
					oos.close();
					fos.close();

					JOptionPane.showMessageDialog(null, "Successfully saved", "Saving complete",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void open(File file) {
		// TODO Auto-generated method stub
		
	}

}
