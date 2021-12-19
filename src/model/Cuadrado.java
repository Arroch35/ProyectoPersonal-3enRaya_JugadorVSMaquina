package model; 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Cuadrado {
	private int dimensiones;
	private String pos;
	private int x;
	private int y;
	private Cuadrado precursor;
	private JFrame f;
	private boolean botonPulsado; 
	private String caracter;
	
	public Cuadrado(JFrame f) {
		this.f=f;
		this.dimensiones=(f.getWidth()-15)/3;
		this.x=0;
		this.y=0;
		this.pos="[0][0]";
		this.botonPulsado=false;
		this.caracter=" · ";
	}
	
	public Cuadrado(JFrame f, String pos, Cuadrado p) {
		this.f=f;
		this.dimensiones=(f.getWidth()-15)/3;
		this.precursor=p;
		this.pos=pos;
		this.x=precursor.getX()+dimensiones;
		this.y=precursor.getY();
		this.botonPulsado=false;
		this.caracter=" · ";
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x+(dimensiones/2)-((dimensiones-1)/2), y+(dimensiones/2)-((dimensiones-1)/2), dimensiones-1, dimensiones-1);
		
		if(!(caracter.equals(" · "))) {
			if(caracter.equals(" X ")) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GREEN);
			}
			g.setFont(new Font("Verdana", Font.BOLD, 60));
			g.drawString(caracter, x+8, y+(dimensiones/2)+23);
		}

	}	
	
	public boolean isBotonPulsado() {
		return botonPulsado;
	}

	public void setBotonPulsado(boolean botonPulsado) {
		this.botonPulsado = botonPulsado;
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public int getDimensiones() {
		return dimensiones;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x += x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y += y;
	}

	public Cuadrado getPrecursor() {
		return precursor;
	}

	public void setPrecursor(Cuadrado precursor) {
		this.precursor = precursor;
	}

	@Override
	public String toString() {
		return "Cuadrado [pos=" + pos + ", x=" + x + ", y=" + y + "]";
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, dimensiones, dimensiones);
	}

}
