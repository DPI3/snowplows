import java.util.List;

/**
* A Player egy valódi emberi résztvevőt reprezentál a rendszerben. 
* A játékos a játék emberi irányítója, de önmagában kevés közvetlen műveletet végez,
* a tényleges interakciók a hozzá tartozó Role segítségével valósulnak meg. 
* Egy játékos egyszerre több szerepkört is betölthet, és ezek a szerepkörök határozzák meg,
* hogy milyen járműveket irányíthat, milyen döntéseket hozhat, és hogyan szerez pontokat a játék során.
* A játékos stratégiai döntéseket hoz, amik hatással vannak a Zúzmaraváros állapotára,
* illetve a saját pontszámára.
 */
public class Player {

    /** A játékos egyedi azonosítója. */
    private int id;

    /** A játékos neve. */
    private String name;

    /** A játékoshoz tartozó szerepkörök listája. */
    private List<Role> roles;

    /**
     * Player példány létrehozása.
     *
     * @param id a játékos egyedi azonosítója
     * @param name a játékos neve
     * @param roles a játékos szerepkörei
     */
    public Player(int id, String name, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
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
     * Összegzi a játékos szerepkörei által szerzett pontokat.
     *
     * @return a játékos összesített pontszáma
     */
    public int getSumPoints() {
        return 0;
    }
}
