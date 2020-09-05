/*
 * Programación Interactiva
 * Autor: Daniel Felipe Vélez C. - 1924306
 * email: daniel.cuaical@correounivalle.edu.co
 * Caso 1: Juego Craps
 * 23-08-2020
 */

package craps;
import java.util.Scanner;

// TODO: Auto-generated Javadoc
/**
 * The Class VistaConsola. Clase encargada de realizar las operaciones de entrada/salida por linea de comandos usando un objeto Scanner.
 */
public class VistaConsola {
	
	private ControlCraps controlCraps;
	private String respuesta;
	private Scanner in;
	
	/**
	 * Instantiates a new vista consola. Constructor de la clase. Se encarga de dar valores iniciales a los atributos del objeto.
	 */
	public VistaConsola() {
		controlCraps = new ControlCraps();
		in = new Scanner(System.in);
	}
	
	/**
	 * Iniciar juego. Inicia la ronda de juego.
	 */
	public void iniciarJuego() {
		System.out.println("¿Quieres  lanzar los dados? [y/n]");
		respuesta = in.nextLine();
		
		if(respuesta.equalsIgnoreCase("y")) {
			//inicia juego
			controlCraps.calcularTiro();
			System.out.printf("Dado 1 = %d \nDado 2 = %d \nTiro = %d \n", controlCraps.getCarasDados()[0],
																			controlCraps.getCarasDados()[1],
																			controlCraps.getTiro());
			controlCraps.determinarEstadoJuego();
			
			switch(controlCraps.getEstado()) {
				
				case 1:
					System.out.println("¡Tu ganas!");
					break;
					
				case 2:
					System.out.println("Pierdes");
					break;
					
				case 3: //ronda de punto
					
					System.out.printf("Has establecido punto = %d \nDebes lanzar nuevamente. \n", controlCraps.getPunto());
					
					while(controlCraps.getEstado() == 3) {
						
						System.out.println("¿Desea lanzar los dados? [y/n]");
						respuesta = in.nextLine();
						
						if(respuesta.equalsIgnoreCase("y")) {
							
							controlCraps.calcularTiro();
							System.out.printf("Dado 1 = %d \nDado 2 = %d \nTiro = %d \n", controlCraps.getCarasDados()[0],
																						  controlCraps.getCarasDados()[1],
																						  controlCraps.getTiro());
							controlCraps.determinarEstadoJuego();
							
						}
						else {
							System.out.println("¡Abandonaste el juego!");
						}
					}
					if(controlCraps.getEstado() == 1) {
						System.out.println("Has logrado el punto, ganaste.");
					}
					else {
						System.out.println("Perdiste.");
					}
					break;
			}
			seguirJuego();
		}
		else if(respuesta.equalsIgnoreCase("n")) {
			System.out.println("¡Saliste del juego!");
			controlCraps.setRetiro();
		}
	}
	
	/**
	 * Seguir juego. Inicia una nueva ronda de juego.
	 */
	private void seguirJuego() {
		System.out.println("Quieres jugar otra ronda? [y/n]");
		respuesta = in.nextLine();
		
		if(respuesta.equalsIgnoreCase("y")) {
			iniciarJuego();
		}
		else {
			System.out.println("Adios.");
		}
	}
}
