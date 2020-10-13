/*
 * Programación Interactiva
 * Autor: Julián Andrés Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Vélez Cuaical - 1924306
 * Mini proyecto 2: Juego de batalla naval.
 */
package batallaNaval;

/**
 * Enum TipoBarco: enum utilizado para obtener cada tipo de barco y su correspondiente tamaño.
 */
public enum TipoBarco {
    //Portavion(4), Submarino(3), Destructor(2), Fragata(1);
    Portavion(3), Submarino(2), Destructor(4), Fragata(1);

    public final int size;

    private TipoBarco(int size) {
        this.size = size;
    }
}