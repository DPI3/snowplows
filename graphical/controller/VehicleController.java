package controller;

import java.lang.reflect.Method;
import src.Game;


public class VehicleController {
    private final Game game;
    private Object controlledVehicle;
    private String lastDirection;

    public VehicleController(Game game) {
        this.game = game;
        updateControlledVehicle();
    }

    public void updateControlledVehicle() {
        try {
            Object player = game.getPlayer();
            Object role = player.getClass().getMethod("getCurrentRole").invoke(player);
            String roleName = role.getClass().getSimpleName();

            if (roleName.equalsIgnoreCase("CleanerRole")) {
                controlledVehicle = game.getSnowplow();
            } else if (roleName.equalsIgnoreCase("BusDriverRole")) {
                controlledVehicle = game.getBus();
            }
        } catch (Exception e) {
            controlledVehicle = null;
        }
    }

    public void moveUp() {
        move("UP");
    }

    public void moveDown() {
        move("DOWN");
    }

    public void moveLeft() {
        move("LEFT");
    }

    public void moveRight() {
        move("RIGHT");
    }

    public void stopMovement() {
        if (!hasControlledVehicle()) {
            return;
        }

        invokeIfExists(controlledVehicle, "stop");
        invokeIfExists(controlledVehicle, "setMoving", false);
        lastDirection = "STOP";
    }

    private void move(String direction) {
        updateControlledVehicle();

        if (!hasControlledVehicle()) {
            return;
        }

        lastDirection = direction;

        if (invokeIfExists(controlledVehicle, "move" + capitalize(direction.toLowerCase()))) {
            return;
        }

        if (invokeIfExists(controlledVehicle, "setDirection", direction)) {
            return;
        }

        invokeIfExists(controlledVehicle, "move", direction);
    }

    private boolean hasControlledVehicle() {
        return controlledVehicle != null;
    }

    private boolean invokeIfExists(Object target, String methodName, Object... args) {
        try {
            Method method = findMethod(target.getClass(), methodName, args);
            if (method == null) {
                return false;
            }

            method.setAccessible(true);
            method.invoke(target, args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Method findMethod(Class<?> clazz, String methodName, Object... args) {
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            if (method.getParameterCount() == args.length) {
                return method;
            }
        }

        return null;
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public Object getControlledVehicle() {
        return controlledVehicle;
    }

    public String getLastDirection() {
        return lastDirection;
    }
}