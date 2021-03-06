/*
 * Programacion Interactiva
 * Mini proyecto 4: Juego de Blackjack.
 */
package comunes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import comunes.Carta.Palos;

/**
 * Clase que modela una baraja de cartas.
 */
public class Baraja {
	private List<Carta> mazo;
	private Random aleatorio;

	/**
	 * Instantiates a new Baraja.
	 */
	public Baraja() {
		this.aleatorio = new Random();
		this.mazo = new ArrayList<Carta>();

		for (Palos palo : Carta.Palos.values()) {
			for (int i = 1; i <= 13; i++)
				mazo.add(new Carta(i, palo));
		}
	}

	/**
	* @return una carta del mazo.
	*/
	public Carta getCarta() {
		int index = aleatorio.nextInt(mazo.size());
		return mazo.remove(index); //elimina del mazo la carta usada
	}

	/**
	* Agrega la lista de cartas ingresada al mazo.
	* @param cartas lista de cartas para agregar al mazo.
	*/
	public void devolverCartas(List<Carta> cartas) {
		mazo.addAll(cartas);
	}
}
