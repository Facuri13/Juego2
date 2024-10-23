package juego;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Jugador1 jugador1;
	Tortuga tortuga;
	Isla[] islas;
	List<Fuego> fuegos;
	Gnomos[] gnomos;
	Color colorCustom = new Color(0, 147, 255); 
	Casagnomos casa;
	
	Random aleatorio;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "El Jardín de los Gnomos", 800, 600);
		this.jugador1 = new Jugador1(400, 40, 2.8);
		this.tortuga= new Tortuga(200, 100, 3);
		this.islas = new Isla[15];
		this.gnomos = new Gnomos[4];
		this.fuegos = new ArrayList<>();
		this.casa = new Casagnomos(408, 55, 3);
		entorno.colorFondo(colorCustom);
		this.aleatorio=new Random();
		
		
		
		
		// Inicializar lo que haga falta para el juego
		// ...
		int k =0;
		for(int i=1;i<=5; i++) {	
			for(int j=1; j<=i;j++) {
				this.islas[k]= new Isla((j*entorno.ancho()/(i+1)-1+(10*j)),100*i,3.5);
				k=k+1;
			
				
			}
			
		}

		// Inicia el juego!
	
		this.entorno.iniciar();
	}
	
	
	public boolean colisionPiso(Jugador1 jugador1, Isla isla) {
	    return Math.abs (jugador1.bordeAbajo() - isla.bordeArriba()) < 2 && 
	    (jugador1.bordeDer() > isla.bordeIzq() && jugador1.bordeIzq() < isla.bordeDer());
	}
	public boolean colisionTecho(Jugador1 jugador1, Isla isla) {
	    return Math.abs (jugador1.bordeArriba() - isla.bordeAbajo()) < 3 && 
	    (jugador1.bordeDer() > isla.bordeIzq() && jugador1.bordeIzq() < isla.bordeDer());
	}
	
	public boolean colisionLadoIzq(Jugador1 jugador1, Isla isla) {
	    return jugador1.bordeDer() >= isla.bordeIzq() && 
	           jugador1.bordeIzq() < isla.bordeIzq() && 
	           jugador1.bordeAbajo() > isla.bordeArriba() && 
	           jugador1.bordeArriba() < isla.bordeAbajo();
	}

	public boolean colisionLadoDer(Jugador1 jugador1, Isla isla) {
	    return jugador1.bordeIzq() <= isla.bordeDer() && 
	           jugador1.bordeDer() > isla.bordeDer() && 
	           jugador1.bordeAbajo() > isla.bordeArriba() && 
	           jugador1.bordeArriba() < isla.bordeAbajo();
	}
	
	public boolean tocaisla(Tortuga tortuga,Isla i) {
		return Math.abs(tortuga.bordeAbajo()-i.bordeArriba())<1;
	}
	public boolean tocaislaBorde(Tortuga tortuga, Isla i) {
		return (tortuga.bordeIzq() >= i.bordeIzq() && tortuga.bordeDer()<=i.bordeDer());
	}
	
	public void crearGnomos() {
		for (int i = 0; i < gnomos.length; i++) {
            if (gnomos[i] == null) {
                gnomos[i] = new Gnomos(408, 55, 2.5);
                break;
            }
        }
    }

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		casa.dibujar(entorno);
		tortuga.dibujar(entorno);
		jugador1.dibujar(entorno);
		
		
		//entorno.escribirTexto(""+entorno.getFrames(),200,20);
		entorno.escribirTexto(""+(entorno.tiempo()/1000), 100, 20);
		entorno.escribirTexto("mouse coord x: "+entorno.mouseX(), 690, 20);                 //Esto no lo borren, lo estoy usando para guiarme por la pantalla. Att: Me.
		entorno.escribirTexto("mouse coord y:"+entorno.mouseY(), 690, 30);					//Tambien aprobecho para decir que si encuentran algo sin mucho sentido o que no esta terminado borrenlo, hay cosas que hago
																							//y me olvido de borrar (la chucha de Test no la borren xd)
		jugador1.vueltaInicio(jugador1, entorno);
		jugador1.MoverVer();
		tortuga.moverVer();
		crearGnomos();
		
		//GENERACION DE ISLAS[]
		for (Isla e: islas ) {
			e.dibujar(entorno);
		}
		
		for (Isla isla : islas) {
		    if (colisionPiso(jugador1, isla)) {
		        jugador1.tocapiso = true;
		        break;
		        
		    } else if (colisionTecho(jugador1, isla)) {
		        jugador1.enSalto = false;
		    } else if (colisionLadoIzq(jugador1, isla)) {
		        jugador1.x = isla.bordeIzq() - jugador1.ancho / 2;
		    } else if (colisionLadoDer(jugador1, isla)) {
		        jugador1.x = isla.bordeDer() + jugador1.ancho / 2;
		    } else {
		        jugador1.tocapiso = false;
		    }
		}
		
		for (Isla isla: islas) {
			int hola=1;
			if(tocaisla(tortuga,isla)) {
				
				
				if(tocaislaBorde(tortuga, isla)) {
					tortuga.tocapiso=true;	
					tortuga.moverInicial(hola,entorno);
					
					}
				else {
					
					tortuga.tocapiso=true;
					tortuga.cambiarDireccion(hola,entorno);
				}
			}
		}

		for (Fuego fuego : fuegos) {
			fuego.actualizar(); 
			fuego.dibujar(entorno);
			}
		for (Gnomos gnomo : gnomos) {
			if (gnomo != null) {
				gnomo.dibujar(entorno);
			}
		}
		//chequearTeclas(){}
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			jugador1.MoverHor(4, entorno);
			jugador1.direccion = 1; 
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			jugador1.MoverHor(-4,entorno);
			jugador1.direccion = -1; 
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			jugador1.saltar();
		}
		
		if(entorno.sePresiono(entorno.TECLA_ESCAPE)) {
			System.out.println("posicion de jugador en X:"+ jugador1.x);
			System.out.println("posicion de jugador en y: " + jugador1.y);
			System.out.println("posicion de tortuga en X:"+ tortuga.x);
			System.out.println("posicion de tortuga en y: " + tortuga.y);
		}	
		
		if(entorno.sePresiono('c')) {
			Fuego fuego = new Fuego(jugador1.x, jugador1.y, 1, jugador1.getDireccion());
			fuegos.add(fuego);
        }
		   for (Gnomos gnomo : gnomos) {
		        if (gnomo != null) {
		            gnomo.dibujar(entorno);
		        }
		    }
}
		
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
}
