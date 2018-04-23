package ShowtimeScripts.dead.STGreenDragons.gui;

import ShowtimeScripts.dead.STGreenDragons.main.STGreenDragons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/*
 * Created by JFormDesigner on Fri Mar 02 18:14:23 CST 2018
 */


/**
 * @author unknown
 */
public class STGreenDragonsGUI extends JFrame{
	public STGreenDragonsGUI(){
		initComponents();
	}

	private void startButtonActionPerformed(ActionEvent e){

		if(hidesInputBox.isSelected()) STGreenDragons.lootHides = true;
		if(boneInputBox.isSelected()) STGreenDragons.lootBones = true;
		if(saveSettingsInput.isSelected()) STGreenDragons.saveSettings = true;
		STGreenDragons.foodIDStart = Integer.parseInt(foodIDInput.getText());
		STGreenDragons.foodAmtStart = Integer.parseInt(foodAmtInput.getText());
		STGreenDragons.ringIDStart = Integer.parseInt(ringIDInput.getText());

		STGreenDragons.g.setVisible(false);
		STGreenDragons.g.dispose();

	}

	private void initComponents(){
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - djj js
		label1 = new JLabel();
		label2 = new JLabel();
		textArea1 = new JTextArea();
		textPane1 = new JTextPane();
		hidesInputBox = new JCheckBox();
		boneInputBox = new JCheckBox();
		startButton = new JButton();
		foodIDInput = new JTextField();
		label3 = new JLabel();
		foodAmtInput = new JTextField();
		label4 = new JLabel();
		ringIDInput = new JTextField();
		saveSettingsInput = new JCheckBox();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(null);

		//---- label1 ----
		label1.setText("STGreenDragons");
		label1.setFont(new Font("AppleGothic", Font.PLAIN, 22));
		contentPane.add(label1);
		label1.setBounds(60, 0, 190, 35);

		//---- label2 ----
		label2.setText("Food ID");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(label2);
		label2.setBounds(175, 32, 50, 25);
		contentPane.add(textArea1);
		textArea1.setBounds(new Rectangle(new Point(95, 45), textArea1.getPreferredSize()));
		contentPane.add(textPane1);
		textPane1.setBounds(new Rectangle(new Point(85, 45), textPane1.getPreferredSize()));

		//---- hidesInputBox ----
		hidesInputBox.setText("Loot Hides");
		hidesInputBox.setSelected(true);
		hidesInputBox.setVerticalAlignment(SwingConstants.TOP);
		hidesInputBox.setHorizontalTextPosition(SwingConstants.LEADING);
		contentPane.add(hidesInputBox);
		hidesInputBox.setBounds(10, 35, 130, 20);

		//---- boneInputBox ----
		boneInputBox.setText("Loot Bones");
		boneInputBox.setHorizontalTextPosition(SwingConstants.LEADING);
		boneInputBox.setSelected(true);
		contentPane.add(boneInputBox);
		boneInputBox.setBounds(10, 60, 120, boneInputBox.getPreferredSize().height);

		//---- startButton ----
		startButton.setText("Enjoy!");
		startButton.addActionListener(e -> startButtonActionPerformed(e));
		contentPane.add(startButton);
		startButton.setBounds(40, 125, 225, startButton.getPreferredSize().height);
		contentPane.add(foodIDInput);
		foodIDInput.setBounds(230, 32, 60, foodIDInput.getPreferredSize().height);

		//---- label3 ----
		label3.setText("Food Amt");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(label3);
		label3.setBounds(161, 65, 62, 21);
		contentPane.add(foodAmtInput);
		foodAmtInput.setBounds(230, 65, 60, 24);

		//---- label4 ----
		label4.setText("Ring ID");
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(label4);
		label4.setBounds(163, 98, 60, 21);

		//---- ringIDInput ----
		ringIDInput.setText("-1");
		contentPane.add(ringIDInput);
		ringIDInput.setBounds(230, 98, 60, ringIDInput.getPreferredSize().height);

		//---- saveSettingsInput ----
		saveSettingsInput.setText("Save Settings");
		saveSettingsInput.setHorizontalTextPosition(SwingConstants.LEADING);
		contentPane.add(saveSettingsInput);
		saveSettingsInput.setBounds(10, 100, 120, 20);

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
	private JLabel label1;
	private JLabel label2;
	private JTextArea textArea1;
	private JTextPane textPane1;
	private JCheckBox hidesInputBox;
	private JCheckBox boneInputBox;
	private JButton startButton;
	private JTextField foodIDInput;
	private JLabel label3;
	private JTextField foodAmtInput;
	private JLabel label4;
	private JTextField ringIDInput;
	private JCheckBox saveSettingsInput;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
