// Uses AWT's Layout Managers
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
// Uses Swing's Container/Components
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Sudoku game. To solve the number puzzle, each row, each column, and each
 * of the nine 3Ã—3 sub-grids shall contain all of the digits from 1 to 9
 */
public class View extends JFrame {
	// Name-constants for the game properties
	public static final int GRID_SIZE = 9; // Size of the board
	public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
	public static JButton btnSolve, btnRefresh, btnCheck;
	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60; // Cell width/height in pixels
	public static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
	// Board width/height in pixels
	public static final Color OPEN_CELL_BGCOLOR = Color.WHITE;
	public static final Color Green_color = Color.GREEN;
	public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0); // RGB
	public static final Color OPEN_CELL_TEXT_NO = Color.RED;
	public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
	public static final Color CLOSED_CELL_TEXT = Color.BLACK;
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

	// The game board composes of 9x9 JTextFields,
	// each containing String "1" to "9", or empty String
	private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
//	private Control con = new Control();
	private int[][] puzzle;
	private JPanel menu;
	private int[][] masks;
	public static JLabel notice, contentNotice;

	private boolean[][] check = new boolean[9][9];

	public View() {
		// TODO Auto-generated constructor stub
		Gui();
	}

	public void Gui() {

		setLayout(new BorderLayout());

		add(viewNumber(), BorderLayout.CENTER);
		add(buttonPanel(), BorderLayout.EAST);
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Sudoku");
		setVisible(true);
	}

	public JPanel viewNumber() {
		JPanel cp = new JPanel();
		cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // 9x9 GridLayout

		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				tfCells[row][col] = new JTextField();
				cp.add(tfCells[row][col]);

				Font fo = new Font("Serif", Font.BOLD, 25);
				tfCells[row][col].setText("");
				tfCells[row][col].setForeground(Color.BLUE);

				tfCells[row][col].setEditable(true);
				tfCells[row][col].setFont(fo);

				tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

				tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
				tfCells[row][col].setFont(FONT_NUMBERS);
				tfCells[row][col].addKeyListener(new KeyAdapter() {
					public void keyTyped(KeyEvent e) {
						char caracter = e.getKeyChar();
						if (((caracter < '0') || (caracter > '9'))) {
							e.consume();
						}
					}
				});
			}
		}

		cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		return cp;
	}

	public JPanel buttonPanel() {
		Font fo1 = new Font("Serif", Font.BOLD, 15);
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(6, 1));
		btnCheck = new JButton("Check");

		btnPanel.add(btnCheck);
		btnCheck.setFont(new Font("Arial", Font.PLAIN, 20));
//		btnCheck.addActionListener(new ActionCheck());

		btnSolve = new JButton("Solve");
		btnPanel.add(btnSolve);
		btnSolve.setFont(new Font("Arial", Font.PLAIN, 20));
//		btnSolve.addActionListener(new ActionSolve());

		btnRefresh = new JButton("Refresh");
		btnPanel.add(btnRefresh);
		btnRefresh.setFont(new Font("Arial", Font.PLAIN, 20));
//		btnRefresh.addActionListener(new ActionRefresh());

		JButton btnExit = new JButton("Exit");

		btnExit.setFont(new Font("Arial", Font.PLAIN, 20));
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnPanel.add(btnExit);
		notice = new JLabel("THÔNG BÁO:");
		contentNotice = new JLabel();
		contentNotice.setFont(fo1);
		contentNotice.setForeground(Color.RED);
		btnPanel.add(notice);
		btnPanel.add(contentNotice);

		return btnPanel;
	}

	public int[][] getTxt() {
		setNoticeHanle();
		int[][] gen = new int[9][9];
		for (int row = 0; row < GRID_SIZE; ++row) {

			for (int col = 0; col < GRID_SIZE; ++col) {
				String txt = tfCells[row][col].getText();
				if (txt == null || txt.equals("")) {
					gen[row][col] = 0;

				} else {
					check[row][col] = true;
					gen[row][col] = Integer.parseInt(txt);
				}
			}

		}
		return gen;
	}

	public void setTxt(int[][] solve) {
	
		for (int row = 0; row < GRID_SIZE; ++row) {

			for (int col = 0; col < GRID_SIZE; ++col) {
				setNoticeHanle();
				if (check[row][col] == false) {
					String txt = tfCells[row][col].getText();

					tfCells[row][col].setText(solve[row][col] + "");
					tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
					;
				} else {
					String txt = tfCells[row][col].getText();
					tfCells[row][col].setText(solve[row][col] + "");
					tfCells[row][col].setForeground(Color.RED);
					tfCells[row][col].setBackground(Green_color);
					check[row][col] = false;
				}
			}

		}
		setNoticeHanled();
	}

	public void setTxtRefesh() {

		for (int row = 0; row < GRID_SIZE; ++row) {

			for (int col = 0; col < GRID_SIZE; ++col) {

				tfCells[row][col].setText("");

			}

		}
	}

	public void setBG() {
		for (int row = 0; row < GRID_SIZE; ++row) {

			for (int col = 0; col < GRID_SIZE; ++col) {

				tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
				tfCells[row][col].setForeground(Color.BLUE);
			}

		}
	}

	public void setNoticeHanle() {

		contentNotice.setText("Ä�kkkkk");

	}

	public void setNoticeHanled() {

		contentNotice.setText("Ä�Ã£ xá»­ lÃ­ xong");

	}

	public void noticeOfBtnCheck() {
		if (checkMatrix()) {
			JOptionPane.showMessageDialog(null, "CÃ¡c tham sá»‘ Ä‘Ãºng");
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại");
		}
	}

	public boolean checkMatrix() {
		int[][] matrix = getTxt();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < matrix.length; i++) {
			list.add(i + 1);
		}

		for (int i = 0; i < matrix.length; i++) {

			for (Integer sum : list) {
				int count = 0;
				int count1 = 0;
				for (int j = 0; j < matrix[i].length; j++) {

					if (sum == matrix[i][j])
						count++;
					if (sum == matrix[j][i])
						count1++;

				}

				if (count >= 2 || count1 >= 2)
					return false;
			}

		}
		int count1 = 0;
		for (int row = 0; row < matrix.length; row += 3) {
			for (int col = 0; col < matrix.length; col += 3) {
				for (Integer sum : list) {
					int count = 0;

					for (int i = row; i < row + 3; i++) {
						for (int j = col; j < col + 3; j++) {
							if (matrix[i][j] > 0)
								count1++;
							if (sum == matrix[i][j])
								count++;

						}
					}
					if (count >= 2)
						return false;
				}

			}

		}

		return true;
	}

	public static void main(String[] args) {
		View check = new View();
//		int[][] t = check.getTxt();
//		for (int i = 0; i < t.length; i++) {
//			for (int j = 0; j < t.length; j++) {
//				System.out.println(t[i][j]);
//			}
//		}
		int[][] check11 = { { 0, 0, 7, 8, 0, 0, 9, 0, 0 }, { 0, 0, 0, 5, 0, 0, 0, 3, 1 }, { 9, 0, 0, 0, 0, 1, 0, 4, 0 },
				{ 2, 1, 0, 0, 6, 0, 7, 8, 0 }, { 0, 0, 0, 0, 0, 3, 0, 9, 0 }, { 3, 0, 9, 0, 1, 0, 2, 0, 0 },
				{ 4, 0, 0, 0, 0, 0, 0, 1, 6 }, { 0, 0, 0, 1, 0, 9, 0, 0, 8 }, { 0, 8, 0, 0, 3, 0, 0, 0, 0 }, };

	}

}