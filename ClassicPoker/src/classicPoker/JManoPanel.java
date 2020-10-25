package classicPoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
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

        for (int i = 0; i < 5; i++)
            JMano.put(new JButton(), null);

        initGUI();
    }

    private void initGUI() {
        listener = new Escucha();

        nombre = new JLabel(jugador.getName());
        dinero = new JLabel(Integer.toString(jugador.getDinero()));

        this.add(nombre);
        this.add(dinero);

        for (JButton JCarta : JMano.keySet()) {
            JCarta.setSize(new Dimension(60, 80));
            JCarta.setMinimumSize(new Dimension(60, 80));
            JCarta.setPreferredSize(new Dimension(60, 80));
            JCarta.addActionListener(listener);
            this.add(JCarta);
        }
    }

    public void mostrarDinero() {
        dinero.setText(jugador.getDinero().toString());
    }

    public void descubrirCartas() {
        List<Carta> mano = jugador.getMano();
        List<JButton> JCartas = new ArrayList(JMano.keySet());
        for (int i = 0; i < 5; i++)
            JMano.replace(JCartas.get(i), mano.get(i));

        Carta carta;
        for (JButton JCarta : JMano.keySet()) {
            carta = JMano.get(JCarta);
            JCarta.setText(carta.numero + " " + carta.palo.toString().charAt(0));
        }
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
