/*
 * Created by JFormDesigner on Fri Mar 02 22:24:54 CST 2018
 */

package ShowtimeScripts.dead.STGreenDragons.gui;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author djj js
 */
public class STGDSettingsGUI extends JFrame{
	public STGDSettingsGUI(){
		initComponents();
	}

	private void importFromFileActionPerformed(ActionEvent e){
		STGreenDragons.readFromFile = true;
		STGreenDragons.g2.setVisible(false);
		STGreenDragons.g2.dispose();
	}

	private void button1ActionPerformed(ActionEvent e){
		STGreenDragons.readFromFile = false;
		STGreenDragons.g2.setVisible(false);
		STGreenDragons.g2.dispose();
	}

	private void initComponents(){
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - djj js
		importFromFile = new JButton();
		button1 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- importFromFile ----
		importFromFile.setText("Default Settings");
		importFromFile.addActionListener(e -> importFromFileActionPerformed(e));
		contentPane.add(importFromFile);
		importFromFile.setBounds(10, 85, 255, 42);

		//---- button1 ----
		button1.setText("Edit Settings");
		button1.addActionListener(e -> button1ActionPerformed(e));
		contentPane.add(button1);
		button1.setBounds(15, 10, 130, 25);

		{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++){
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - djj js
	private JButton importFromFile;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
