package com.hlopes.cm;

import com.hlopes.cm.models.Board;
import com.hlopes.cm.vision.TabuleiroConsole;

public class Application {
	public static void main(String[] args) {

		Board board = new Board(6, 6, 3);
		new TabuleiroConsole(board);
		
	}

}
