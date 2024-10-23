package juego;

import java.awt.Image;
import java.util.Random;
import entorno.Entorno;


public class Tortuga {
	double x;
	double y;
	Image imagen;
	double ancho;
	double alto;
	double escala;
	double direccion;
	boolean tocapiso;
	boolean estaenaire;
	boolean movimiento;
	Random random= new Random();
	
	
	public Tortuga(double x, double y, double escala) {
		Random rand= new Random();
		direccion = rand.nextInt(2);
		this.x = x;
		this.y = y;
		this.escala = escala;
		imagen = entorno.Herramientas.cargarImagen("assets/Tortuga.png");
		imagen = entorno.Herramientas.cargarImagen("assets/Tortugaizq.png");
		ancho = imagen.getWidth(null)*escala;
		alto = imagen.getHeight(null)*escala;

		tocapiso= false;
		estaenaire= false;
		movimiento=false;
	}
	public double bordeArriba() {
		return y-(alto/2);
	}

	public double bordeAbajo() {
		return y+(alto/2);
	}

	public double bordeIzq() {
		return x-(ancho/2);
	}

	public double bordeDer() {
		return x+(ancho/2);
	}
	
	public void dibujar(Entorno e) {
			e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}
	
	public void moverVer() {
		if(!tocapiso) {
			y=y+3;
		}
	}
	
	public void cambiarDireccion(double horizontal, Entorno e) {
		
		
		if(direccion%2==0) {
			
			x=x+horizontal*-1;	
			direccion=1;
		}
		else {
			x=x+horizontal*1;
			direccion=0;
		}
	}
		

	
	public void moverInicial(double horizontal, Entorno e) {
		
		if(direccion%2==0) {
			x=x+horizontal*1;		
		}
		if (direccion%2!=0) {
			x=x+horizontal*-1;
		}
		
	}

}
		
		
		
	
	



