package model;

public class Casilla {
	private int i;
	private int j;
	private int valor;
	
	public Casilla(int i, int j, int valor) {
		this.i=i;
		this.j=j;
		this.valor=valor;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public int getValor() {
		return valor;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "casilla["+i+"]["+j+"]="+valor;
	}
	
	
}
