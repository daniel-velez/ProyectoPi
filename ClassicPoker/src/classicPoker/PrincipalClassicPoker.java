/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene el metodo main
 * e inicia el programa.
 */
public class PrincipalClassicPoker {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

        /*
        EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				//PokerView myGame = new PokerView();
			}
        });
        */
        List <Carta> cartas = new ArrayList<Carta>();
        cartas.add(new Carta(10, Palos.corazones));
        cartas.add(new Carta(11, Palos.corazones));
        cartas.add(new Carta(12, Palos.corazones));
        cartas.add(new Carta(13, Palos.corazones));
        cartas.add(new Carta(14, Palos.corazones));

        int manoPoker = PokerRules.determinarMano(cartas);
        System.out.println("La mano es: " + manoPoker);
	}
}
