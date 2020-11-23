/*
 * Programacion Interactiva
 * Autor: Julian Andres Orejuela Erazo - 1541304 
 * Autor: Daniel Felipe Velez Cuaical - 1924306
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Clase encargada de representar el juego y de realizar las operaciones de E/S
 * por medio de un JFrame.
 */
public class PokerView extends JFrame {

    private JInstruccionesPanel instrucciones;
    private List<Jugador> jugadores;
    private Map<Jugador, JManoPanel> playersView;
    private JLabel textBig, textSmall;
    private boolean loading;

    private List<JButton> fichas;
    private JButton apostar, pasar, igualar, aumentar, descartar;
    private JButton retirarse, ayuda, levantarse, saltar;

    private Jugador jugador;
    private int apuestaDelJugador;
    private int valorParaIgualar;

    private Escucha listener;

    /**
     * Instantiates a new poker view.
     */
    public PokerView(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.instrucciones = new JInstruccionesPanel();

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
     *
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
                g.drawImage(getImage("background.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBorder(new EmptyBorder(14, 0, 5, 0));
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
        userInfo.add(Box.createRigidArea(new Dimension(50, 0)));
        userInfo.add(playersView.get(jugadores.get(4)).getUserName());
        userInfo.add(playersView.get(jugadores.get(4)).getUserMoney());
        botonesPanel.add(userInfo, BorderLayout.LINE_START);

        // # Fichas

        fichas = new ArrayList<JButton>();

        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f100.png"))));
        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f200.png"))));
        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f500.png"))));
        fichas.add(new JButton(new ImageIcon(getClass().getResource("/images/f1000.png"))));

        for (JButton ficha : fichas) {
            configurarBoton(ficha, listener);
            //ficha.setEnabled(false);
            fichasPanel.add(ficha);
        }
        botonesPanel.add(fichasPanel, BorderLayout.CENTER);

        apostar = new JButton("Apostar");
        pasar = new JButton("Pasar");
        igualar = new JButton("Igualar");
        aumentar = new JButton("Aumentar");
        retirarse = new JButton("Retirarse");
        descartar = new JButton("Descartar");
        levantarse = new JButton("Levantarse");
        saltar = new JButton("Saltar");
        ayuda = new JButton("Ayuda");

        opcionesPanel.add(apostar);
        opcionesPanel.add(pasar);
        opcionesPanel.add(igualar);
        opcionesPanel.add(aumentar);
        opcionesPanel.add(descartar);
        opcionesPanel.add(retirarse);
        opcionesPanel.add(levantarse);
        opcionesPanel.add(ayuda);
        setComponentSize(opcionesPanel, new Dimension(300, 112));

        igualar.addActionListener(listener);
        aumentar.addActionListener(listener);
        retirarse.addActionListener(listener);
        descartar.addActionListener(listener);

        apostar.addActionListener(listener);
        pasar.addActionListener(listener);

        apostar.setVisible(false);
        pasar.setVisible(false);
        igualar.setVisible(false);
        aumentar.setVisible(false);
        retirarse.setVisible(false);
        descartar.setVisible(false);

        botonesPanel.add(opcionesPanel, BorderLayout.LINE_END);

        add(botonesPanel, BorderLayout.PAGE_END);

        this.pack();
    }

    // #---------------------------------------------------------------------------
    // # Métodos desde control a la vista
    // #---------------------------------------------------------------------------

    /**
     * 
     * @param msg
     * @param time
     * @throws InterruptedException
     */
    public void showMessage(String msg, int time) throws InterruptedException {
        textSmall.setText(msg);
        Thread.sleep(time);
    }

    /**
     * 
     * @param msg
     * @throws InterruptedException
     */
    public void showBigMessage(String msg) throws InterruptedException {
        textBig.setText(msg);
    }

    /**
     * 
     * @param player
     */
    public void updateMoney(Jugador player) {
        playersView.get(player).mostrarDinero();
        revalidate();
        repaint();
    }

    /**
     * 
     * @return
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
     * 
     * @param valorParaIgualar
     * @param ultimaRonda
     * @return
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
     * 
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
     * 
     */
    private synchronized void onPasarClick() {
        playersView.get(jugador).getCartasSeleccionadas();
        notifyAll();
    }

    /**
     * 
     */
    private synchronized void onIgualarClick() {
        apuestaDelJugador = valorParaIgualar;
        notifyAll();
    }

    /**
     * 
     */
    private synchronized void onAumentarClick() {
        apuestaDelJugador += valorParaIgualar;
        notifyAll();
    }

    /**
     * 
     * @param ficha
     */
    private void onFichaClick(JButton ficha) {
        apuestaDelJugador += Integer.parseInt(ficha.getText());
        aumentar.setEnabled(true);
        if (valorParaIgualar == 0)
            textSmall.setText("Tu apuesta: " + apuestaDelJugador);
        else
            textSmall.setText("Tu apuesta: " + valorParaIgualar + " + " + apuestaDelJugador);
    }

    /**
     * 
     */
    private synchronized void onRetirarseClick() {
        jugador.retirarse();
        apuestaDelJugador = 0;
        notifyAll();
    }

    private synchronized void onDescartarClick() {
        if (playersView.get(jugador).getCartasSeleccionadasSize() > 0)
            notifyAll();
        else
            textSmall.setText("No has seleccionado ninguna carta");
    }

    // #---------------------------------------------------------------------------
    // # Juego
    // #---------------------------------------------------------------------------

    /**
     * 
     * @throws InterruptedException
     */
    public void iniciarRonda() throws InterruptedException {
        for (int i = 3; i > 0; i--)
            showMessage("La ronda iniciará en " + i, 1000);
    }

    public void rondaDeApuesta() {

    }

    public void rondaDeDescarte() {

    }

    public void descubrirCartas() {

    }

    public void mostrarDinero() {

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
        }
    }
}
