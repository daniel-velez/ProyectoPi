/*
 * Programacion Interactiva
 * Mini proyecto 2: Juego de batalla naval.
 */
package batallaNaval;

/**
 * Enum TipoBarco: enum utilizado para obtener cada tipo de barco y su correspondiente tamaño.
 */
public enum TipoBarco {
    Portavion(3), Submarino(2), Destructor(4), Fragata(1);

    public final int size;

    private TipoBarco(int size) {
        this.size = size;
    }
}