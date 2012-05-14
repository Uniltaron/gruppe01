import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Integer;
import java.io.*;

/**
 * File:         BomberMain.java
 */

/**
 * Startpunkt des Spiels
 */
public class BomberMain extends JFrame {
    /** Entsprechender path f�r die Dateien */
    public static String RP = "./";
    /** Men� */
    private BomberMenu menu = null;
    /** Spiel */
    private BomberGame game = null;

    /** Spielersounds */
    public static BomberSndEffect sndEffectPlayer = null;
    /** Berechnung der Dimension des Spiels */
    public static final int shiftCount = 4;
    /**Gr��e pro Feld im Spiel */
    public static final int size = 1 << shiftCount;

    static {
        sndEffectPlayer = new BomberSndEffect();
    }

    /**
     * Konstruiert das Hauptger�st
     */
    public BomberMain() {
        /** Hinzuf�gen von Fenster-Ereignissteuerung */
        addWindowListener(new WindowAdapter() {
            /**
             * 
             * @param evt window event
             */
            public void windowClosing(WindowEvent evt) {
                /** Programmterminierung*/
                System.exit(0);
            }
        });

        /** Hinzuf�gen von Keys-Ereignisteuerung */
        addKeyListener(new KeyAdapter() {
            /**
             * Steuert Ereignisse bei Keys.
             * @param evt keyboard event
             */
            public void keyPressed(KeyEvent evt) {
                if (menu != null) menu.keyPressed(evt);
                if (game != null) game.keyPressed(evt);
            }

            /**
             * Steuerung ausgel�ster Key Ereignisse.
             * @param evt keyboard event
             */
            public void keyReleased(KeyEvent evt) {
                if (game != null) game.keyReleased(evt);
            }
        });

        /** Fenstertitel */
        setTitle("Bomberman Gruppe1");

        /** Fenstersymbol */
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(
                new File(RP + "Images/Bomberman.gif").getCanonicalPath()));
        }
        catch (Exception e) { new ErrorDialog(e); }

        /** Konstruieren und hinzuf�gen von Men� zum Ger�st */
        getContentPane().add(menu = new BomberMenu(this));

        /** Feste Fenstergr��e um �nderung durch User zu verhindern */
        setResizable(false);
        /** Fensterminimierung */
        pack();

        /** Bildfl�che erhalten */
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;

        /** Fenster auf Bildfl�che zentrieren */
        setLocation(x, y);
        /** Ger�st zeigen */
        show();
        /** Fenster zum top level hervorheben */
        toFront();
    }

    /**
     * Neues Spiel erstellen.
     * @param players totale Anzahl Spieler
     */
    public void newGame(int players)
    {
        JDialog dialog = new JDialog(this, "Lade Spiel...", false);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setSize(new Dimension(200, 0));
        dialog.setResizable(false);
        int x = getLocation().x + (getSize().width - 200) / 2;
        int y = getLocation().y + getSize().height / 2;
        dialog.setLocation(x, y);
        /** Dialog zeigen */
        dialog.show();

        /** Contentpane entfernen */
        getContentPane().removeAll();
        getLayeredPane().removeAll();
        /** Men� beseitigen */
        menu = null;
        /** Map erstellen*/
        BomberMap map = new BomberMap(this);

        /** Spiel erstellen */
        game = new BomberGame(this, map, players);

        /** Ladedialog beseitigen */
        dialog.dispose();
        /** Ger�st zeigen */
        show();
        /** if Java 2 verf�gbar */
        if (Main.J2) {
           BomberBGM.unmute();
           /** Spielermusik */
           BomberBGM.change("Battle");
        }
    }

    /**
     * Startpunkt des Spiels
     * @param args Argumente
     */
    public static void main(String[] args) {
        BomberMain bomberMain1 = new BomberMain();
    }
}
