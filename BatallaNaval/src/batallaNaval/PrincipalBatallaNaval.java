/*
 * Programación Interactiva
 * Autor: Julián Andrés Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Vélez Cuaical - 1924306
 * Mini proyecto 2: Juego de batalla naval.
 */
package batallaNaval;

import java.awt.EventQueue;

/**
 * The Class PrincipalBatallaNaval. Clase que contiene el metodo main
 * e inicia el programa.
 */
public class PrincipalBatallaNaval {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				BatallaNavalVista myGame = new BatallaNavalVista();
			}
		});

	}
}
