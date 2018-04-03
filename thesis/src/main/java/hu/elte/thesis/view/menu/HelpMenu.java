package hu.elte.thesis.view.menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.view.service.ImageResizingService;
import hu.elte.thesis.view.service.PropertyService;
import javax.swing.JMenu;

import hu.elte.thesis.controller.MainController;

public class HelpMenu extends JMenu {

	private static final long serialVersionUID = 6667707369562342166L;

	private MainController mainController;

	private ImageResizingService imageResizingService;
	private PropertyService propertyService;
	private Properties properties;
	
	private final String introductionText = "This game is a custom implementation of the game called Blob wars.\nThis is a short introduction to help you understand how this game works.";
	private final String stepOne = "You can step if you are the actual player - the labels which show your name and the number of fields taken by you have black backgrounds - you can step by clicking on one of your blobs, then you will have to choose a field to step toby clicking onto it.";
	private final String stepTwo = "There are two ways you can step:\n1. You can multiply your blobs by choosing a field that is a direct neigbour of your clicked figure.";
	private final String stepThree = "2. Or you can transport your blob to a field that is two steps away from your position, not changing the number of blobs you own.";
	private final String fight = "If you step to a position which direct neighbours are the enemy's puppets, you will overtake them. So your fields will increase.";
	private final String win = "To win the game, you have to own the most fields on the board or corner your enemy, so they cannot move anymore with either of their blobs.\nThe game can end in a draw too as there are even fields on the board.";
	private final String goodLuck = "Good luck with the game, hope you enjoy it - and win! :D";

	public HelpMenu(MainController mainController) {
		super("Help");
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
		this.propertyService = new PropertyService();
		this.properties = this.propertyService.loadPictureProperties();
	}

	public HelpMenu getHelpMenu() {
		addGetHelpMenuItem();
		return this;
	}

	private void addGetHelpMenuItem() {
		JMenuItem showHelp = new JMenuItem("Get help...");

		showHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHelpMessageDialog();
			}
		});

		add(showHelp);
	}

	private void showHelpMessageDialog() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(500, 500));
		
		JTextArea textAreaOne = getTextArea(introductionText);
		JTextArea textAreaTwo = getTextArea(stepOne);
		JLabel pictureOne = new JLabel(imageResizingService.resizeImage("images/pictures/one.png", 389, 276, false));
		JTextArea textAreaThree = new JTextArea(stepTwo);
		JLabel pictureTwo = new JLabel(imageResizingService.resizeImage("images/pictures/two.png", 138, 66, false));

		panel.add(textAreaOne);
		panel.add(textAreaTwo);
		panel.add(pictureOne);
		panel.add(textAreaTwo);
		panel.add(pictureTwo);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setMaximumSize(new Dimension(500, 500));
		scrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		JOptionPane.showMessageDialog(mainController.getMainWindow(), scrollPane, "Help", JOptionPane.QUESTION_MESSAGE);

	}

	private JTextArea getTextArea(String text) {
		JTextArea textArea = new JTextArea(text);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Times new roman", Font.PLAIN, 15));
		return textArea;
	}
}
