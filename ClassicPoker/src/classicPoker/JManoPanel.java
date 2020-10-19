package classicPoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JManoPanel extends JPanel {
    private Map<JButton, Carta> JMano;
    private Jugador jugador;
    private JLabel nombre, dinero;

    private Boolean ordenarCartas;
    private List<JButton> cartasSeleccionadas;

    private Escucha listener;

    public JManoPanel(Jugador jugador) {
        this.jugador = jugador;
        this.ordenarCartas = true;
        this.cartasSeleccionadas = new ArrayList<JButton>();

        JMano = new HashMap<JButton, Carta>();
        for (Carta carta : jugador.getMano())
            JMano.put(new JButton(Integer.toString(carta.numero)), carta);

        initGUI();
    }

    private void initGUI() {
        listener = new Escucha();

        nombre = new JLabel(jugador.getName());
        dinero = new JLabel(Integer.toString(jugador.getDinero()));

        this.add(nombre);
        this.add(dinero);

        for (JButton JCarta : JMano.keySet()) {
            JCarta.addActionListener(listener);
            this.add(JCarta);
        }
    }

    public void mostrarDinero() {
        dinero.setText(Integer.toString(jugador.getDinero()));
    }

    public void descubrirCartas() {

    }

    public void taparCartas() {

    }

    public List<Carta> getCartasSeleccionadas() {
        List<Carta> cartas = new ArrayList<Carta>();
        for (JButton JCarta : cartasSeleccionadas)
            cartas.add(JMano.get(JCarta));
        return cartas;
    }

    // #---------------------------------------------------------------------------
    // # EVENTS
    // #---------------------------------------------------------------------------

    private void onJCartaClick(JButton JCarta) {
        if (ordenarCartas) {
            if (cartasSeleccionadas.size() == 0)
                cartasSeleccionadas.add(JCarta);
            else if (cartasSeleccionadas.size() == 1) {
                Carta swap = JMano.get(JCarta);
                JMano.replace(JCarta, JMano.get(cartasSeleccionadas.get(0)));
                JMano.replace(cartasSeleccionadas.get(0), swap);
            }
        } else
            cartasSeleccionadas.add(JCarta);
    }

    /**
     * The Class Escucha
     */
    private class Escucha implements ActionListener {

        /**
         * Action performed.
         * @param event the event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            for (JButton JCarta : JMano.keySet())
                if (event.getSource() == JCarta)
                    onJCartaClick(JCarta);
        }

    }

}
