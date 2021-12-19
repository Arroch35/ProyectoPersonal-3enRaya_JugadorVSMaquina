package manager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import model.Casilla;
import model.Cuadrado;
import model.Raton;

public class Manager extends JPanel{
	private boolean jugador1;
	private Cuadrado[][] tablero;
	private int[][] matriz;
	private Raton raton;
	private Scanner scan;
	private Random rand;
	private JFrame f; 
	
	public Manager() {
		raton=new Raton(this);
		this.jugador1=true;
		this.tablero=new Cuadrado[3][3];
		this.matriz=new int[3][3];
		this.scan=new Scanner(System.in);
		this.rand=new Random();
		this.f = new JFrame("Buscaminas");
		f.add(this);
		f.setSize(333, 356);
		f.setResizable(false);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addMouseListener(raton);
		rellenarTablero();
		rellenarMatriz();

	}
	
	public void actualizar() {
		System.out.println("Tres en Raya");
		do {
			turnos();
			mostrarTablero();
			setJugador1();
			this.repaint();
		}while(!ganar() && !empate());
	}

	public void setJugador1() {
		if(jugador1) {
			jugador1=false;
		}else {
			jugador1=true;
		}
	}
	
	public void rellenarTablero() {
		Cuadrado c=null;
		for(int i=0; i<tablero.length; i++) {
			for(int j=0; j<tablero[i].length; j++) {
				if(i==0 && j==0) {
					tablero[i][j]=new Cuadrado(f);
				} else {
					tablero[i][j]=new Cuadrado(f, "["+i+"]["+j+"]", c);
				}
				if(i!=0&&j==0) {
					tablero[i][j].setX(-tablero[i][j].getX());
					tablero[i][j].setY(tablero[i][j].getDimensiones());
				}
				c=tablero[i][j];
				
				System.out.print(tablero[i][j]);
			}
			System.out.println();
		}
	}
	
	public void rellenarMatriz() {
		for(int i=0; i<matriz.length; i++) {
			Arrays.fill(matriz[i], 0);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i=0; i<tablero.length; i++) {
			for(int j=0; j<tablero[0].length; j++) {
				tablero[i][j].paint(g2d);
			}
		}
		raton.paint(g2d);
	}
	
	public void mostrarTablero() {
		for(int i=0; i<tablero.length; i++) {
			for(int j=0; j<tablero[i].length; j++) {
				System.out.print(tablero[i][j]);
			}
			System.out.println();
		}
	}
	
	public void turnos() {
		if(jugador1) {
			System.out.println("¡Jugador1, es tu turno!");
			menu();
		}else {
			System.out.println("¡Jugador2, es tu turno!");
			inteligenciaArtificial(tablero);
		}
	}
	
	public void menu() {
		boolean ocupado;
		boolean paso;
		do {
			raton.actualizar(this);
			ocupado=false;
			paso=false;
			for(int i=0; i<tablero.length; i++) {
				for(int j=0; j<tablero[i].length; j++) {
					if(raton.isClick() && raton.getBounds().intersects(tablero[i][j].getBounds())) {
						if(tablero[i][j].getCaracter().equals(" · ")) {
							tablero[i][j].setBotonPulsado(true);
							tablero[i][j].setCaracter(" X "); 
							paso=true;
						} else {
							ocupado=true;
						}
						raton.setClick(false);
					}
				}
			}
		} while(ocupado || !paso);
	}
	
	public boolean ganar() {
		boolean ganar=false, contd1=false, contd2=false;
		int contf=0, contc=0;
		for(int i=0; i<tablero.length && contf<2 && contc<2; i++) {
			contf=0;
			contc=0;
			for(int j=0; j<tablero[0].length && contf<2 && contc<2; j++) {
				if(!(tablero[i][j].getCaracter().equals(" · "))){
					if(j!=0 && tablero[i][j].getCaracter().equals(tablero[i][j-1].getCaracter())) {
						contf++;
					}
				}
				if(!(tablero[j][i].getCaracter().equals(" · "))){
					if(j!=0 && tablero[j][i].getCaracter().equals(tablero[j-1][i].getCaracter())) {
						contc++;
					}
				}
			}
		}
		if(!(tablero[1][1].getCaracter().equals(" · "))) {
			if(tablero[0][0].getCaracter().equals(tablero[1][1].getCaracter()) && tablero[1][1].getCaracter().equals(tablero[2][2].getCaracter())) {
				contd1=true;
			}
			if(tablero[0][2].getCaracter().equals(tablero[1][1].getCaracter()) && tablero[1][1].getCaracter().equals(tablero[2][0].getCaracter())) {
				contd2=true;
			}
		}

		if(contf==2 || contc==2 || contd1 || contd2) {
			ganar=true;
			if(jugador1) {
				JOptionPane.showMessageDialog(this, "¡Has perdido!" , "¡Lo siento!", JOptionPane.YES_NO_OPTION);
				System.exit(ABORT);
			} else {
				JOptionPane.showMessageDialog(this, "¡Has ganado!" , "¡Felicidades!", JOptionPane.YES_NO_OPTION);
				System.exit(ABORT);
			}

		}
		return ganar;
	}
	
	public boolean empate() {
		for(int i=0; i<tablero.length; i++) {
			for(int j=0; j<tablero[i].length; j++) {
				if(tablero[i][j].getCaracter().equals(" · ")) {
					return false;
				}
			}
		}
		JOptionPane.showMessageDialog(this, "¡Has empatado!" , "¡Bien jugado!", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
		return true;
	}
	
	public void inteligenciaArtificial(Cuadrado[][] tablero) {
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[0].length; j++) {
				if(tablero[i][j].getCaracter().equals(" X ")) {
					matriz[i][j]=-1;
				}
				if(tablero[i][j].getCaracter().equals(" O ")) {
					matriz[i][j]=-2;
				}
			}
		}
		calcularPuntos();
	}
	
	public void calcularPuntos() {
		int contf=0, contc=0, contd1=0, contd2=0, contf1=0, contc1=0, contd11=0, contd21=0;
		int contf2=0, contc2=0, contd12=0, contd22=0;
		boolean d1=false, d2=false, d11=false, d21=false, d12=false, d22=false;
		int[][] valores = new int[3][3];
		for(int i=0; i<valores.length; i++) {
			for(int j=0; j<valores[0].length; j++) {
				valores[i][j]=matriz[i][j];
			}
		}
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[0].length; j++) {
				contf=0;
				contc=0;
				contd1=0;
				contd2=0;
				contf1=0;
				contc1=0;
				contd11=0;
				contd21=0;
				contf2=0;
				contc2=0;
				contd12=0;
				contd22=0;
				if(matriz[i][j]==0) {
					for(int k=0; k<matriz.length; k++) {
						//FILA
						if(matriz[i][k]==0 || matriz[i][k]==-2) {
							contf++;
							if(contf==3) {
								valores[i][j]+=1;
							}
						} else if (matriz[i][k]==-1) {
							contf1++;
							if(contf1==2) {
								valores[i][j]+=10;
							} else {
								valores[i][j]+=1;
							}
						}
						if (matriz[i][k]==-2) {
							contf2++;
							if(contf2==2) {
								valores[i][j]+=20;
							}
						}
						
						//COLUMNA
						if(matriz[k][j]==0 || matriz[k][j]==-2) {
							contc++;
							if(contc==3) {
								valores[i][j]+=1;
							}
						} else if (matriz[k][j]==-1) {
							contc1++;
							if(contc1==2) {
								valores[i][j]+=10;
							} else {
								valores[i][j]+=1;
							}
						}
						if (matriz[k][j]==-2) {
							contc2++;
							if(contc2==2) {
								valores[i][j]+=20;
							}
						}
						
						//DIAGONAL I-D
						if(matriz[k][k]==0 || matriz[k][k]==-2) {
							if(i==k&&j==k) {
								d1=true;
							}
							contd1++;
							if(contd1==3 && d1) {
								valores[i][j]+=1;
								d1=false;
							} 
						} else if (matriz[k][k]==-1) {
							if(j==i) {
								d11=true;
							}
							contd11++;
							if(d11) {
								if(contd11==2) {
									valores[i][j]+=10;
									d11=false;
								} else {
									valores[i][j]+=1;
									d11=false;
								}
							}

						}
						if (matriz[k][k]==-2) {
							if(j==i) {
								d12=true;
							}
							contd12++;
							if(contd12==2 && d12) {
								valores[i][j]+=20;
								d12=false;
							}
						}
						
						//DIAGONAL D-I
						if(matriz[k][2-k]==0 || matriz[k][2-k]==-2) {
							if(i==k&&j==2-k) {
								d2=true;;
							}
							contd2++;
							if(contd2==3 && d2) {
								valores[i][j]+=1;
								d2=false;
							}
						} else if (matriz[k][2-k]==-1) {
							if((i==0 || i==1 || i==2) && (j==2-i)) {
								d21=true;;
							}
							contd21++;
							if(d21) {
								if(contd21==2) {
									valores[i][j]+=10;
									d21=false;
								} else {
									valores[i][j]+=1;
									d21=false;
								}
							}

						} 
						if (matriz[k][2-k]==-2) {
							if((i==0 || i==1 || i==2) && (j==2-i)) {
								d22=true;
							}
							contd22++;
							if(contd22==2 && d22) {
								valores[i][j]+=20;
								d22=false;
							}
						}
					}
				}
			}
		}
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz[i].length; j++) {
				System.out.print(matriz[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		for(int i=0; i<valores.length; i++) {
			for(int j=0; j<valores[i].length; j++) {
				System.out.print(valores[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		colocarCaracter(valores);
	}
	
	public void colocarCaracter(int[][] valores) {
		ArrayList<Casilla> max=new ArrayList<Casilla>();
		for(int i=0; i<valores.length; i++) {
			for(int j=0; j<valores[i].length; j++) {
				if(max.isEmpty()) { 
					max.add(new Casilla(i, j, valores[i][j]));
				} else {
					if(valores[i][j]>max.get(0).getValor()) {
						max.clear();
						max.add(new Casilla(i, j, valores[i][j]));
					} else if(valores[i][j]==max.get(0).getValor()){
						max.add( new Casilla(i, j, valores[i][j]));
					}
				}
			}
		}
		if(max.size()==1) {
			tablero[max.get(0).getI()][max.get(0).getJ()].setCaracter(" O ");
		} else {
			int r=rand.nextInt(max.size());
			tablero[max.get(r).getI()][max.get(r).getJ()].setCaracter(" O ");
		}
	}
}
