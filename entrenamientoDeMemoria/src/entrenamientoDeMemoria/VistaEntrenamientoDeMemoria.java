package entrenamientoDeMemoria;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;

/*
TODO comentarios
TODO imagenes
*/

public class VistaEntrenamientoDeMemoria extends JFrame {
    // atributos
    private Escucha listener;
    private ControlEntrenamientoDeMemoria control;
    private JLabel[] cartasLabel;
    private Timer timerOcultarCartas;
    private ArrayList<Integer> cartas;
    private JPanel zonaJuego, zonaIndicaciones;
    private JLabel cartaGanadora;
    private JTextArea indicaciones;
    private JTextArea instrucciones;

    private InputStream loadFont;
    private Font zorqueFont;

    // metodos
    VistaEntrenamientoDeMemoria() {
        initGUI(); // graphic components are created here.

        this.setTitle("Entrenamiento de memoria");
        this.setSize(956, 560);
        // this.pack();
        ; // modify the size of the JFrame.
        this.setLocationRelativeTo(null); // null -> muestra la ventana centrada
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // al cerrar la ventana se termina la ejecución del
                                                             // programa
        this.setVisible(true);
    }

    private void initGUI() {
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        control = new ControlEntrenamientoDeMemoria();
        listener = new Escucha();
        cartasLabel = new JLabel[12];

        // #---------------------------------------------------------------------------

        loadFont = getClass().getResourceAsStream("/fonts/MaldiniNormal-ZVKG3.ttf");
        try {
            zorqueFont = Font.createFont(Font.TRUETYPE_FONT, loadFont);
        } catch (FontFormatException e) {
        } catch (IOException e) {
        }

        // #---------------------------------------------------------------------------

        zonaJuego = new JPanel();
        zonaJuego.setPreferredSize(new Dimension(730, 521));
        zonaJuego.setBackground(new Color(42, 69, 78)); // 0, 233, 211
        zonaJuego.setLayout(new GridBagLayout());
        add(zonaJuego);

        zonaIndicaciones = new JPanel();
        zonaIndicaciones.setPreferredSize(new Dimension(210, 521));
        zonaIndicaciones.setBackground(new Color(245, 245, 245));
        add(zonaIndicaciones);

        // #---------------------------------------------------------------------------

        cartaGanadora = new JLabel();
        cartaGanadora.setIcon(getImage("ojo", 150));
        cartaGanadora.setBorder(new EmptyBorder(25, 0, 0, 0));
        zonaIndicaciones.add(cartaGanadora);

        indicaciones = new JTextArea("Encuentra esta carta");
        indicaciones.setFont(zorqueFont.deriveFont(20f));
        indicaciones.setBackground(new Color(245, 245, 245));
        indicaciones.setEditable(false);
        zonaIndicaciones.add(indicaciones);

        instrucciones = new JTextArea("Memoriza las \ncartas antes de que" + "\n termine el tiempo."
                + "\n Selecciona la carta\n correcta para \navanzar a la\n siguiente ronda."
                + "\nUna vez completada\n la ronda 12\n ganas el juego.");
        instrucciones.setFont(zorqueFont.deriveFont(14f));
        instrucciones.setBackground(new Color(245, 245, 245));
        instrucciones.setBorder(new EmptyBorder(25, 0, 0, 0));
        instrucciones.setEditable(false);
        zonaIndicaciones.add(instrucciones);

        // #---------------------------------------------------------------------------

        timerOcultarCartas = new Timer(control.getTiempoDeEspera(), listener);
        mostrarCartas();
    }

    private void mostrarAbstractCartas(int numeroDeCeldas, int columnas, List<Integer> restricciones) {
        GridBagConstraints constraints = new GridBagConstraints();
        int imageSize = (cartas.size() == 4) ? 225 : 150;

        for (int i = 0; i < numeroDeCeldas; i++) {
            if (restricciones.indexOf(i) != -1)
                continue;

            constraints.gridx = i % columnas;
            constraints.gridy = (int) Math.floor(i / columnas);
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            cartasLabel[i] = new JLabel();
            cartasLabel[i].removeMouseListener(listener);
            cartasLabel[i].setBorder(new EmptyBorder(10, 10, 10, 10));
            cartasLabel[i].setIcon(getImage(cartas.get(getCartaIndex(numeroDeCeldas, i)).toString(), imageSize));

            zonaJuego.add(cartasLabel[i], constraints);
        }
    }

    private void mostrarCartas() {
        cartas = control.revolverCartas();

        zonaJuego.removeAll();
        zonaJuego.revalidate();
        zonaJuego.repaint();

        if (cartas.size() == 4)
            mostrarAbstractCartas(4, 2, java.util.Collections.emptyList());
        else if (cartas.size() == 6)
            mostrarAbstractCartas(6, 3, java.util.Collections.emptyList());
        else if (cartas.size() == 8)
            mostrarAbstractCartas(9, 3, java.util.Arrays.asList(new Integer[] { 4 }));
        else if (cartas.size() == 10)
            mostrarAbstractCartas(10, 4, java.util.Arrays.asList(new Integer[] { 5, 6 }));
        else if (cartas.size() == 12)
            mostrarAbstractCartas(12, 4, java.util.Collections.emptyList());

        timerOcultarCartas.setDelay(control.getTiempoDeEspera());
        timerOcultarCartas.start();
    }

    private void ocultarCartas() {
        int imageSize = (cartas.size() == 4) ? 225 : 150;

        for (int i = 0; i < cartas.size(); i++) {
            cartasLabel[i].setIcon(getImage("1", imageSize));
            cartasLabel[i].addMouseListener(listener);
            cartasLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        cartaGanadora.setIcon(getImage(Integer.toString(control.getCartaGanadora()), 150));
        timerOcultarCartas.stop();
        zonaIndicaciones.add(indicaciones);
    }

    private void determinarJuego(int carta) {
        if (control.determinarRonda(carta)) {
            mostrarCartas();
        } else {
            mostrarCartas();
        }
    }

    // #---------------------------------------------------------------------------
    // # FUNCIONES AUXILIARES
    // #---------------------------------------------------------------------------

    private int getCartaIndex(int numeroDeCeldas, int i) {
        if (numeroDeCeldas == 9)
            if (i > 4)
                return i - 1;
        if (numeroDeCeldas == 10)
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
            if (event.getSource() == timerOcultarCartas)
                ocultarCartas();

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

}
