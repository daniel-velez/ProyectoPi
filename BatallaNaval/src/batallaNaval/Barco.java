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
import java.awt.Point;

/**
 * The class Barco
 */
public class Barco {
    private Map<Point, EstadoCasilla> partesDelBarco;
    private TipoBarco tipo;
    private Point location;
    private Orientation orientacion;

    /**
     * 
     * @param tipo
     * @param location
     * @param orientacion
     */
    public Barco(TipoBarco tipo, Point location, Orientation orientacion) {
        this.partesDelBarco = new HashMap<Point, EstadoCasilla>();
        this.tipo = tipo;
        this.location = location;
        this.orientacion = orientacion;
        List<Point> puntos = ubicarBarco(tipo, location, orientacion);

        for (int i = 0; i < tipo.size; i++) {
            partesDelBarco.put(puntos.get(i), EstadoCasilla.aFlote);
        }
    }

    /**
     * 
     * @param tipo
     * @param location
     * @param orientacion
     * @return
     */
    public static List<Point> ubicarBarco(TipoBarco tipo, Point location, Orientation orientacion) {
        List<Point> casillasBarco = new ArrayList<Point>();
        for (int i = 0; i < tipo.size; i++)
            casillasBarco.add((Point) location.clone());

        if (orientacion == Orientation.ARB)
            for (int i = 0; i < tipo.size; i++)
                casillasBarco.get(i).translate(0, -i);

        if (orientacion == Orientation.DER)
            for (int i = 0; i < tipo.size; i++)
                casillasBarco.get(i).translate(i, 0);

        if (orientacion == Orientation.ABJ)
            for (int i = 0; i < tipo.size; i++)
                casillasBarco.get(i).translate(0, i);

        if (orientacion == Orientation.IZQ)
            for (int i = 0; i < tipo.size; i++)
                casillasBarco.get(i).translate(-i, 0);

        return casillasBarco;
    }

    /**
     * 
     * @return
     */
    public boolean nosHudieron() {
        for (EstadoCasilla estado : partesDelBarco.values())
            if (estado == EstadoCasilla.aFlote)
                return false;
        return true;
    }

    /**
     * 
     * @param location
     * @return
     */
    public Map<Point, EstadoCasilla> nosDisparan(Point location) {
        partesDelBarco.replace(location, EstadoCasilla.Tocado);

        if (nosHudieron())
            for (Point punto : partesDelBarco.keySet())
                partesDelBarco.replace(punto, EstadoCasilla.Hundido);

        return partesDelBarco;
    }

    /**
     * 
     * @return
     */
    public TipoBarco getTipo() {
        return tipo;
    }

    /**
     * 
     * @return
     */
    public Orientation getOrientacion() {
        return orientacion;
    }

    /**
     * 
     * @return
     */
    public List<Point> getPoints() {
        return ubicarBarco(tipo, location, orientacion);
    }
}
