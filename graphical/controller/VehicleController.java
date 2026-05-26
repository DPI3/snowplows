package controller;

import java.lang.reflect.Method;
import src.Game;

/**
 * Jármű-vezérlő osztály, amely reflexió segítségével irányítja az aktuálisan vezérelt járművet.
 * A játékos szerepétől függően a hókotró vagy a busz vezérlését végzi.
 */
public class VehicleController {
    private final Game game;
    private Object controlledVehicle;
    private String lastDirection;

    /**
     * Létrehozza a jármű-vezérlőt a megadott játékpéldánnyal, és frissíti a vezérelt járművet.
     *
     * @param game a játék példány
     */
    public VehicleController(Game game) {
        this.game = game;
        updateControlledVehicle();
    }

    /**
     * Frissíti a vezérelt járművet a játékos aktuális szerepe alapján.
     * {@code CleanerRole} esetén a hókotrót, {@code BusDriverRole} esetén a buszt állítja be.
     */
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

    /**
     * Felfelé mozgatja a vezérelt járművet.
     */
    public void moveUp() {
        move("UP");
    }

    /**
     * Lefelé mozgatja a vezérelt járművet.
     */
    public void moveDown() {
        move("DOWN");
    }

    /**
     * Balra mozgatja a vezérelt járművet.
     */
    public void moveLeft() {
        move("LEFT");
    }

    /**
     * Jobbra mozgatja a vezérelt járművet.
     */
    public void moveRight() {
        move("RIGHT");
    }

    /**
     * Megállítja a vezérelt járművet. Ha nincs vezérelt jármű, nem csinál semmit.
     */
    public void stopMovement() {
        if (!hasControlledVehicle()) {
            return;
        }

        invokeIfExists(controlledVehicle, "stop");
        invokeIfExists(controlledVehicle, "setMoving", false);
        lastDirection = "STOP";
    }

    /**
     * A megadott irányba mozgatja a vezérelt járművet reflexió segítségével.
     *
     * @param direction a mozgás iránya (pl. "UP", "DOWN", "LEFT", "RIGHT")
     */
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

    /**
     * Megvizsgálja, hogy van-e jelenleg vezérelt jármű.
     *
     * @return {@code true}, ha van vezérelt jármű
     */
    private boolean hasControlledVehicle() {
        return controlledVehicle != null;
    }

    /**
     * Megpróbálja reflexióval meghívni a megadott metódust a célobjektumon.
     *
     * @param target a célobjektum
     * @param methodName a metódus neve
     * @param args a metódus paraméterei
     * @return {@code true}, ha a metódushívás sikeres volt
     */
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

    /**
     * Megkeresi a megadott nevű és paraméterszámú metódust az adott osztályban.
     *
     * @param clazz az osztály, amelyben keresni kell
     * @param methodName a keresett metódus neve
     * @param args a metódus paraméterei (a paraméterszám meghatározásához)
     * @return a megtalált metódus, vagy {@code null} ha nem található
     */
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

    /**
     * Nagybetűssé alakítja a szöveg első karakterét.
     *
     * @param value az átalakítandó szöveg
     * @return a nagybetűs első karakterrel rendelkező szöveg, vagy az eredeti érték ha üres vagy {@code null}
     */
    private String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * Visszaadja az aktuálisan vezérelt járművet.
     *
     * @return a vezérelt jármű objektum, vagy {@code null} ha nincs
     */
    public Object getControlledVehicle() {
        return controlledVehicle;
    }

    /**
     * Visszaadja az utolsó mozgási irányt.
     *
     * @return az utolsó irány szövegesen (pl. "UP", "DOWN", "LEFT", "RIGHT", "STOP")
     */
    public String getLastDirection() {
        return lastDirection;
    }
}
