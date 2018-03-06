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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hu.elte.thesis.controller.MainController;
import hu.elte.thesis.model.GameType;
import hu.elte.thesis.model.Player;
import hu.elte.thesis.model.Position;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = -3559703782267201891L;

	private MainController mainController;
	private GameType gameType;
	private int tableSize = 6;
	
	private JLabel actualPlayerLabel;
	
	public GamePanel(MainController mainController) {
		super(new BorderLayout());
		this.mainController = mainController;
	}

	public JPanel createInitialTableBoard() {
		this.actualPlayerLabel = new JLabel();
		this.actualPlayerLabel.setText("Actual player: " /*+ this.controller.getPlayer1().getName()*/);
		this.actualPlayerLabel.setFont(new Font("Times New Roman", Font.ITALIC, 25));
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(this.tableSize, this.tableSize));
		addButtons(center);
		
		add(actualPlayerLabel, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		
		return this;
	}

	private void addButtons(JPanel center) {
		for(int i = 2; i < this.tableSize + 2; i++) {
			for(int j = 2; j < this.tableSize + 2; j++) {
				addButton(center, i, j);
			}
		}
	}

	private void addButton(JPanel center, int i, int j) {
		JButton button = new JButton();
		setInitialButtonAppearance(button, i, j);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(mainController.step1(i, j)) {
					renderCleavageFields();
				}
			/*	if(controller.getFrom() == null) {
					if(engine.step1(i, j)) { button.setText(""); }
				}
				else {
					Player p = engine.step2(i, j);
					if(p != null) {
						button.setText(p.name());
						Player winner = engine.winner();
						if(winner != null) { getWinner(winner); }
						if(p == Player.M) label.setText("Actual player: B");
						else label.setText("Actual player: M");
					}
				}*/
			}

		});
		center.add(button);
	}

	private void renderCleavageFields() {
		List<Position> positions = this.mainController.getCleavageFields();
		for(Position p : positions) {
			Component component = this.getComponent(p.getRow() * (this.tableSize + 4) + p.getColumn());
			JButton button = (JButton) component;
			button.setBackground(Color.GRAY);
		}
	}
	
	private void setInitialButtonAppearance(JButton button, int i, int j) {
		button.setPreferredSize(new Dimension(70, 70));
		button.setBackground(Color.LIGHT_GRAY);
		button.setFont(new Font("Times New Roman", Font.BOLD, 30));
		if( (i == 2 && j == 2) || (i == this.tableSize + 1 && j == 2) ) button.setBackground(Color.BLACK);
		if( (i == 2 && j == this.tableSize + 1) || (i == this.tableSize + 1 && j == this.tableSize + 1) ) button.setBackground(Color.RED);
	}

	private void getWinner(Player winner) {
		JOptionPane.showMessageDialog(this, "Game over!\nThe winner is player " + winner.getName() + " !! :D");
		//newGame();
	}

/*	private void newGame() {
		GameWindow newWindow = new GameWindow(this.gameType, this.tableSize);
		newWindow.setVisible(true);
		disposeWindow();
	}*/
	
	
	
	
}
