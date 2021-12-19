package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import org.w3c.dom.events.MouseEvent;

import manager.Manager;

public class Raton extends MouseAdapter{
	
	private Point posicion;
	private boolean click;
	
	public Raton(Manager m) {
		this.posicion=new Point();
		this.click=false;
		actualizarPosicion(m);
		
	}

	public void actualizar(Manager m) {
		actualizarPosicion(m);
	}
	
	private void actualizarPosicion(Manager m) {
		final Point posicionInicial=MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(posicionInicial, m);
		posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());
		
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
			setClick(true);
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		System.out.println(e.getY());
	}
	
	public void paint(Graphics2D g) {}
	
	public Point getPosicion() {
		return posicion;
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) getPosicion().getX(), (int) getPosicion().getY(), 10, 10);
	}
	
	
}
