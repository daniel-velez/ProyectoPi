package entrenamientoDeMemoria;

import java.util.ArrayList;

public class ControlEntrenamientoDeMemoria {

	private int complejidad;
	private int tiempoDeEspera;
	private int cartaGanadora;

	public ControlEntrenamientoDeMemoria() {
		complejidad = 4;
		tiempoDeEspera = 15;
	}

	public ArrayList<Integer> revolverCartas() {
		// inicializar el ArrayList<Integer> con la cantidad de imagenes a devolver
		ArrayList<Integer> cartas = new ArrayList<Integer>();
		ArrayList<Integer> seleccionadas = new ArrayList<Integer>();
		cartas.ensureCapacity(12);

		for (int j = 1; j <= 12; j++)
			cartas.add(j);

		// escoger las imagenes aleatoriamente
		for (int i = 0; i < complejidad; i++) {
			int cartaSeleccionada = (int) (Math.random() * cartas.size());
			seleccionadas.add(cartas.get(cartaSeleccionada));
			cartas.remove(cartaSeleccionada);
		}

		return seleccionadas;
	}

	public int getTiempoDeEspera() {

		return tiempoDeEspera;
	}

	public boolean determinarRonda(int cartaSeleccionada) {

		boolean estado = true;
		return estado;
	}

}
