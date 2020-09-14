package entrenamientoDeMemoria;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class VistaEntrenamientoDeMemoria extends JFrame {
    // atributos
    private Escucha listener;
    private ControlEntrenamientoDeMemoria control;
    private JLabel[] cartasLabel;
    private Timer timer;
    private ArrayList<Integer> cartas;
    private JPanel zonaJuego, zonaIndicaciones;
    private JLabel cartaGanadora, ganastePerdiste, contador, siguienteRonda;
    private JTextArea indicaciones;
    private JTextPane instrucciones;
    private Map<Integer, PanelProperties> panelPropertiesByCartasSize;
    private boolean finDeRonda;

    private InputStream loadFont;
    private Font niagaraphobia;
    private final int TIEMPO_DE_ESPERA = 2;

    // metodos
    VistaEntrenamientoDeMemoria() {
        panelPropertiesByCartasSize = new HashMap<>();
        panelPropertiesByCartasSize.put(4, new PanelProperties(4, 2, null, 225));
        panelPropertiesByCartasSize.put(6, new PanelProperties(6, 3, null, 200));
        panelPropertiesByCartasSize.put(8, new PanelProperties(9, 3, Arrays.asList(new Integer[] { 4 }), 150));
        panelPropertiesByCartasSize.put(10, new PanelProperties(12, 4, Arrays.asList(new Integer[] { 5, 6 }), 150));
        panelPropertiesByCartasSize.put(12, new PanelProperties(12, 4, null, 150));

        initGUI();

        this.setTitle("Entrenamiento de memoria");
        this.setSize(956, 560);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initGUI() {
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        control = new ControlEntrenamientoDeMemoria();
        listener = new Escucha();
        cartasLabel = new JLabel[12];
        ganastePerdiste = new JLabel();
        contador = new JLabel();

        for (int i = 0; i < 12; i++)
            cartasLabel[i] = new JLabel();

        // #---------------------------------------------------------------------------

        loadFont = getClass().getResourceAsStream("/fonts/Niagaraphobia-Bro3.ttf");
        try {
            niagaraphobia = Font.createFont(Font.TRUETYPE_FONT, loadFont);
        } catch (FontFormatException e) {
        } catch (IOException e) {
        }

        // #---------------------------------------------------------------------------

        zonaJuego = new JPanel();
        zonaJuego.setPreferredSize(new Dimension(730, 521));
        add(zonaJuego);

        zonaIndicaciones = new JPanel();
        zonaIndicaciones.setPreferredSize(new Dimension(210, 521));
        zonaIndicaciones.setBackground(new Color(255, 255, 255));
        add(zonaIndicaciones);

        // #---------------------------------------------------------------------------

        cartaGanadora = new JLabel();
        cartaGanadora.setBorder(new EmptyBorder(25, 0, 0, 0));
        zonaIndicaciones.add(cartaGanadora);

        indicaciones = new JTextArea("¡Mira estas delicias!");
        indicaciones.setFont(niagaraphobia.deriveFont(24f));
        indicaciones.setBackground(new Color(255, 255, 255));
        indicaciones.setEditable(false);
        zonaIndicaciones.add(indicaciones);

        JLabel separador = new JLabel("       _____________________________");
        separador.setFont(new Font("Arial", Font.BOLD, 35));
        separador.setForeground(new Color(255, 87, 51));
        zonaIndicaciones.add(separador);

        instrucciones = new JTextPane();
        instrucciones.setText(
                "Para ganar este juego\nmemorizar muy bien debes\nantes de que termine\nel tiempo\n\n¡Apresurate!\nTic Toc... Tic Toc...");
        instrucciones.setFont(niagaraphobia.deriveFont(19f));

        // centrar el texto de las instrucciones
        StyledDocument doc = instrucciones.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        instrucciones.setBorder(new EmptyBorder(20, 0, 0, 0));
        instrucciones.setEditable(false);
        instrucciones.setForeground(new Color(100, 100, 100));
        instrucciones.setHighlighter(null);
        zonaIndicaciones.add(instrucciones);

        ganastePerdiste.setFont(niagaraphobia.deriveFont(100f));
        ganastePerdiste.setBounds(0, 130, 730, 100);
        ganastePerdiste.setHorizontalAlignment(SwingConstants.CENTER);

        siguienteRonda = new JLabel("La siguiente ronda comenzará en...");
        siguienteRonda.setFont(niagaraphobia.deriveFont(20f));
        siguienteRonda.setBounds(0, 210, 730, 100);
        siguienteRonda.setHorizontalAlignment(SwingConstants.CENTER);

        // #---------------------------------------------------------------------------

        timer = new Timer(1000, listener);
        mostrarCartas();
    }

    private void mostrarCartas() {
        cartas = control.revolverCartas();
        cartaGanadora.setIcon(getImage("ojo", 150));
        finDeRonda = false;

        indicaciones.setText("¡Mira estas delicias!");
        indicaciones.setFont(niagaraphobia.deriveFont(24f));

        zonaJuego.setLayout(new GridBagLayout());
        zonaJuego.setBackground(new Color(42, 69, 78)); // 0, 233, 211
        zonaJuego.removeAll();
        zonaJuego.revalidate();
        zonaJuego.repaint();

        mostrarPanelDeCartas(panelPropertiesByCartasSize.get(cartas.size()));

        contador.setText(String.valueOf(control.getTiempoDeEspera()));
        contador.setFont(niagaraphobia.deriveFont(30f));
        contador.setBorder(new EmptyBorder(20, 0, 0, 0));
        contador.setMinimumSize(new Dimension(210, 37));
        contador.setPreferredSize(new Dimension(210, 37));
        contador.setHorizontalAlignment(SwingConstants.CENTER);
        zonaIndicaciones.add(contador);

        timer.start();
    }

    private void mostrarPanelDeCartas(PanelProperties props) {
        GridBagConstraints constraints = new GridBagConstraints();

        for (int i = 0; i < props.numeroDeCeldas; i++) {
            if (props.restricciones.indexOf(i) != -1)
                continue;

            constraints.gridx = i % props.columnas;
            constraints.gridy = (int) Math.floor(i / props.columnas);
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            cartasLabel[i].removeMouseListener(listener);
            cartasLabel[i]
                    .setBorder(cartas.size() == 8 ? new EmptyBorder(10, 25, 10, 25) : new EmptyBorder(10, 10, 10, 10));
            cartasLabel[i].setIcon(getImage("C" + props.size + "." + cartas.get(getCartaIndex(i)), props.size));

            zonaJuego.add(cartasLabel[i], constraints);
        }
    }

    private void ocultarCartas() {
        PanelProperties props = panelPropertiesByCartasSize.get(cartas.size());

        for (int i = 0; i < props.numeroDeCeldas; i++) {
            if (props.restricciones.indexOf(i) != -1)
                continue;

            cartasLabel[i].setIcon(getImage("H" + props.size + "." + getCartaIndex(i + 1), props.size));
            cartasLabel[i].addMouseListener(listener);
            cartasLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        cartaGanadora.setIcon(getImage("C150." + control.getCartaGanadora(), 150));
        indicaciones.setText("¿En dónde estaba... ?");
        indicaciones.setFont(niagaraphobia.deriveFont(24f));
    }

    private void determinarJuego(int carta) {
        if (control.determinarRonda(carta)) {
            ganastePerdiste.setText("¡Ganaste!");
            ganastePerdiste.setForeground(new Color(180, 230, 47));
        } else {
            ganastePerdiste.setText("Perdiste... :(");
            ganastePerdiste.setForeground(new Color(215, 14, 14));
        }

        contador.setText(String.valueOf(TIEMPO_DE_ESPERA));
        contador.setFont(niagaraphobia.deriveFont(30f));
        contador.setBounds(0, 250, 730, 100);
        contador.setHorizontalAlignment(SwingConstants.CENTER);

        zonaJuego.setBackground(Color.WHITE);
        zonaJuego.setLayout(null);
        zonaJuego.removeAll();
        zonaJuego.revalidate();
        zonaJuego.repaint();
        zonaJuego.add(ganastePerdiste);
        zonaJuego.add(siguienteRonda);
        zonaJuego.add(contador);

        finDeRonda = true;
        timer.start();
    }

    // #---------------------------------------------------------------------------
    // # FUNCIONES AUXILIARES
    // #---------------------------------------------------------------------------

    private int getCartaIndex(int i) {
        if (cartas.size() == 8)
            if (i > 4)
                return i - 1;
        if (cartas.size() == 10)
            if (i > 6)
                return i - 2;
        return i;
    }

    private ImageIcon getImage(String name, int size) {
        return new ImageIcon(new ImageIcon(this.getClass().getResource("/images/" + name + ".png")).getImage()
                .getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }

    // #---------------------------------------------------------------------------
    // # LISTENER
    // #---------------------------------------------------------------------------

    private class Escucha implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == timer)
                if (Integer.parseInt(contador.getText()) == 0) {
                    timer.stop();
                    if (finDeRonda)
                        mostrarCartas();
                    else
                        ocultarCartas();
                } else
                    contador.setText(String.valueOf(Integer.parseInt(contador.getText()) - 1));

        }

        @Override
        public void mouseClicked(MouseEvent event) {
            for (int i = 0; i < cartas.size(); i++)
                if (event.getSource() == cartasLabel[i]) {
                    determinarJuego(i);
                    break;
                }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    private class PanelProperties {
        public int numeroDeCeldas;
        public int columnas;
        public List<Integer> restricciones;
        public int size;

        PanelProperties(int numeroDeCeldas, int columnas, List<Integer> restricciones, int size) {
            this.numeroDeCeldas = numeroDeCeldas;
            this.columnas = columnas;
            this.restricciones = restricciones != null ? restricciones : Collections.emptyList();
            this.size = size;
        }
    }
}