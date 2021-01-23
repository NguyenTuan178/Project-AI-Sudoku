package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Sudoku;
import view.View;

public class Control {
	private Sudoku model;
	private View view;

	public Control() {
		// TODO Auto-generated constructor stub
		init();
	}

	int count = 0;

	public void init() {
		view = new View();
		View.btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (count == 0) {
					// TODO Auto-generated method stub
					if (view.checkMatrix()) {
						int[][] check4 = { { 0, 0, 7, 0, 1, 0, 0, 0, 8 }, { 0, 0, 0, 6, 8, 0, 3, 0, 2 },
								{ 0, 0, 0, 2, 0, 4, 0, 9, 7 }, { 0, 3, 2, 4, 7, 9, 6, 8, 5 },
								{ 0, 0, 0, 1, 6, 0, 0, 0, 4 }, { 0, 6, 0, 0, 0, 0, 0, 1, 9 },
								{ 0, 7, 0, 0, 4, 0, 0, 0, 0 }, { 3, 0, 9, 0, 2, 0, 8, 5, 1 },
								{ 0, 5, 6, 8, 0, 1, 0, 7, 0 }, };
//					

//					model = new Sudoku(check4);
						model = new Sudoku(view.getTxt());
						int[][] check = model.convert();
						view.setTxt(check);
						count = 1;


					} else {
						JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại số");
					}
				} else {
					view.setTxtRefesh();
					view.setBG();
//					model = new Sudoku(check4);
					model = new Sudoku(view.getTxt());
					int[][] check = model.convert();
					view.setTxt(check);
					count = 0;
				}
			}
		});
		View.btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.setTxtRefesh();
				view.setBG();
			}
		});

		View.btnCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.noticeOfBtnCheck();
			}
		});

	}

	public static void main(String[] args) throws InterruptedException {
		Control con = new Control();
	}
}
