/*
 * Programacion Interactiva
 * Mini proyecto 3: Juego de poker clasico.
 */
package classicPoker;

import pokerView.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.management.timer.TimerNotification;
import javax.swing.JButton;

/**
 * Clase que modela un jugador.
 */
public class Jugador {

    private int dinero;
    private String nombre;
    private List<Carta> mano;
    private TipoJugador tipo;
    private boolean retirado;

    private PokerView pokerView;
    private JManoPanel manoPanel;
    private MazoDeCartas mazo;

    private Random aleatorio;

    /**
     * Instantiates a new jugador.
     * @param dinero el dinero del jugador
     * @param tipo el tipo de jugador (simulado o usuario). 
     */
    public Jugador(int dinero, TipoJugador tipo, MazoDeCartas mazo, String nombre) {
        this.dinero = dinero;
        this.tipo = tipo;
        this.retirado = false;
        this.mano = new ArrayList<Carta>();
        this.mazo = mazo;
        this.nombre = nombre;
        this.aleatorio = new Random();
    }

    /**
     * Define el atributo pokerView del jugador.
     * @param pokerView la vista para la relación conoce un.
     */
    public void defineView(PokerView pokerView) {
        this.pokerView = pokerView;
    }

    /**
     * Aporta la cantidad de dinero a la mesa de apuesta.
     * @param cantidad la cantidad de dinero.
     * @return la cantidad de dinero de la apuesta, -1 si no tiene suficiente dinero.
     */
    public int aportar(int cantidad) {
        if (dinero >= cantidad) {
            dinero -= cantidad;
            pokerView.updateMoney(this);
            return cantidad;
        } else 
            return -1; // No tiene suficiente dinero.
    }

    /**
     * Agrega la lista de cartas a la mano del jugador.
     * @param cartas
     */
    public void recibirCartas(List<Carta> cartas) {
        mano.addAll(cartas);
        if (getTipo() == TipoJugador.Usuario) {
            manoPanel.descubrirCartas(mano);
            manoPanel.setOrdenarCartas(true);
        }
        else
            manoPanel.taparCartas();
    }

    /**
     * Simula la decision de apostar.
     * @return el valor de la apuesta, 0 en caso de pasar sin apostar.
     * @throws InterruptedException
     */
    public int apostar() throws InterruptedException {
        if (tipo == TipoJugador.Usuario) 
            return aportar(pokerView.apostar());

        pokerView.showMessage(nombre + " está pensando su apuesta...", TimeControl.JUGADOR_PENSANDO_APUESTA);

        int randomDecision = aleatorio.nextInt(2) + 1;
        if (randomDecision == 1) { //apostar
            int nuevaApuesta = aportar(aleatorio.nextInt(dinero / 10) / 100 * 100);
            pokerView.showMessage(nombre + " ha apostado $" + nuevaApuesta, TimeControl.JUGADOR_TOMA_DECISION);
            return nuevaApuesta;
        }
        pokerView.showMessage(nombre + " ha cedido el turno", TimeControl.JUGADOR_TOMA_DECISION);
        return 0; //pasar
    }

    /**
     * Simula la decision de apostar.
     * @param carrier valor para igualar la apuesta.
     * @param ultimaRonda true si es la ultima ronda, false en caso contrario.
     * @return valor de la apuesta o 0 en caso de pasar, retirarse.
     * @throws InterruptedException
     */
    public int apostar(int valorParaIgualar, boolean ultimaRonda) throws InterruptedException {

        if (tipo == TipoJugador.Usuario) {
            if (ultimaRonda) {
                int valorApuesta = aportar(pokerView.apostar(valorParaIgualar, true));
                return valorApuesta;
            }
            else {
                int valor = aportar(pokerView.apostar(valorParaIgualar, false));
                if (valor > getDinero())
                    return 0;
                return valor;
            }
        }

        pokerView.showMessage(nombre + " está pensando su apuesta...", TimeControl.JUGADOR_PENSANDO_APUESTA);

        int randomDecision = ultimaRonda ? aleatorio.nextInt(12) + 1 : aleatorio.nextInt(20) + 1;
        
        if (randomDecision >= 2 && randomDecision <= 12) { //igualar
            int valorApuesta = aportar(valorParaIgualar);

            if (valorApuesta == -1) //no tiene suficiente dinero.
                return retirarseRondaApuesta();
            
            pokerView.showMessage(nombre + " ha igualado la apuesta", TimeControl.JUGADOR_TOMA_DECISION);
            return valorApuesta;
        }
        else if (randomDecision > 12 && randomDecision <=20) { //aumentar
            int nuevaApuesta = aportar(aleatorio.nextInt(dinero / 10) / 100 * 100);
            if (nuevaApuesta == -1) //no tiene suficiente dinero
                return retirarseRondaApuesta();
            pokerView.showMessage(nombre + " ha incrementado la apuesta en $" + nuevaApuesta,
                    TimeControl.JUGADOR_TOMA_DECISION);
            return nuevaApuesta + valorParaIgualar;
        }
        //retirarse 
        return retirarseRondaApuesta();
    }

    /**
     * Simula la decision de retirarse.
     */
    public void retirarse() {
        mazo.descartar(mano);
        mano.clear();
        manoPanel.descartar();
        manoPanel.setOrdenarCartas(false);
        retirado = true;
    }

    /**
     * Simula la decision de descartar.
     * @return lista de cartas a descartar.
     * @throws InterruptedException
     */
    public void descartar() throws InterruptedException {
        if (tipo == TipoJugador.Usuario) {
            manoPanel.setOrdenarCartas(false);
            pokerView.descartar();

            List <Integer> cartasADescartar = manoPanel.getCartasSeleccionadas();
            for (Integer i : cartasADescartar) 
                manoPanel.descartar(i);
            
            String cantidad = cartasADescartar.size() == 1 ? " carta" : " cartas";
            pokerView.showMessage("Has descartado " +  cartasADescartar.size() + cantidad, TimeControl.TIEMPO_DE_DESCARTE);

            for (int i : cartasADescartar) {
                mazo.descartar(mano.remove(i));
                mano.add(i, mazo.sacarCarta());
            }
            manoPanel.descubrirCartas(mano); 
            manoPanel.setOrdenarCartas(true);
            return;
        }

        List<Integer> cartas = IntStream.range(0, 5).boxed().collect(Collectors.toList());
        int cartasADesechar = aleatorio.nextInt(5);

        if (cartasADesechar == 0) {
            pokerView.showMessage(nombre + " continua sin descartar", TimeControl.TIEMPO_DE_DESCARTE);
            return;
        }

        for (int i = 0; i < 5 - cartasADesechar; i++)
            cartas.remove(aleatorio.nextInt(cartas.size()));

        for (int i = 0; i < cartasADesechar; i++)
            manoPanel.descartar(cartas.get(i));

        pokerView.showMessage(nombre + " ha descartado " + cartasADesechar + " cartas", TimeControl.TIEMPO_DE_DESCARTE);

        for (int i = 0; i < cartasADesechar; i++) {
            mazo.descartar(mano.remove((int) cartas.get(i)));
            mano.add(cartas.get(i), mazo.sacarCarta());
        }
        manoPanel.taparCartas();
    }

    /**
     * @return true si el jugador se ha retirado.
     */
    public boolean seHaRetirado() {
        return retirado;
    }

    /**
     * Simula la decision de retirarse y muestra en pantalla el nombre de quien se ha retirado.
     * @return 0 indicando que el jugador no ha apostado.
     * @throws InterruptedException
     */
    private int retirarseRondaApuesta() throws InterruptedException {
        retirarse();
        pokerView.showMessage(nombre + " se ha retirado", TimeControl.JUGADOR_TOMA_DECISION);
        return 0;
    }

    /**
     * Muestra las cartas del jugador por medio de manoPanel.
     */
    public void descubrirCartas() {
        manoPanel.descubrirCartas(mano);
    }

    /**
     * Agrega el dinero especificado al monto de dinero del jugador.
     * @param dinero
     */
    public void recibirDinero(int dinero) {
        this.dinero += dinero;
        pokerView.updateMoney(this);
    }

    /**
     * Pone las cartas del jugador en el mazo de descarte y establece el valor de retirado en false.
     */
    public void jugarDeNuevo() {
        this.mazo.descartar(mano);
        this.mano.clear();
        this.retirado = false;
        manoPanel.setOrdenarCartas(true);
    }


    // #---------------------------------------------------------------------------
    // # GETTERS
    // #---------------------------------------------------------------------------

    /**
     * @return el dinero del jugador.
     */
    public Integer getDinero() {
        return dinero;
    }

    /**
     * @return el nombre del jugador.
     */
    public String getName() {
        return nombre;
    }

    /**
     * @return el tipo de jugador.
     */
    public TipoJugador getTipo() {
        return tipo;
    }

    /**
     * @return la mano del jugador.
     */
    public List<Carta> getMano() {
        return mano;
    }

    // #---------------------------------------------------------------------------
    // # SETTERS
    // #---------------------------------------------------------------------------

    /**
     * Establece el panel asociado al jugador.
     * @param manoPanel 
     */
    public void setManoPanel(JManoPanel manoPanel) {
        this.manoPanel = manoPanel;
    }

    /**
     * Intercambia la posicion de dos cartas en la lista de cartas del jugador.
     * @param index1
     * @param index2
     */
    public void cambiarCartas(int index1, int index2) {
        Carta carta1 = mano.get(index1);
        Carta carta2 = mano.get(index2);

        mano.set(index1, carta2);
        mano.set(index2, carta1);

        manoPanel.descubrirCartas(mano);
    }

    /**
    * enum que contiene los tipos de jugador (simulado o usuario).
    */
    public enum TipoJugador {
        Simulado, Usuario
    }
}
