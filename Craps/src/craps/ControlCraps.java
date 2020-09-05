/*
 * Programación Interactiva
 * Autor: Daniel Felipe Vélez C. - 1924306
 * email: daniel.cuaical@correounivalle.edu.co
 * Caso 1: Juego Craps
 * 23-08-2020
 */
package craps;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlCraps. Clase que maneja la lógica del juego Craps. Determina el valor del tiro, el estado del juego, el valor del punto, etc.
 */
public class ControlCraps {
	
	private Dado dado1;
	private Dado dado2;
	private int tiro;
	private int punto;
	private int estado;
	private int[] carasDados;
	private boolean lanzamiento;
	
	/**
	 * Instantiates a new control craps. Constructor de la clase. Se encarga de dar valores iniciales a los atributos del objeto.
	 */
	public ControlCraps() {
		dado1 = new Dado();
		dado2 = new Dado();
		lanzamiento = true; //ronda de tiro
		
		carasDados= new int[2];
	}
	
	/**
	 * Calcular tiro. Simula el lanzamiento de los dados y establece el valor del tiro.
	 */
	public void calcularTiro() {
		carasDados[0] = dado1.getCaraVisible();
		carasDados[1] = dado2.getCaraVisible();
		tiro = carasDados[0] + carasDados[1];
	}
	
	/**
	 * Determinar estado juego. Determina el estado del juego: estado = 1 -> ganar, estado = 2 -> perder, estado = 3 -> ronda punto.
	 */
	public void determinarEstadoJuego() {
		if(lanzamiento) {
			//ronda de tiro
			if(tiro == 7 || tiro == 11) {
				estado = 1;	//ganar
			}
			else if(tiro == 2 || tiro == 3 || tiro == 12) {
				estado = 2; //perder
			}
			else {
				//entra a ronda de punto
				estado = 3; 
				punto = tiro;
				lanzamiento = false;
			}
		}
		else {
			rondaPunto();
		}
		
	}
	
	/**
	 * Ronda punto. Establece el estado del juego en la ronda punto.
	 */
	private void rondaPunto() { //metodo privado (no va a ser llamado desde una clase externa).
		if(tiro == punto) {
			estado = 1; //ganar
			lanzamiento = true;
		}
		else if(tiro == 7) {
			estado = 2; //perder
			lanzamiento = true;
		}
	}
	
	/**
	 * Sets the retiro. Establece el estado del juego si el usuario se retira del juego.
	 */
	public void setRetiro() {
		estado = 2;
		lanzamiento = true;
	}

	/**
	 * Gets the tiro.
	 *
	 * @return tiro
	 */
	public int getTiro() {
		return tiro;
	}

	/**
	 * Gets the punto.
	 *
	 * @return punto
	 */
	public int getPunto() {
		return punto;
	}

	/**
	 * Gets the estado.
	 *
	 * @return estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * Gets the caras dados.
	 *
	 * @return carasDados
	 */
	public int[] getCarasDados() { //retorna un arreglo
		return carasDados;
	}
	
	
	
}
