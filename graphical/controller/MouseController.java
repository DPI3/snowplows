package controller;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {
    private final GameController controller;
    private Component hoveredComponent;

    public MouseController(GameController controller) {
        this.controller = controller;
    }

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

    @Override
    public void mousePressed(MouseEvent e) {
        hoveredComponent = e.getComponent();
        if (hoveredComponent != null) {
            hoveredComponent.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (hoveredComponent != null) {
            hoveredComponent.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hoveredComponent = e.getComponent();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoveredComponent = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hoveredComponent = e.getComponent();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        hoveredComponent = e.getComponent();
    }

    public Component getHoveredComponent() {
        return hoveredComponent;
    }
}