package juego;

import java.awt.Image;

import entorno.Entorno;

public class Fuego {
	double x;
	double y;
	Image imagen;
	Image imagenizq;
	double ancho;
	double alto;
	double escala;
	double velocidad;
	int direccion;
	
	public Fuego(double x, double y, double escala, int direccion) {
		this.x = x;
		this.y = y;
		this.escala = escala*3.5;
		this.velocidad = 10;
		this.direccion = direccion;
		imagen = entorno.Herramientas.cargarImagen("assets/fuego.png");
		imagenizq = entorno.Herramientas.cargarImagen("assets/fuegoizq.png");
		ancho = imagen.getWidth(null)*escala;
		alto = imagen.getHeight(null)*escala;
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
	public void dibujar (Entorno e) {
		if (direccion == 1){
			e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
		}
		else {
			e.dibujarImagen(this.imagenizq, this.x, this.y, 0, this.escala);
		}
	}
	public void actualizar () {
		x+=velocidad*direccion;
	}
}
