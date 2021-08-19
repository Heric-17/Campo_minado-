package com.hlopes.cm.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.hlopes.cm.exception.ExplosaoException;
import com.hlopes.cm.exception.SairException;
import com.hlopes.cm.models.Board;

public class TabuleiroConsole {

	private Board board;
	private Scanner entrada = new Scanner(System.in);

	public TabuleiroConsole(Board board) {
		this.board = board;

		gameExe();
	}

	private void gameExe() {
		try {
			boolean continuar = true;
			while (continuar) {
				cicloGame();
				System.out.println("Outra partida (S/n)");
				String resposta = entrada.nextLine();

				if (resposta.equalsIgnoreCase("n")) {
					continuar = false;
				} else {
					board.restart();
				}
			}
		} catch (SairException e) {
			System.out.println("Game over");
		} finally {
			entrada.close();
		}
	}

	private void cicloGame() {
		try {
			while (!board.objetivoAlcancado()) {
				System.out.println(board);

				String digitado = capturarValorDigitado("Digite (x, y): ");
				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(s -> Integer.parseInt(s.trim()))
						.iterator();

				digitado = capturarValorDigitado("1 - Abrir \n2 - (Des)Marcar");
				if ("1".equals(digitado)) {
					board.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					board.marcar(xy.next(), xy.next());
				}
			}
			System.out.println(board);
			System.out.println("You Won");
		} catch (ExplosaoException e) {
			System.out.println("BOOOM");
			System.out.println(board);
		}

	}

	private String capturarValorDigitado(String string) {

		System.out.print(string);
		String coordenada = entrada.nextLine();
		if (coordenada.equalsIgnoreCase("sair")) {
			throw new SairException();
		}
		return coordenada;
	}

}
