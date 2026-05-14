package src;

import java.util.List;

/**
 * A Player egy valódi emberi résztvevőt reprezentál a rendszerben.
 * A játékoshoz egy aktuális Role és egy külön kezelt pontszám tartozik.
 */
public class Player {

    /** A játékos egyedi azonosítója. */
    @SuppressWarnings("unused")
    private int id;

    /** A játékos neve. */
    private String name;

    /** A játékos aktuális szerepköre. */
    private Role currentRole;

    /** A játékos pontszáma. */
    private int score;

    /**
     * Player példány létrehozása.
     *
     * @param id a játékos egyedi azonosítója
     * @param name a játékos neve
     * @param currentRole a játékos aktuális szerepköre
     */
    public Player(int id, String name, Role currentRole) {
        this.id = id;
        this.name = name;
        this.currentRole = currentRole;
    }

    /**
     * Kompatibilitási konstruktor a korábbi több-szerepkörös hívásokhoz.
     * Az első szerepkört állítja be aktuális szerepkörnek.
     *
     * @param id a játékos egyedi azonosítója
     * @param name a játékos neve
     * @param roles a játékos szerepkörei
     */
    public Player(int id, String name, List<Role> roles) {
        this(id, name, roles == null || roles.isEmpty() ? null : roles.get(0));
    }


    /**
     * Visszaadja a játékos nevét.
     *
     * @return a játékos neve
     */
    public String getName() {
        return name;
    }

    /**
     * Visszaadja a játékos összesített pontszámát.
     *
     * @return a játékos összesített pontszáma
     */
    public int getSumPoints() {
        return score;
    }

    /**
     * Visszaadja a játékos aktuális szerepkörét.
     *
     * @return a játékos aktuális szerepköre
     */
    public Role getCurrentRole() {
        return currentRole;
    }

    /**
     * Beállítja a játékos aktuális szerepkörét.
     *
     * @param role az új aktuális szerepkör
     */
    public void setCurrentRole(Role role) {
        this.currentRole = role;
    }

    /**
     * Visszaadja a játékos pontszámát.
     *
     * @return a játékos pontszáma
     */
    public int getScore() {
        return score;
    }

    /**
     * Növeli a játékos pontszámát.
     *
     * @param points a hozzáadandó pontok száma
     */
    public void addScore(int points) {
        this.score += points;
    }
}
