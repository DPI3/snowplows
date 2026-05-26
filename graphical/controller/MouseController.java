package controller;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Egérvezérlő osztály, amely a {@link MouseListener} és {@link MouseMotionListener} interfészeket implementálja.
 * Az egéresemények alapján a megfelelő játékvezérlő műveleteket hívja meg.
 */
public class MouseController implements MouseListener, MouseMotionListener {
    private final GameController controller;
    private Component hoveredComponent;

    /**
     * Létrehozza az egérvezérlőt a megadott játékvezérlővel.
     *
     * @param controller a játékvezérlő, amelynek az egéreseményeket továbbítja
     */
    public MouseController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Egérkattintás kezelése. A kattintott komponens neve alapján hívja meg
     * a megfelelő játékvezérlő műveletet.
     *
     * @param e az egéresemény
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Component source = e.getComponent();

        if (source == null) {
            return;
        }

        String name = source.getName();

        if (name == null) {
            return;
        }

        switch (name.toUpperCase()) {
            case "STORE": controller.openStore(); break;
            case "SETTINGS": controller.openSettings(); break;
            case "MENU": controller.openMenu(); break;
            case "CHANGE": controller.changeHead(); break;
            case "PAUSE": controller.togglePause(); break;
            default: controller.confirm(); break;
        }
    }

    /**
     * Egérgomb lenyomásának kezelése. Beállítja az aktuális komponenst és újrarajzolja.
     *
     * @param e az egéresemény
     */
    @Override
    public void mousePressed(MouseEvent e) {
        hoveredComponent = e.getComponent();
        if (hoveredComponent != null) {
            hoveredComponent.repaint();
        }
    }

    /**
     * Egérgomb felengedésének kezelése. Újrarajzolja az aktuális komponenst.
     *
     * @param e az egéresemény
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (hoveredComponent != null) {
            hoveredComponent.repaint();
        }
    }

    /**
     * Egér belépésének kezelése egy komponens területére.
     *
     * @param e az egéresemény
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        hoveredComponent = e.getComponent();
    }

    /**
     * Egér kilépésének kezelése egy komponens területéről.
     *
     * @param e az egéresemény
     */
    @Override
    public void mouseExited(MouseEvent e) {
        hoveredComponent = null;
    }

    /**
     * Egérmozgás kezelése (gomb lenyomása nélkül).
     *
     * @param e az egéresemény
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        hoveredComponent = e.getComponent();
    }

    /**
     * Egérhúzás kezelése (gomb lenyomásával).
     *
     * @param e az egéresemény
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        hoveredComponent = e.getComponent();
    }

    /**
     * Visszaadja az aktuálisan kijelölt (fölötte lévő egérrel rendelkező) komponenst.
     *
     * @return az aktuális komponens, vagy {@code null} ha nincs
     */
    public Component getHoveredComponent() {
        return hoveredComponent;
    }
}
