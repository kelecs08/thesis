package hu.elte.thesis.view.menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hu.elte.thesis.controller.MainControllerInterface;
import hu.elte.thesis.view.service.PropertyService;

/**
 * The implementation of the Help menu.
 * 
 * @author kelecs08
 */
public class HelpMenu extends JMenu {

	private static final long serialVersionUID = 6667707369562342166L;

	private final MainControllerInterface mainController;
	private PropertyService propertyService;
	private Properties textProperties;

	public HelpMenu(MainControllerInterface mainController) {
		super("Help");
		this.mainController = mainController;
		this.propertyService = new PropertyService();
		this.textProperties = propertyService.loadTextProperties();
	}

	/**
	 * Get the initialized Help menu.
	 * @return
	 * 		this object
	 */
	public HelpMenu getHelpMenu() {
		addGetHelpMenuItem();
		return this;
	}

	private void addGetHelpMenuItem() {
		JMenuItem showHelp = new JMenuItem("Instructions");
		showHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHelpMessageDialog();
			}
		});
		
		JMenuItem credentials = new JMenuItem("Credentials");
		credentials.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showCredentials();				
			}
		});
		
		add(showHelp);
		add(credentials);
	}

	private void showHelpMessageDialog() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(500, 500));
		
		JTextArea aimTextArea = getTextArea(this.textProperties.getProperty("aim"));
		JTextArea howToStepTextArea = getTextArea(this.textProperties.getProperty("step"));
		JTextArea stepOneTextArea = getTextArea(this.textProperties.getProperty("step-two"));
		JTextArea fightTextArea = getTextArea(this.textProperties.getProperty("fight"));
		JTextArea winTextArea = getTextArea(this.textProperties.getProperty("win"));
		JTextArea goodLuckTextArea = getTextArea(this.textProperties.getProperty("good-luck"));

		panel.add(aimTextArea);
		panel.add(howToStepTextArea);
		panel.add(stepOneTextArea);
		panel.add(fightTextArea);
		panel.add(winTextArea);
		panel.add(goodLuckTextArea);

		JOptionPane.showMessageDialog(mainController.getMainWindow(), panel, "Help", JOptionPane.QUESTION_MESSAGE);
	}

	private void showCredentials() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setPreferredSize(new Dimension(250, 100));
		
		JTextArea introductionTextArea = getTextArea(this.textProperties.getProperty("introduction"));
		JTextArea creatorTextArea = getTextArea(this.textProperties.getProperty("creator"));
		
		panel.add(introductionTextArea);
		panel.add(creatorTextArea);
		
		JOptionPane.showMessageDialog(mainController.getMainWindow(), panel, "About the coder", JOptionPane.OK_OPTION);
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
