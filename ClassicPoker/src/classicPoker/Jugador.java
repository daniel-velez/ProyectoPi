package classicPoker;

import java.util.List;

public class Jugador {
    private int dinero;
    private String nombre;
    private List<Carta> mano;
    private TipoJugador tipo;

    public Jugador(int dinero, TipoJugador tipo) {
        this.dinero = dinero;
        this.tipo = tipo;
    }

    /**
     * Aporta la cantidad de dinero a la mesa de apuesta
     * @param cantidad la cantidad de dinero
     */
    public void aportar(int cantidad) {
        dinero -= cantidad;
    }

    public void recibirCartas(List<Carta> cartas) {
        mano.addAll(cartas);
    }

    /**
     * Simula la decicion de apostar
     * @param carrier valor para igualar la apuesta
     * @return valor de la apuesta o null en caso de pasar, retirarse
     */
    public int apostar(int carrier) {

    }

    /**
     * Simula la decicion de descartar
     * @return lista de cartas a descartar
     */
    public List<Carta> descartar() {

    }

    // #---------------------------------------------------------------------------
    // # GETTERS
    // #---------------------------------------------------------------------------

    public int getDinero() {
        return dinero;
    }

    public String getName() {
        return nombre;
    }

    public TipoJugador getTipo() {
        return tipo;
    }
}

enum TipoJugador {
    Simulado, Usuario
}