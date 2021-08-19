package com.hlopes.cm.models;

import java.util.ArrayList;
import java.util.List;

import com.hlopes.cm.exception.ExplosaoException;

public class Board {
	private int linhas;
	private int colunas;
	private int minas;

	private final List<Field> fields = new ArrayList<>();

	public Board(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	public void abrir(int linha, int coluna) {
		try {
			fields.parallelStream().filter(c -> c.getColuna() == coluna && c.getLinha() == linha).findFirst()
					.ifPresent(c -> c.abrir());
		} catch (ExplosaoException e) {
			fields.forEach(c -> c.setAberto(true));
			throw e;
		}
	}

	public void marcar(int linha, int coluna) {
		fields.parallelStream().filter(c -> c.getColuna() == coluna && c.getLinha() == linha).findFirst()
				.ifPresent(c -> c.alternateMark());
	}

	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				fields.add(new Field(i, j));
			}

		}

	}

	private void associarVizinhos() {
		for (Field field1 : fields) {
			for (Field field2 : fields) {
				field1.addVizinho(field2);
			}
		}
	}

	private void sortearMinas() {
		int minasArmadas = 0;
		do {
			int aleatorio = (int) (Math.random() * fields.size());

			fields.get(aleatorio).minar();

			minasArmadas = (int) fields.stream().filter(m -> m.isMinado()).count();
		} while (minasArmadas < minas);
	}

	public boolean objetivoAlcancado() {
		return fields.stream().allMatch(c -> c.objetivoAlcacado());
	}

	public void restart() {
		fields.stream().forEach(c -> c.reinicar());
		sortearMinas();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("  ");

		for (int c = 0; c < colunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");

		int indice = 0;
		for (int i = 0; i < linhas; i++) {
			sb.append(i);
			sb.append(" ");
			for (int j = 0; j < colunas; j++) {
				sb.append(" ");
				sb.append(fields.get(indice));
				sb.append(" ");
				indice++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
