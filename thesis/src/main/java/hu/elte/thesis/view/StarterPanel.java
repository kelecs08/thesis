package hu.elte.thesis.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import hu.elte.thesis.controller.MainController;

public class StarterPanel extends JPanel {

	private static final long serialVersionUID = 5637766498281419109L;

	private MainController mainController;
	
	public StarterPanel(MainController mainController) {
		super();
		this.mainController = mainController;
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
	}
	
	public StarterPanel getStarterPanel() {
//		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game settings"));
				
		createButtons();
		
		return this;
	}

	private void createButtons() {
		JButton small = createButton(4);
		JButton medium = createButton(6);
		JButton large = createButton(8);
		add(Box.createRigidArea(new Dimension(0, 300)));
		add(small);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(medium);
		add(Box.createRigidArea(new Dimension(0, 40)));
		add(large);
		add(Box.createRigidArea(new Dimension(0, 40)));
	}

	private JButton createButton(int tableSize) {
		JButton button = new JButton(tableSize + " x " + tableSize);
		setButtonAppearance(button);
		button.addActionListener(getActionListener(tableSize));
		return button;
	}

	private void setButtonAppearance(JButton button) {
		button.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(150, 50));
		button.setFont(new Font("Times new roman", Font.BOLD, 25));
	}

	private ActionListener getActionListener(int tableSize) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("in action listener before");
				mainController.setTableSize(tableSize);
				System.out.println("in action listener after");
				
			}
		};
	}
}
