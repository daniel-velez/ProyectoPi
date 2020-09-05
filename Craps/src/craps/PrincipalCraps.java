/*
 * Programación Interactiva
 * Autor: Daniel Felipe Vélez C. - 1924306
 * email: daniel.cuaical@correounivalle.edu.co
 * Caso 1: Juego Craps
 * 23-08-2020
 */

package craps;

import java.awt.EventQueue;


// TODO: Auto-generated Javadoc
/**
 * The Class PrincipalCraps. Clase que contiene el método main e inicia el programa.
 */
public class PrincipalCraps {

	/**
	 * The main method. Método principal en Java.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//VistaConsola consola = new VistaConsola();
		//consola.iniciarJuego();
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				VistaGUIGridBagLayout myGame = new VistaGUIGridBagLayout();
			}
		});
	}

}
