package com.hlopes.cm.models;

import java.util.ArrayList;
import java.util.List;

import com.hlopes.cm.exception.ExplosaoException;

public class Field {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private List<Field> vizinhos = new ArrayList<>();

	public Field(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean addVizinho(Field vizinho) {
		boolean candidatoAVizinhoLinha = linha != vizinho.linha;
		boolean candidatoAVizinhocoluna = coluna != vizinho.coluna;

		boolean isDiagonal = candidatoAVizinhocoluna && candidatoAVizinhoLinha;

		int diferencaLinha = Math.abs(linha - vizinho.linha);
		int diferencacoluna = Math.abs(coluna - vizinho.coluna);
		int deltaDif = diferencacoluna + diferencaLinha;

		if (isDiagonal && deltaDif == 2) {
			vizinhos.add(vizinho);
			return true;
		} else if (!isDiagonal && deltaDif == 1) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void alternateMark() {
		if (!aberto) {
			marcado = !marcado;
		}
	}

	void minar() {
		minado = true;
	}

	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				throw new ExplosaoException();
			}

			if (vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}

			return true;
		} else {
			return false;
		}
	}

	boolean vizinhacaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isMinado() {
		return minado;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcacado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;

		return desvendado || protegido;
	}

	long minasVisinhaca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}

	void reinicar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	public String toString() {
		if(marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasVisinhaca() > 0) {
			return Long.toString(minasVisinhaca());
		} else if (aberto) {
			return "0";
		} else {
			return "?";
		}
	}
}
