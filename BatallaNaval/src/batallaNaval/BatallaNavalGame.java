/*
 * Programación Interactiva
 * Autor: Julián Andrés Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Vélez Cuaical - 1924306
 * Mini proyecto 2: Juego de batalla naval.
 */
package batallaNaval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.Point;

public class BatallaNavalGame {
    public enum Jugador {
        human, AI
    }

    //! revisar inicializacion el linea en los videos de la profe
    //! revisar comentarios
    private Map<Jugador, Map<Point, Barco>> mapa = new HashMap<>(); // mapa de los jugadores
    private Map<Jugador, Integer> numeroDeBarcos = new HashMap<>(); // numero de barcos a flote
    private Map<TipoBarco, Integer> navySize = new HashMap<>(); // numero de barcos de cada tipo
    private int mapSize;
    private boolean started;

    // #--------------------------------------------------------------------------------------------------------------------------------------

    public BatallaNavalGame(int mapSize) {
        mapa.put(Jugador.human, new HashMap<>());
        mapa.put(Jugador.AI, new HashMap<>());
        this.mapSize = mapSize;
        this.started = false;

        //  se asignan los tipos de barcos y la cantidad de cada uno.
        navySize.put(TipoBarco.Portavion, 0);
        navySize.put(TipoBarco.Submarino, 0);
        navySize.put(TipoBarco.Destructor, 2);
        navySize.put(TipoBarco.Fragata, 1);

        numeroDeBarcos.put(Jugador.human, 3);
        numeroDeBarcos.put(Jugador.AI, 3);

        inicializarBarcosAI();
    }

    // verificar que la posicion del barco este libre en el mapa -> tarea asignada a vista
    public void ubicarBarco(Jugador player, Point location, Orientation orientacion, TipoBarco tipo) {
        Barco barco = new Barco(tipo, location, orientacion);

        // ubicar el barco en el mapa
        List<Point> casillasBarco = Barco.ubicarBarco(tipo, location, orientacion);
        for (Point point : casillasBarco)
            mapa.get(player).put(point, barco);
    }

    private void inicializarBarcosAI() {
        for (TipoBarco tipo : navySize.keySet())
            ubicarBarcoAleatoriamente(tipo, navySize.get(tipo));
    }

    private void ubicarBarcoAleatoriamente(TipoBarco tipo, int cantidad) {
        while (cantidad > 0) {
            Random aleatorio = new Random();
            Point casilla = new Point(aleatorio.nextInt(mapSize), aleatorio.nextInt(mapSize));
            Orientation orientacion = Orientation.values()[aleatorio.nextInt(4)];
            if (validarUbicacion(Barco.ubicarBarco(tipo, casilla, orientacion))) {
                ubicarBarco(BatallaNavalGame.Jugador.AI, casilla, orientacion, tipo);
                cantidad--;
            }
        }
    }

    private boolean validarUbicacion(List<Point> puntos) {
        // verificar que el barco no se salga del mapa.
        for (Point punto : puntos)
            if (!(punto.getX() < mapSize && punto.getX() >= 0 && punto.getY() < mapSize && punto.getY() >= 0))
                return false;

        // verificar que la posicion no este ocupada por otro barco.
        for (Point punto : puntos)
            if (mapa.get(Jugador.AI).get(punto) != null)
                return false;

        return true;
    }

    /**
     * 
     * @param player El jugador al que le disparan
     * @param location
     * @return
     */
    public Map<Point, EstadoCasilla> disparar(Jugador player, Point location) {
        Barco barco = mapa.get(player).get(location);
        if (barco != null) {
            Map<Point, EstadoCasilla> casillas = barco.nosDisparan(location);
            // confirmar que el barco fue completamente hundido
            if (barco.nosHudieron())
                numeroDeBarcos.replace(player, numeroDeBarcos.get(player) - 1); // se le resta un barco al jugador.
            return casillas;
        } else
            return new HashMap<Point, EstadoCasilla>() {
                {
                    put(location, EstadoCasilla.Disparo);
                }
            };
    }

    //! no se tiene en cuenta que en esa posicion no se haya disparado antes
    public Map<Point, EstadoCasilla> disparar(Jugador player) {
        //! revisar vvv
        //! el contador de barcos puede llegar a -1
        return disparar(player, new Point((int) (Math.random() * mapSize), (int) (Math.random() * mapSize)));
    }

    public Jugador determinarJuego() {
        if (numeroDeBarcos.get(Jugador.human) == 0)
            return Jugador.AI;
        if (numeroDeBarcos.get(Jugador.AI) == 0)
            return Jugador.human;
        return null;
    }

    public void reiniciar() {
        mapa.replace(Jugador.human, new HashMap<>());
        mapa.replace(Jugador.AI, new HashMap<>());
        this.started = false;

        navySize.replace(TipoBarco.Portavion, 0);
        navySize.replace(TipoBarco.Submarino, 0);
        navySize.replace(TipoBarco.Destructor, 2);
        navySize.replace(TipoBarco.Fragata, 1);

        numeroDeBarcos.replace(Jugador.human, 3);
        numeroDeBarcos.replace(Jugador.AI, 3);

        inicializarBarcosAI();
    }

    // #---------------------------------------------------------------------------
    // # AUXILIARES
    // #---------------------------------------------------------------------------

    public void play() {
        started = true;
    }

    public boolean haEmpezado() {
        return started;
    }

    public Map<Point, Barco> getMap(Jugador player) {
        return mapa.get(player);
    }

    public int getMapSize() {
        return mapSize;
    }

    public void restarUnBarco(TipoBarco tipo) {
        navySize.replace(tipo, navySize.get(tipo) - 1);
    }

    public int getCantidadBarco(TipoBarco tipo) {
        return navySize.get(tipo);
    }

    public TipoBarco cambiarTipoBarco() {
        for (TipoBarco tipo : navySize.keySet()) {
            if (navySize.get(tipo) > 0)
                return tipo;
        }
        return null;
    }

}
