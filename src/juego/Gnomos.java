package juego;

import java.awt.Image;
import entorno.Entorno;
import java.util.Random;

public class Gnomos {
	double x;
	double y;
	Image imagen;
	double ancho;
	double alto;
	double escala;
	boolean tocapiso;
	int gravedad;
	boolean sefuedeisla;
	int direccion;
	double velocidad;
	Random random= new Random();
	
	
	public Gnomos(double x, double y, double escala, double velocidad) {
		Random rand= new Random();
		direccion = rand.nextInt(2);
		this.x = x;
		this.y = y;
		this.escala = escala;
		this.velocidad=velocidad;
		imagen = entorno.Herramientas.cargarImagen("assets/gnomo.png");
		ancho = imagen.getWidth(null)*escala;
		alto = imagen.getHeight(null)*escala;
		gravedad = 4;
		tocapiso = false;
		sefuedeisla= false;
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
	public void moverHorizontal(double horizontal, Entorno e) {
		if(direccion%2==0) {
			x=x+horizontal*velocidad*1.2;		
		}
		if (direccion%2!=0) {
			x=x+horizontal*velocidad*-1.2;
		}
	}
		
	
	public void moverVer() {
		if (!tocapiso) {
			y = y + gravedad;
		}
	}
}
