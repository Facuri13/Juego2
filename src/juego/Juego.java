package juego;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Jugador1 jugador1;

	Isla[] islas;
	List<Fuego> fuegos;
	Gnomos[] gnomos;
	Color colorCustom = new Color(0, 147, 255);
	Casagnomos casa;
	Tortuga tortugas[];
	Random aleatorio;
	int gnomoPer = 0;
	int gnomoSalv = 0;
	boolean ganaste = false;
	boolean perdiste = false;

	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "El Jardín de los Gnomos", 800, 600);
		this.jugador1 = new Jugador1(430, 450, 2.8);

		this.islas = new Isla[15];
		this.gnomos = new Gnomos[5];
		this.fuegos = new ArrayList<>();
		this.casa = new Casagnomos(408, 55, 3);
		entorno.colorFondo(colorCustom);
		this.aleatorio = new Random();
		this.tortugas = new Tortuga[3];

		// Inicializar lo que haga falta para el juego
		// ...
		int k = 0;
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= i; j++) {
				this.islas[k] = new Isla((j * entorno.ancho() / (i + 1) - 1 + (10 * j)), 100 * i, 3.5);
				k = k + 1;

			}

		}

		// Inicia el juego!

		this.entorno.iniciar();
	}

	public boolean colisionPisoGnomo(Gnomos gnomo, Isla isla) {
		return Math.abs(gnomo.bordeAbajo() - isla.bordeArriba()) < 2
				&& (gnomo.bordeDer() > isla.bordeIzq() && gnomo.bordeIzq() < isla.bordeDer());
	}

	public boolean colisionPiso(Jugador1 jugador1, Isla isla) {
		return Math.abs(jugador1.bordeAbajo() - isla.bordeArriba()) < 2
				&& (jugador1.bordeDer() > isla.bordeIzq() && jugador1.bordeIzq() < isla.bordeDer());
	}

	public boolean colisionTecho(Jugador1 jugador1, Isla isla) {
		return Math.abs(jugador1.bordeArriba() - isla.bordeAbajo()) < 7
				&& (jugador1.bordeDer() > isla.bordeIzq() && jugador1.bordeIzq() < isla.bordeDer());
	}

	public boolean colisionLadoIzq(Jugador1 jugador1, Isla isla) {
		return jugador1.bordeDer() >= isla.bordeIzq() && jugador1.bordeIzq() < isla.bordeIzq()
				&& jugador1.bordeAbajo() > isla.bordeArriba() && jugador1.bordeArriba() < isla.bordeAbajo();
	}

	public boolean colisionLadoDer(Jugador1 jugador1, Isla isla) {
		return jugador1.bordeIzq() <= isla.bordeDer() && jugador1.bordeDer() > isla.bordeDer()
				&& jugador1.bordeAbajo() > isla.bordeArriba() && jugador1.bordeArriba() < isla.bordeAbajo();
	}

	public boolean tocaisla(Tortuga tortuga, Isla i) {
		return Math.abs(tortuga.bordeAbajo() - i.bordeArriba()) < 1;
	}

	public boolean tocaislaBorde(Tortuga tortuga, Isla i) {
		return (tortuga.bordeIzq() >= i.bordeIzq() && tortuga.bordeDer() <= i.bordeDer());
	}

	public void creartortugas() {
		for (int i = 1; i < tortugas.length; i++) {

			tortugas[0] = new Tortuga(200, 100, 3);
			tortugas[1] = new Tortuga(274, 100, 3);
			tortugas[2] = new Tortuga(713, 100, 3);
		}

	}

	public void crearGnomos() {
		for (int i = 1; i < gnomos.length; i++) {
			if (gnomos[i] == null) {
				gnomos[i] = new Gnomos(408, 55, 2.5, 0.75);

			}
		}
	}

	public void moverGnomos() {
		for (Gnomos gnomo : gnomos) {
			if (gnomo != null) {
				boolean estaSobreIsla = false;
				for (Isla isla : islas) {
					if (colisionPisoGnomo(gnomo, isla)) { // El triplehijueputa del gnomo solo se mueve cuando toca una
															// isla
						gnomo.tocapiso = true;
						estaSobreIsla = true;
						break;
					}
				}
				if (!estaSobreIsla) {
					gnomo.tocapiso = false;

				}
				gnomo.moverHorizontal();
				gnomo.moverVer();
				gnomo.actualizarGnomos();
			}
		}
	}

	public void movertortugas() {
		for (Tortuga t : tortugas) {
			if (t != null) {
				boolean estaSobreIsla = false;
				for (Isla isla : islas) {
					if (tocaisla(t, isla)) { // El triplehijueputa del gnomo solo se mueve cuando toca una
															// isla
						t.tocapiso = true;
						estaSobreIsla = true;
						break;
					}
				}
				if (!estaSobreIsla) {
					t.tocapiso = false;

				}
				t.moverInicial();
				t.moverVer();
				t.cambiarDireccion();
			}
		}
	}
	// check de colision gnomo-jugador
	public boolean colisionGnomoPersonaje(Gnomos gnomo, Jugador1 jugador) {
		return gnomo.bordeDer() > jugador.bordeIzq() && gnomo.bordeIzq() < jugador.bordeDer()
				&& gnomo.bordeAbajo() > jugador.bordeArriba() && gnomo.bordeArriba() < jugador.bordeAbajo();
	}

	public boolean colisionGnomoTortuga(Gnomos gnomo, Tortuga tortuga) {

		return gnomo.bordeDer() > tortuga.bordeIzq() && gnomo.bordeIzq() < tortuga.bordeDer()
				&& gnomo.bordeAbajo() > tortuga.bordeArriba() && gnomo.bordeArriba() < tortuga.bordeAbajo();

	}

	public void destruirGnomos() { // Logica de cuando el gnomo pasaria a ser null
		for (int i = 0; i < gnomos.length; i++) {
			for (Tortuga t : tortugas) {
				if (gnomos[i] != null) {

					if ((colisionGnomoPersonaje(gnomos[i], jugador1))) {
						gnomos[i] = null;
						gnomoSalv = gnomoSalv + 1;
					} else {
						if ((colisionGnomoTortuga(gnomos[i], t)) || (gnomos[i].y > 800)) {
							gnomos[i] = null;
							gnomoPer = gnomoPer + 1;
						}
					}
				}
			}
		}
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		// ...
		// DIBUJO DE LAS CLASES
		if (ganaste) {
			this.entorno.cambiarFont("Calibri", 60, Color.yellow);
			entorno.escribirTexto("Felicidades, ganaste!", 30, 287);
		} else if (perdiste) {
			this.entorno.cambiarFont("Calibri", 60, Color.red);
			entorno.escribirTexto("Perdiste, los gnomos murieron", 30, 287);
		}

		else {
			casa.dibujar(entorno);

			jugador1.dibujar(entorno);

			// SET DE LOS MOVIMIENTOS Y CREACION
			jugador1.MoverVer();

		}

		moverGnomos();
		crearGnomos();
		creartortugas();
		destruirGnomos();
		movertortugas();
		this.entorno.cambiarFont("Arial", 20, Color.black);
		entorno.escribirTexto("Gnomos salvados: " + gnomoSalv, 40, 60);
		entorno.escribirTexto("Gnomos perdidos: " + gnomoPer, 40, 80);
		for (Tortuga t : tortugas) {
			t.moverVer();
		}

		if (gnomoSalv == 10) {
			for (int i = 0; i < gnomos.length; i++) {
				gnomos[i] = null;
			}
			
			jugador1 = null;
			for (int i = 0; i < islas.length; i++) {
				islas[i] = null;
			}
			casa = null;
			ganaste = true;
		}
		if (gnomoPer == 10) {
			for (int i = 0; i < gnomos.length; i++) {
				gnomos[i] = null;
			}
			
			jugador1 = null;
			for (int i = 0; i < islas.length; i++) {
				islas[i] = null;
			}
			casa = null;
			perdiste = true;
		}
		if (jugador1.y >= 600) {
			perdiste = true;
		}
		// GENERACION DE ISLAS[]
		for (Isla e : islas) {
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
		// GENERACION DE GNOMOS
		for (Gnomos gnomo : gnomos) {
			if (gnomo != null) {
				gnomo.dibujar(entorno);
			}
		}

		// GENERACION DE TORTUGAS
		for (Tortuga t : tortugas) {

			t.dibujar(entorno);

		}

		//for (Isla isla : islas) {
			//for (Tortuga t : tortugas) {
				

				//if (tocaisla(t, isla)) {

//					if (tocaislaBorde(t, isla)) {
//						
//						t.moverInicial(1, entorno);
//
//					} else {
//
//						//t.tocapiso = true;
//						t.cambiarDireccion(1, entorno);
//					}
//				}
//			}
//		}

		// GENERACION DE FUEGO
		for (Fuego fuego : fuegos) {
			fuego.actualizar();
			fuego.dibujar(entorno);
		}

		// chequearTeclas(){}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			jugador1.MoverHor(4, entorno);
			jugador1.direccion = 1;
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			jugador1.MoverHor(-4, entorno);
			jugador1.direccion = -1;
		}
		if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			jugador1.saltar();
		}

		if (entorno.sePresiono(entorno.TECLA_ESCAPE)) {
			System.out.println("posicion de jugador en X:" + jugador1.x);
			System.out.println("posicion de jugador en y: " + jugador1.y);
			
		}

		if (entorno.sePresiono('c')) {
			Fuego fuego = new Fuego(jugador1.x, jugador1.y, 1, jugador1.getDireccion());
			fuegos.add(fuego);
		}

		// AYUDA PARA LAS COORDENADAS DEL MOUSE Y SET DEL TIEMPO
		// entorno.escribirTexto(""+entorno.getFrames(),200,20);
		entorno.escribirTexto("" + (entorno.tiempo() / 1000), 100, 20); // Esto no lo borren, lo estoy usando para
																		// guiarme por la pantalla. Att: Me.
		entorno.escribirTexto("mouse coord x: " + entorno.mouseX(), 500, 200); // Tambien aprobecho para decir que si
																				// encuentran algo sin mucho sentido o
																				// que no esta terminado borrenlo, hay
																				// cosas que hago
		entorno.escribirTexto("mouse coord y:" + entorno.mouseY(), 500, 300); // y me olvido de borrar (la chucha de
																				// Test no la borren xd)
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
