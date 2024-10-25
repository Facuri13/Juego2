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
	List<Isla> islas;
	List<Fuego> fuegos;
	List<Gnomos> gnomos;
	Color colorCustom = new Color(0, 147, 255);
	Casagnomos casa;
	List<Tortuga> tortugas;
	Random aleatorio;
	int ultimognomo = 0;
	int indiceGnomo = 0;
	int gnomoPer = 0;
	int gnomoSalv = 0;
	boolean ganaste = false;
	boolean perdiste = false;
	boolean skillissue = false;

	// Variables y métodos propios de cada grupo
	// ...
	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "El Jardín de los Gnomos", 800, 600);
		this.jugador1 = new Jugador1(430, 450, 2.8);
		// this.tortuga= new Tortuga(180, 100, 3);
		this.islas = new ArrayList<>();
		this.gnomos = new ArrayList<>();
		this.fuegos = new ArrayList<>();
		this.casa = new Casagnomos(408, 55, 3);
		entorno.colorFondo(colorCustom);
		this.aleatorio = new Random();
		this.tortugas = new ArrayList<>();
		// Inicializar lo que haga falta para el juego
		// ...
		int k = 0;
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= i; j++) {
				this.islas.add(new Isla((j * entorno.ancho() / (i + 1) - 1 + (10 * j)), 100 * i, 3.5));
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
		return Math.abs(tortuga.bordeAbajo() - i.bordeArriba()) < 5
				&& (tortuga.bordeDer() > i.bordeIzq() && tortuga.bordeIzq() < i.bordeDer());
	}

	public boolean tocaislaBorde(Tortuga tortuga, Isla i) {
		return (tortuga.bordeIzq() >= i.bordeIzq() && tortuga.bordeDer() <= i.bordeDer());
	}

	public void crearGnomos() {
		if (gnomos.size() < 4) {
			double velocidad = (Math.random() * 3);
			if (this.entorno.tiempo() % 300 == 0) {
				gnomos.add(new Gnomos(408, 55, 2.5, 0.75 * velocidad));
			}
		}
	}

	public void creartortugas() {
		if (tortugas.size() < 3) {
			int random = (int) (Math.random() * 3);
			if (random == 0) {
				tortugas.add(new Tortuga(134, 55, 2.5));
			}
			if (random == 1) {
				tortugas.add(new Tortuga(182, 55, 2.5));
			}
			if (random == 2) {
				tortugas.add(new Tortuga(550, 55, 2.5));
			}
		}
	}

	public void moverGnomos() {
		for (Gnomos gnomo : gnomos) {
			if (gnomo != null) {
				boolean estaSobreIsla = false;
				for (Isla isla : islas) {
					if (colisionPisoGnomo(gnomo, isla)) {
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
			for (Isla i : islas) {
				if (tocaisla(t, i)) {
					t.tocapiso = true;
					break;
				}
			}
		}
	}

	// check de colision gnomo-jugador
	public boolean colisionGnomoPersonaje(Gnomos gnomo, Jugador1 jugador) {
		return gnomo.bordeDer() > jugador.bordeIzq() && gnomo.bordeIzq() < jugador.bordeDer()
				&& gnomo.bordeAbajo() > jugador.bordeArriba() && gnomo.bordeArriba() < jugador.bordeAbajo();
	}

	public boolean colisionboladefuegotortuga(Tortuga t, Fuego f) {
		return t.bordeDer() > f.bordeIzq() && t.bordeIzq() < f.bordeDer() && t.bordeAbajo() > f.bordeArriba()
				&& t.bordeArriba() < f.bordeAbajo();
	}

	public boolean colisionjugadortortuga(Jugador1 j, Tortuga t) {
		return t.bordeDer() > j.bordeIzq() && t.bordeIzq() < j.bordeDer() && j.bordeAbajo() > t.bordeArriba()
				&& j.bordeArriba() < t.bordeAbajo();
	}

	public boolean colisionGnomoTortuga(Gnomos gnomo, Tortuga tortuga) {
		return gnomo.bordeDer() > tortuga.bordeIzq() && gnomo.bordeIzq() < tortuga.bordeDer()
				&& gnomo.bordeAbajo() > tortuga.bordeArriba() && gnomo.bordeArriba() < tortuga.bordeAbajo();
	}

	public void destruirtortugas() {
		for (Tortuga t : tortugas) {
			for (Fuego f : fuegos) {
				if (colisionboladefuegotortuga(t, f)) {
					t.murio = true;
					f.murio = true;
				}
			}
		}
		for (int i = 0; i < tortugas.size(); i++) {
			if (tortugas.get(i).murio) {
				tortugas.remove(i);
			}
		}
		for (int i = 0; i < fuegos.size(); i++) {
			if (fuegos.get(i).murio) {
				fuegos.remove(i);
			}
		}
	}

	public void destruirGnomos() {
		for (Tortuga t : tortugas) {
			for (Gnomos g : gnomos) {
				if ((colisionGnomoTortuga(g, t))) {
					g.murio = true;
					gnomoPer = gnomoPer + 1;
				}
			}
		}
		for (Gnomos g : gnomos) {
			if ((colisionGnomoPersonaje(g, jugador1))) {
				g.murio = true;
				gnomoSalv = gnomoSalv + 1;
			}
		}
		for (Gnomos g : gnomos) {
			if (g.y > 800) {
				g.murio = true;
				gnomoPer = gnomoPer + 1;
			}
		}
		for (int i = 0; i < gnomos.size(); i++) {
			if (gnomos.get(i).murio) {
				gnomos.remove(i);
			}
		}
	}

	public void muertejugador() {
		if (jugador1 != null && jugador1.y >= 600) {
			skillissue = true;
		}
		for (Tortuga tortuga : tortugas) {
			if (colisionjugadortortuga(jugador1, tortuga)) {
				skillissue = true;
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
			this.entorno.cambiarFont("Calibri", 50, Color.yellow);
			entorno.escribirTexto("Felicidades, ganaste!", 153, entorno.alto() / 2);
		} else if (perdiste) {
			this.entorno.cambiarFont("Calibri", 50, Color.red);
			entorno.escribirTexto("Perdiste, los gnomos murieron", entorno.ancho() - (entorno.ancho() - 100),
					entorno.alto() / 2);
		} else if (skillissue) {
			this.entorno.cambiarFont("Calibri", 50, Color.red);
			entorno.escribirTexto("Perdiste, tenes skill issue", entorno.ancho() - (entorno.ancho() - 100),
					entorno.alto() / 2);
		} else {
			casa.dibujar(entorno);
			jugador1.dibujar(entorno);
			creartortugas();
			for (Tortuga t : tortugas) {
				t.dibujar(entorno);
				t.moverVer();
			}
			// SET DE LOS MOVIMIENTOS Y CREACION
			jugador1.MoverVer();
			moverGnomos();
			crearGnomos();
			creartortugas();
			movertortugas();
			destruirGnomos();
			destruirtortugas();
			muertejugador();
			this.entorno.cambiarFont("Calibri", 15, Color.black);
			entorno.escribirTexto("Gnomos salvados: " + gnomoSalv, 10, 20);
			entorno.escribirTexto("Gnomos perdidos: " + gnomoPer, 10, 30);
			for (Tortuga t : tortugas) {
				t.dibujar(entorno);
			}
			if (gnomoSalv >= 10) {
				gnomos.clear();
				tortugas.clear();
				jugador1 = null;
				islas.clear();
				ganaste = true;
			}
			if (gnomoPer >= 10) {
				gnomos.clear();
				tortugas.clear();
				jugador1 = null;
				islas.clear();
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
				gnomo.dibujar(entorno);

			}
			// GENERACION DE TORTUGAS
			for (Tortuga t : tortugas) {

				for (Isla i : islas) {
					if (tocaisla(t, i)) {

						if (tocaislaBorde(t, i)) {
							t.tocapiso = true;
							t.moverInicial(1, entorno);

						} else {

							t.tocapiso = true;
							t.cambiarDireccion(1, entorno);
						}
					}
				}
			}
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
		}
		// AYUDA PARA LAS COORDENADAS DEL MOUSE Y SET DEL TIEMPO
		// entorno.escribirTexto(""+entorno.getFrames(),200,20);
		this.entorno.cambiarFont("Calibri", 15, Color.black);
		entorno.escribirTexto("" + (entorno.tiempo() / 1000), 200, 20); // Esto no lo borren, lo estoy usando para
																		// guiarme por la pantalla. Att: Me.
		entorno.escribirTexto("mouse coord x: " + entorno.mouseX(), 680, 20); // Tambien aprobecho para decir que si
																				// encuentran algo sin mucho sentido o
																				// que no esta terminado borrenlo, hay
																				// cosas que hago
		entorno.escribirTexto("mouse coord y: " + entorno.mouseY(), 680, 30); // y me olvido de borrar (la chucha de
																				// Test no la borren xd)
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}