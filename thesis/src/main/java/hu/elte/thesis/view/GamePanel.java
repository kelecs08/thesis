package hu.elte.thesis.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;
import hu.elte.thesis.view.dto.SimplePosition;
import hu.elte.thesis.view.service.ImageResizingService;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = -3559703782267201891L;

	private MainController mainController;
	private ImageResizingService imageResizingService;
	
	private ImageIcon playerOneImage;
	private ImageIcon playerTwoImage;
	
	private JLabel actualPlayerLabel;
	private JPanel center;
	private JPanel playerOne;
	private JPanel playerTwo;
	
	public GamePanel(MainController mainController) {
		super(new BorderLayout());
		this.mainController = mainController;
		this.imageResizingService = new ImageResizingService();
		this.playerOneImage = imageResizingService.resizeImage("images/players/blue.png", 50, 50);
		this.playerTwoImage = imageResizingService.resizeImage("images/players/red.png", 50, 50);
	}

	public JPanel createInitialTableBoard() {
		this.actualPlayerLabel = new JLabel();
		this.actualPlayerLabel.setText("Actual player: " + this.mainController.getActualPlayer().getName());
		this.actualPlayerLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		
		center = new JPanel();
		center.setLayout(new GridLayout(this.mainController.getTableSize(), this.mainController.getTableSize()));
		addButtons(center);
		
		playerOne = new JPanel();
		BoxLayout boxLayout = new BoxLayout(playerOne, BoxLayout.Y_AXIS);
		playerOne.setLayout(boxLayout);
		playerOne.add(Box.createRigidArea(new Dimension(0, 40)));
		JLabel playerOneLabel = new JLabel(imageResizingService.resizeImage("images/players/blue.png", 80, 80));
		playerOneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerOne.add(playerOneLabel);
		playerOne.add(new JLabel(mainController.getPlayerOne().getName()));
		
		playerTwo = new JPanel();
		boxLayout = new BoxLayout(playerTwo, BoxLayout.Y_AXIS);
		playerTwo.setLayout(boxLayout);
		playerTwo.add(Box.createRigidArea(new Dimension(0, 40)));
		playerTwo.add(new JLabel(imageResizingService.resizeImage("images/players/red.png", 80, 80)));
		playerTwo.add(new JLabel(mainController.getPlayerTwo().getName()));
		
		add(actualPlayerLabel, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(playerOne, BorderLayout.LINE_START);
		add(playerTwo, BorderLayout.LINE_END);
		
		return this;
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
					System.out.println("In isClickOne function");
					renderFirstLevelFields();
					renderSecondLevelFields();
				}
				else if(mainController.isClickingTheClickedPosition(i, j))
					updateFields();
				else if(!mainController.isClickOne(i, j))
					if(mainController.step(i, j)) {
						updateFields();
						mainController.stepWithComputer();
						updateFields();						
						System.out.println("In step function");
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
	
	private void setInitialButtonAppearance(JButton button, int i, int j) {
		button.setPreferredSize(new Dimension(70, 70));
		button.setBackground(Color.LIGHT_GRAY);
		button.setFont(new Font("Times New Roman", Font.BOLD, 30));
		if( (i == 2 && j == 2) || (i == this.mainController.getTableSize() + 1 && j == 2) ) button.setIcon(playerOneImage);
		if( (i == 2 && j == this.mainController.getTableSize() + 1) || (i == this.mainController.getTableSize() + 1 && j == this.mainController.getTableSize() + 1) ) button.setIcon(playerTwoImage);
	}
	
	public void updateFields() {
		this.actualPlayerLabel.setText("Actual player: " + this.mainController.getActualPlayer().getName());
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
			this.mainController.startNewGame();
		}
		Player winner = this.mainController.getWinner();
		if(winner != null) {
			JOptionPane.showMessageDialog(this, "Game over!\nThe winner is player " + winner.getName() + " !! :D");
			this.mainController.startNewGame();
		}
	}	
	
}
