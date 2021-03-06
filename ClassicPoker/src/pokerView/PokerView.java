/*
 * Programacion Interactiva
 * Mini proyecto 3: Juego de poker clasico.
 */

package pokerView;

import classicPoker.*;
import java.awt.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Clase encargada de representar el juego y de realizar las operaciones de E/S
 * por medio de un JFrame.
 */
public class PokerView extends JFrame {

    private List<Jugador> jugadores;
    private Map<Jugador, JManoPanel> playersView;
    private JLabel textBig, textSmall;
    private boolean loading;

    private List<JButton> fichas;
    private JButton apostar, pasar, igualar, aumentar, descartar;
    private JButton retirarse, ayuda, salir, saltar, jugarDeNuevo, mostrarCartas;

    private Jugador jugador;
    private int apuestaDelJugador;
    private int valorParaIgualar;
    private boolean jugarDeNuevoFlag = false;
    private Map<JButton, Integer> valoresFichas = new HashMap<JButton, Integer>();

    private Escucha listener;

    /**
     * Instantiates a new poker view.
     */
    public PokerView(List<Jugador> jugadores) {
        this.jugadores = jugadores;

        for (Jugador jugador : jugadores)
            if (jugador.getTipo() == Jugador.TipoJugador.Usuario)
                this.jugador = jugador;

        loading = true;

        this.setTitle("Classic Poker");
        this.setResizable(false);
        this.setIconImage(getImage("icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1031, 607));
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Muestra una imagen de fondo al inicio del juego.
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (loading)
            g.drawImage(getImage("splash.png").getImage(), 0, 30, getWidth(), getHeight() - 30, this);
    }

    /**
     * Inits the content pane.
     */
    private void initContentPane() {
        JPanel contentPane = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(getImage("background-2.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        this.setContentPane(contentPane);
    }

    /**
     * Inits the GUI.
     */
    public void initGUI() {
        loading = false;
        initContentPane();
        this.setLayout(new BorderLayout());
        JPanel botonesPanel = new JPanel(new BorderLayout());
        JPanel userInfo = new JPanel(), fichasPanel = new JPanel(), opcionesPanel = new JPanel();
        listener = new Escucha();
        textBig = new JLabel();
        textSmall = new JLabel();
        List<JManoPanel> playersView2 = new ArrayList<JManoPanel>();

        botonesPanel.setOpaque(false);
        fichasPanel.setOpaque(false);
        userInfo.setOpaque(false);
        opcionesPanel.setOpaque(false);

        // # Vista de los jugadores

        playersView = new HashMap<Jugador, JManoPanel>();
        for (Jugador jugador : jugadores) {
            playersView.put(jugador, new JManoPanel(jugador));
            jugador.setManoPanel(playersView.get(jugador));
            playersView2.add(playersView.get(jugador));
        }

        add(new GameTable(playersView2, textBig, textSmall), BorderLayout.CENTER);

        // #-------------------------------

        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.PAGE_AXIS));
        setComponentSize(userInfo, new Dimension(300, 112));
        userInfo.add(Box.createRigidArea(new Dimension(50, 30)));
        userInfo.add(playersView.get(jugadores.get(4)).getUserName());
        userInfo.add(playersView.get(jugadores.get(4)).getUserMoney());
        botonesPanel.add(userInfo, BorderLayout.LINE_START);

        // # Fichas

        fichas = new ArrayList<JButton>();

        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f100.png"))));
        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f200.png"))));
        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f500.png"))));
        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f1000.png"))));

        int[] valores = { 100, 200, 500, 1000 };
        int i = 0;
        for (JButton ficha : fichas) {
            configurarBoton(ficha, listener);
            ficha.setEnabled(false);
            valoresFichas.put(ficha, valores[i]);
            fichasPanel.add(ficha);
            i++;
        }
        botonesPanel.add(fichasPanel, BorderLayout.CENTER);

        apostar = new JButton("Apostar");
        pasar = new JButton("Pasar");
        igualar = new JButton("Igualar");
        aumentar = new JButton("Aumentar");
        retirarse = new JButton("Retirarse");
        descartar = new JButton("Descartar");
        salir = new JButton("Salir");
        saltar = new JButton("Saltar");
        ayuda = new JButton("Ayuda");
        jugarDeNuevo = new JButton("Jugar de nuevo");
        mostrarCartas = new JButton("Mostrar cartas");

        opcionesPanel.add(apostar);
        opcionesPanel.add(pasar);
        opcionesPanel.add(igualar);
        opcionesPanel.add(aumentar);
        opcionesPanel.add(descartar);
        opcionesPanel.add(retirarse);
        opcionesPanel.add(salir);
        opcionesPanel.add(jugarDeNuevo);
        opcionesPanel.add(mostrarCartas);
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setComponentSize(opcionesPanel, new Dimension(290, 102));

        igualar.addActionListener(listener);
        aumentar.addActionListener(listener);
        retirarse.addActionListener(listener);
        descartar.addActionListener(listener);

        apostar.addActionListener(listener);
        pasar.addActionListener(listener);
        jugarDeNuevo.addActionListener(listener);
        salir.addActionListener(listener);
        mostrarCartas.addActionListener(listener);

        apostar.setVisible(false);
        pasar.setVisible(false);
        igualar.setVisible(false);
        aumentar.setVisible(false);
        retirarse.setVisible(false);
        descartar.setVisible(false);
        jugarDeNuevo.setVisible(false);

        botonesPanel.add(opcionesPanel, BorderLayout.LINE_END);

        add(botonesPanel, BorderLayout.PAGE_END);

        this.pack();

        JOptionPane.showMessageDialog(this,
                "Puedes intercambiar la posición de dos cartas haciendo "
                        + "\nclick primero en una carta y luego en la otra. \n"
                        + "\nDurante la ronda de descarte al hacer click en una carta "
                        + "\nla seleccionas para descartarla, puedes descartar varias" + "\ncartas o ninguna.",
                "Instrucciones cartas", JOptionPane.INFORMATION_MESSAGE);
    }

    // #---------------------------------------------------------------------------
    // # Métodos desde control a la vista
    // #---------------------------------------------------------------------------

    /**
     * Muestra un mensaje en el texto pequeño
     * @param msg
     * @param time tiempo a mostrar el mensaje
     * @throws InterruptedException
     */
    public void showMessage(String msg, int time) throws InterruptedException {
        textSmall.setText(msg);
        revalidate();
        repaint();
        Thread.sleep(time);
    }

    /**
     * Muestra un mensaje en el texto grande
     * @param msg
     */
    public void showBigMessage(String msg) {
        textBig.setText(msg);
        textBig.revalidate();
        textBig.repaint();
    }

    /**
     * Actuliza el dinero de un jugador en la vista
     * @param player
     */
    public void updateMoney(Jugador player) {
        playersView.get(player).mostrarDinero();
        revalidate();
        repaint();
    }

    /**
     * Se encarga de gestionar la operacion de apostar para el jugador humano
     * @return apuestaDelJugador
     * @throws InterruptedException
     */
    public synchronized int apostar() throws InterruptedException {
        textSmall.setText("Es tu turno");
        apostar.setVisible(true);
        pasar.setVisible(true);
        wait();
        quitarFichas(true);
        aumentar.setText("Aumentar");
        aumentar.setVisible(false);
        apostar.setVisible(false);
        return apuestaDelJugador;
    }

    /**
     * Se encarga de gestionar la operacion de apostar para el jugador humano
     * @param valorParaIgualar
     * @param ultimaRonda indica si es la segunda pasada de la ronda de apuesta
     * @return apuestaDelJugador
     * @throws InterruptedException
     */
    public synchronized int apostar(int valorParaIgualar, boolean ultimaRonda) throws InterruptedException {
        textSmall.setText("Es tu turno");
        this.valorParaIgualar = valorParaIgualar;
        apuestaDelJugador = 0;
        igualar.setVisible(true);
        aumentar.setVisible(true);
        aumentar.setEnabled(false);
        retirarse.setVisible(true);
        quitarFichas(ultimaRonda);
        wait();
        quitarFichas(true);
        igualar.setVisible(false);
        aumentar.setVisible(false);
        retirarse.setVisible(false);

        return apuestaDelJugador;
    }

    /**
     * Se encarga de gestionar la operacion de descartar para el jugador humano
     * @throws InterruptedException
     */
    public synchronized void descartar() throws InterruptedException {
        textSmall.setText("Es tu turno");

        descartar.setVisible(true);
        pasar.setVisible(true);

        wait();

        pasar.setVisible(false);
        descartar.setVisible(false);
    }

    // #---------------------------------------------------------------------------
    // # Eventos
    // #---------------------------------------------------------------------------

    /**
     * Evento del boton apostar
     */
    private void onApostarClick() {
        apuestaDelJugador = 0;

        pasar.setVisible(false);
        apostar.setVisible(false);
        quitarFichas(false);
        aumentar.setText("Hacer apuesta");
        aumentar.setVisible(true);
        aumentar.setEnabled(false);
    }

    /**
     * Evento del boton pasar
     */
    private synchronized void onPasarClick() {
        playersView.get(jugador).getCartasSeleccionadas();
        notifyAll();
    }

    /**
     * Evento del boton igualar
     */
    private synchronized void onIgualarClick() {
        if (valorParaIgualar <= jugador.getDinero())
            apuestaDelJugador = valorParaIgualar;
        notifyAll();
    }

    /**
     * Evento del boton aumentar
     */
    private synchronized void onAumentarClick() {
        apuestaDelJugador += valorParaIgualar;
        notifyAll();
    }

    /**
     * Evento del boton jugar de nuevo
     */
    public synchronized void onJugarDeNuevoClick() {
        jugarDeNuevoFlag = true;
        notifyAll();
    }

    /**
     * Evento de los botones ficha
     * @param ficha
     */
    private void onFichaClick(JButton ficha) {
        apuestaDelJugador += valoresFichas.get(ficha);
        if ((apuestaDelJugador + valorParaIgualar) > jugador.getDinero()) {
            apuestaDelJugador -= valoresFichas.get(ficha);
        }

        aumentar.setEnabled(true);
        if (valorParaIgualar == 0)
            textSmall.setText("Tu apuesta: " + apuestaDelJugador);
        else
            textSmall.setText("Tu apuesta: " + valorParaIgualar + " + " + apuestaDelJugador);
    }

    /**
     * Evento del boton retirarse
     */
    private synchronized void onRetirarseClick() {
        jugador.retirarse();
        apuestaDelJugador = 0;
        notifyAll();
    }

    /**
     * Evento del boton descartar
     */
    private synchronized void onDescartarClick() {
        if (playersView.get(jugador).getCartasSeleccionadasSize() > 0)
            notifyAll();
        else
            textSmall.setText("No has seleccionado ninguna carta");
    }

    private void onMostrarCartasClick() {
        for (Jugador jugador : jugadores) {
            if (!jugador.seHaRetirado())
                jugador.descubrirCartas();
        }
    }

    // #---------------------------------------------------------------------------
    // # Juego
    // #---------------------------------------------------------------------------

    /**
     * Muestra el mensaje de que la ronda está iniciando
     * @throws InterruptedException
     */
    public void iniciarRonda() throws InterruptedException {
        this.jugarDeNuevoFlag = false;
        for (int i = 3; i > 0; i--)
            showMessage("La ronda iniciará en " + i, 1000);
    }

    /**
     * Espera la instruccion para jugar una nueva ronda
     */
    public synchronized boolean nuevaRonda() throws InterruptedException {
        jugarDeNuevo.setVisible(true);
        wait();
        jugarDeNuevo.setVisible(false);
        revalidate();
        repaint();
        return jugarDeNuevoFlag;
    }

    // #---------------------------------------------------------------------------
    // # Funciones auxiliares
    // #---------------------------------------------------------------------------

    /**
     * Configura un boton y le adiciona un escucha.
     * @param boton   el boton a configurar
     * @param escucha el escucha de los eventos
     */
    private void configurarBoton(JButton boton, Escucha escucha) {
        boton.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setContentAreaFilled(false);
        boton.addActionListener(escucha);
    }

    /**
     * Habilita o deshabilita los botones de las fichas.
     * @param flag
     */
    private void quitarFichas(boolean flag) {
        for (JButton ficha : fichas)
            ficha.setEnabled(!flag);
    }

    /**
     * Fija el tamaño para el componente dado.
     * @param obj el componente
     * @param size
     */
    private void setComponentSize(JPanel obj, Dimension size) {
        obj.setPreferredSize(size);
        obj.setMinimumSize(size);
        obj.setMaximumSize(size);
    }

    /**
     * Crea un ImageIcon cargando la imagen especificada. 
     * @param name el nombre de la imagen.
     * @return un ImageIcon
     */
    private ImageIcon getImage(String name) {
        return new ImageIcon(this.getClass().getResource("/images/" + name));
    }

    // #---------------------------------------------------------------------------
    // # Listener
    // #---------------------------------------------------------------------------

    /**
     * The Class Escucha. Clase interna encargada de manejar los eventos de la
     * ventana.
     */
    private class Escucha implements ActionListener {

        /**
         * Action performed.
         *
         * @param event the event
         */
        @Override
        public void actionPerformed(ActionEvent event) {

            int indexFicha = fichas.indexOf(event.getSource());
            if (indexFicha != -1)
                onFichaClick(fichas.get(indexFicha));

            if (event.getSource() == igualar)
                onIgualarClick();

            if (event.getSource() == aumentar)
                onAumentarClick();

            if (event.getSource() == retirarse)
                onRetirarseClick();

            if (event.getSource() == apostar)
                onApostarClick();

            if (event.getSource() == pasar)
                onPasarClick();

            if (event.getSource() == descartar)
                onDescartarClick();

            if (event.getSource() == jugarDeNuevo)
                onJugarDeNuevoClick();

            if (event.getSource() == mostrarCartas)
                onMostrarCartasClick();

            if (event.getSource() == salir)
                System.exit(0);
        }
    }
}
