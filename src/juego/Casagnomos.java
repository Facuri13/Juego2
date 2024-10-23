package juego;

import java.awt.Image;

import entorno.Entorno;

public class Casagnomos {
	
	
	double x;
	double y;
	Image imagen;
	double ancho;
	double alto;
	double escala;
	
	public Casagnomos(double x, double y, double escala) {
		
		this.x = x;
		this.y = y;
		this.escala = escala;
		imagen = entorno.Herramientas.cargarImagen("assets/casaTP.png");
		ancho = imagen.getWidth(null)*escala;
		alto = imagen.getHeight(null)*escala;
	}
	
	public void dibujar(Entorno e) {
			e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}

}