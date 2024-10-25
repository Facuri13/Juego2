package juego;

import java.awt.Image;
import entorno.Entorno;
import java.util.Random;

public class Gnomos {
	private static final int MIN_VELOCIDAD = 0;
	private static final int MAX_VELOCIDAD = 1;
	double x;
	double y;
	Image imagen;
	double ancho;
	double alto;
	double escala;
	boolean tocapiso;
	int gravedad;
	boolean cambioDireccion;
	int direccion;
	double velocidad;
	double vacio;
	Random random= new Random();
	boolean murio;
	
	
	public Gnomos(double x, double y, double escala, double velocidad) {
		Random rand= new Random();
		
		this.x = x;
		this.y = y;
		this.escala = escala;
		this.velocidad=velocidad;
		imagen = entorno.Herramientas.cargarImagen("assets/gnomo.png");
		ancho = imagen.getWidth(null)*escala;
		alto = imagen.getHeight(null)*escala;
		gravedad = 4;
		tocapiso = false;
		cambioDireccion= false;
		murio=false;
		
        
        // Generar una dirección aleatoria entre -1 y 1
        direccion = rand.nextBoolean() ? 1 : -1;
        
        // Generar velocidad aleatoria entre los valores mínimo y máximo
        velocidad = MIN_VELOCIDAD + (MAX_VELOCIDAD - MIN_VELOCIDAD) * rand.nextDouble();
	
		
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
	 public void moverHorizontal() { // Movimiento horizontal en base a la direccion (random)
	        if (direccion == 0) {
	            x = x + velocidad * 1.2;
	        } else {
	            x = x - velocidad * 1.2;
	        }
	    }

	    public void moverVer() { 
	        if (!tocapiso) {
	            y = y + gravedad; //Movimiento vertical 
	            if (cambioDireccion = false) {
	            	 Random rand = new Random();
	 	            direccion = rand.nextInt(2);  //Cambio de direccion SOLO CUANDO NO HAYA CAMBIADO ANTES
	 	            cambioDireccion = true; 
	            }
	           
	        }
	        }

	    public void actualizarGnomos() {  //Cuando el gnomo hijo de mil puta esta en el aire Y NO CAMBIO SU DIRECCION TODAVIA
	        if (tocapiso && !cambioDireccion) {
	            Random rand = new Random();
	            direccion = rand.nextInt(2);   //Cambio de direccion OTRA VEZ 
	            cambioDireccion = true;  
	        }
	    }
}