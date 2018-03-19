package hu.elte.thesis.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.Blobs;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.view.dto.SimplePosition;
import hu.elte.thesis.view.service.ImageResizingService;
import hu.elte.thesis.view.service.PropertyService;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = -3559703782267201891L;

	private MainController mainController;
	private ImageResizingService imageResizingService;
	private PropertyService propertyService;
	private Properties iconProperties;
	
	private ImageIcon playerOneImage;
	private ImageIcon playerTwoImage;
	
	private JPanel center;
	private JPanel playerOnePanel;
	private JPanel playerTwoPanel;
	
	private JLabel playerOneIconLabel;
	private JLabel playerTwoIconLabel;
	
	private JButton playerOneNameButton;
	private JButton playerTwoNameButton;
	
	private JButton playerOneScoreButton;
	private JButton playerTwoScoreButton;
	
	public GamePanel(MainController mainController) {
		super(new BorderLayout());
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
		this.propertyService = new PropertyService();
		this.iconProperties = propertyService.loadIconProperties();
		
		int playerSize = Integer.parseInt(iconProperties.getProperty("playerSize"));
		this.playerOneImage = imageResizingService.resizeImage(iconProperties.getProperty(Blobs.BLUE.getBlobColorString()), playerSize, playerSize, false);
		this.playerTwoImage = imageResizingService.resizeImage(iconProperties.getProperty(Blobs.RED.getBlobColorString()), playerSize, playerSize, true);
	}
	
	public ImageIcon getPlayerOneImage() { return playerOneImage; }
	public void setPlayerOneImage(ImageIcon playerOneImage) { this.playerOneImage = playerOneImage;	}

	public ImageIcon getPlayerTwoImage() { return playerTwoImage; }
	public void setPlayerTwoImage(ImageIcon playerTwoImage) { this.playerTwoImage = playerTwoImage;	}

	public JLabel getPlayerOneIconLabel() {	return playerOneIconLabel; }
	public void setPlayerOneIconLabel(JLabel playerOneIconLabel) {	this.playerOneIconLabel = playerOneIconLabel; }

	public JLabel getPlayerTwoIconLabel() {	return playerTwoIconLabel; }
	public void setPlayerTwoIconLabel(JLabel playerTwoIconLabel) { this.playerTwoIconLabel = playerTwoIconLabel;}


	public JPanel createInitialTableBoard() {
		center = new JPanel();
		center.setLayout(new GridLayout(this.mainController.getTableSize(), this.mainController.getTableSize()));
		addButtons(center);
	    center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
	    
		playerOnePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(playerOnePanel, BoxLayout.Y_AXIS);
		playerOnePanel.setLayout(boxLayout);
		
		int playerLabelSize = Integer.parseInt(iconProperties.getProperty("playerLabelSize"));
		playerOneIconLabel = new JLabel(imageResizingService.resizeImage(iconProperties.getProperty("blue"), playerLabelSize, playerLabelSize, false));
		playerIconLabelSettings(playerOneIconLabel);
		
		playerOneNameButton = new JButton(mainController.getPlayerOne().getName());
		playerOneNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(center, "New name:\n(Maximum 10 character)", "New name for " + mainController.getPlayerOne().getName(), JOptionPane.QUESTION_MESSAGE);
				if(name != null && !("").equals(name) && name.length() <= 10) {
					playerOneNameButton.setText(name);
					mainController.getPlayerOne().setName(name);
				}
			}
		});
		playerButtonSettings(playerOneNameButton);
		setBGBlackFGWhite(playerOneNameButton);
		
		playerOneScoreButton = new JButton(Integer.toString(mainController.getPlayerOne().getReservedSpots()));
		playerButtonSettings(playerOneScoreButton);
		setBGBlackFGWhite(playerOneScoreButton);

		playerOnePanel.add(playerOneIconLabel);
		playerOnePanel.add(playerOneNameButton);
		playerOnePanel.add(playerOneScoreButton);
	    playerOnePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		playerTwoPanel = new JPanel();
		boxLayout = new BoxLayout(playerTwoPanel, BoxLayout.Y_AXIS);
		playerTwoPanel.setLayout(boxLayout);
		
		playerTwoIconLabel = new JLabel(imageResizingService.resizeImage(iconProperties.getProperty("red"), playerLabelSize, playerLabelSize, true));
		playerIconLabelSettings(playerTwoIconLabel);
		
		playerTwoNameButton = new JButton(mainController.getPlayerTwo().getName());
		playerTwoNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(center, "New name:\n(Maximum 10 character)", "New name for " + mainController.getPlayerTwo().getName(), JOptionPane.QUESTION_MESSAGE);
				if(name != null && !("").equals(name)) {
					playerTwoNameButton.setText(name);
					mainController.getPlayerTwo().setName(name);
				}
			}
		});
		playerButtonSettings(playerTwoNameButton);
		setBGWhiteFGBlack(playerTwoNameButton);

		playerTwoScoreButton = new JButton(Integer.toString(mainController.getPlayerTwo().getReservedSpots()));
		playerButtonSettings(playerTwoScoreButton);
		setBGWhiteFGBlack(playerTwoScoreButton);
		
		playerTwoPanel.add(playerTwoIconLabel);
		playerTwoPanel.add(playerTwoNameButton);
		playerTwoPanel.add(playerTwoScoreButton);
	    playerTwoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		add(center, BorderLayout.CENTER);
		add(playerOnePanel, BorderLayout.LINE_START);
		add(playerTwoPanel, BorderLayout.LINE_END);
		
		return this;
	}

	private void setBGBlackFGWhite(JButton button) {
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
	}
	
	private void setBGWhiteFGBlack(JButton button) {
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
	}

	private void playerButtonSettings(JButton playerNameButton) {
		playerNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerNameButton.setFont(new Font("Times new roman", Font.BOLD, 18));
		playerNameButton.setMaximumSize(new Dimension(150, 35));
		playerNameButton.setPreferredSize(new Dimension(150, 35));
	}

	private void playerIconLabelSettings(JLabel playerIconLabel) {
		playerIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerIconLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
	}
	
	public void updateCenter() {
		center.removeAll();
		center.revalidate();
		center.setLayout(new GridLayout(this.mainController.getTableSize(), this.mainController.getTableSize()));
		addButtons(center);
	}

	private void addButtons(JPanel center) {
		for(int i = 2; i < this.mainController.getTableSize() + 2; i++) {
			for(int j = 2; j < this.mainController.getTableSize() + 2; j++) {
				addButton(center, i, j);
			}
		}
	}

	private void addButton(JPanel center, int i, int j) {
		JButton button = new JButton();
		setInitialButtonAppearance(button, i, j);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mainController.isClickOne(i, j)) {
					button.setBackground(Color.BLACK);
					renderFirstLevelFields();
					renderSecondLevelFields();
				}
				else if(mainController.isClickingTheClickedPosition(i, j))
					updateFields();
				else if(!mainController.isClickOne(i, j))
					if(mainController.step(i, j)) {
						updateFields();
						Position position = mainController.stepWithComputer();
						if(position != null) {
							System.out.println(position.toString());
							updateFields();
							renderField(position.getRow()-2, position.getColumn()-2, Color.BLACK);
						}
					}
			}
		});
		center.add(button);
	}

	private void renderFirstLevelFields() {
		renderFields(mainController.getPositionsToBeRenderedFirstLevel(), Color.DARK_GRAY);
	}

	private void renderSecondLevelFields() {
		renderFields(mainController.getPositionsToBeRenderedSecondLevel(), Color.GRAY);
	}

	private void renderFields(List<SimplePosition> positionsToBeRendered, Color color) {
		for(SimplePosition sp: positionsToBeRendered) {
			Component component = center.getComponent(sp.getRow() * mainController.getTableSize() + sp.getColumn());
			JButton field = (JButton) component;
			field.setBackground(color);
		}
	}
	
	public void renderField(int row, int column, Color color) {
		Component component = center.getComponent(row * mainController.getTableSize() + column);
		JButton field = (JButton) component;
		field.setBackground(color);
	}
	
	private void setInitialButtonAppearance(JButton button, int i, int j) {
		button.setPreferredSize(new Dimension(70, 70));
		button.setBackground(Color.LIGHT_GRAY);
		button.setFont(new Font("Times New Roman", Font.BOLD, 30));
		if( (i == 2 && j == 2) || (i == this.mainController.getTableSize() + 1 && j == 2) ) button.setIcon(playerOneImage);
		if( (i == 2 && j == this.mainController.getTableSize() + 1) || (i == this.mainController.getTableSize() + 1 && j == this.mainController.getTableSize() + 1) ) button.setIcon(playerTwoImage);
	}
	
	public void updateFields() {
		if(this.mainController.getActualPlayer().getName().equals(playerOneNameButton.getText())) {
			setBGBlackFGWhite(playerOneNameButton);
			setBGBlackFGWhite(playerOneScoreButton);
			playerOneScoreButton.setText(Integer.toString(mainController.getPlayerOne().getReservedSpots()));

			setBGWhiteFGBlack(playerTwoNameButton);
			setBGWhiteFGBlack(playerTwoScoreButton);
			playerTwoScoreButton.setText(Integer.toString(mainController.getPlayerTwo().getReservedSpots()));
		} else {
			setBGBlackFGWhite(playerTwoNameButton);
			setBGBlackFGWhite(playerTwoScoreButton);
			playerOneScoreButton.setText(Integer.toString(mainController.getPlayerOne().getReservedSpots()));

			setBGWhiteFGBlack(playerOneNameButton);
			setBGWhiteFGBlack(playerOneScoreButton);
			playerTwoScoreButton.setText(Integer.toString(mainController.getPlayerTwo().getReservedSpots()));	
		}
		for(int i = 0; i < this.mainController.getTableSize(); i++) {
			for(int j = 0; j < this.mainController.getTableSize(); j++) {
				Component component = center.getComponent(i * this.mainController.getTableSize() + j);
				JButton field = (JButton) component;
				if(this.mainController.isPlayerOneField(i, j)) field.setIcon(playerOneImage);
				else if(this.mainController.isPlayerTwoField(i, j)) field.setIcon(playerTwoImage);
				else field.setIcon(null);
				field.setBackground(Color.LIGHT_GRAY);
			}
		}
		checkWinning();
	}

	private void checkWinning() {
		if(this.mainController.isDraw()) {
			JOptionPane.showMessageDialog(this, "Game over!\nThe game ended in a draw.");
			if(this.mainController.getGameType().equals(GameType.ONE_PLAYER))
				this.mainController.startNewGame(true);
			else
				this.mainController.startNewGame(false);
		}
		Player winner = this.mainController.getWinner();
		if(winner != null) {
			JOptionPane.showMessageDialog(this, "Game over!\nThe winner is player " + winner.getName() + " !! :D");
			if(this.mainController.getGameType().equals(GameType.ONE_PLAYER))
				this.mainController.startNewGame(true);
			else
				this.mainController.startNewGame(false);
		}
	}

	public void updatePlayerOneLabel(ImageIcon blobPlayerLabel) { playerOneIconLabel.setIcon(blobPlayerLabel); }	
	public void updatePlayerTwoLabel(ImageIcon blobPlayerLabel) { playerTwoIconLabel.setIcon(blobPlayerLabel); }	
}
