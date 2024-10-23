package juego;

import java.awt.Image;
import entorno.Entorno;

public class Jugador1 {
	double x;
	double y;
	Image imagen;
	Image imagenizq;
	double ancho;
	double alto;
	double escala;
	int direccion;
	boolean tocapiso;
	int gravedad;
	boolean enSalto;
	double velocidadSalto;
	
	
	public Jugador1(double x, double y, double escala) {
		this.x = x;
		this.y = y;
		this.escala = escala;
		imagen = entorno.Herramientas.cargarImagen("assets/personaje.png");
		imagenizq = entorno.Herramientas.cargarImagen("assets/personajeizq.png");
		ancho = imagen.getWidth(null)*escala;
		alto = imagen.getHeight(null)*escala;
		tocapiso = false;
		direccion = 1;
		gravedad = 4;
		enSalto = false;
		velocidadSalto = 0;
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
	
	public int getDireccion() {
		return direccion;
	}
	
	public void dibujar(Entorno e) {
		if (direccion == 1) {
            e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
        } 
		else 
            e.dibujarImagen(this.imagenizq, this.x, this.y, 0, this.escala);
        }
	
	public void MoverHor(double horizontal, Entorno e) {
		x = x + horizontal;
		if(bordeIzq()<-4) {
			x=ancho/2;
		}
		if(bordeDer() > e.ancho()) {
			x = e.ancho()-(ancho/2);
		}
		
	}
	public void MoverVer() {
		if(enSalto) {
			y = y - velocidadSalto;
			velocidadSalto -= 0.5;
			
			if (velocidadSalto <= 0) {
				enSalto = false;
			}
		}
		else if (!tocapiso) {
			y = y + gravedad;
		}
	}
	
	public void saltar() {
		if (tocapiso && !enSalto) {
			enSalto= true;
			velocidadSalto = 11;
		}
	}

	public void vueltaInicio(Jugador1 jugador1,Entorno e) {
		if(jugador1.y>=600) {
			x=400;
			y=58;
			jugador1.x=x;
			jugador1.y=y;
			
		}
	}
}

