package juego;
import java.awt.Image;

import entorno.Entorno;

public class Isla {
	
		
		double x;
		double y;
		Image imagen;
		double ancho;
		double alto;
		double escala;
		
		public Isla(double x, double y, double escala) {
			
			this.x = x;
			this.y = y;
			this.escala = escala;
			imagen = entorno.Herramientas.cargarImagen("assets/isla.png");
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
		
		public void dibujar(Entorno e) {
				e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
		}

}
