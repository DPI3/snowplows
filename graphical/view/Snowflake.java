package view;

import java.util.Random;

/**
 * Egyetlen hópehely adatait tároló osztály, amely kezeli a pozíciót,
 * méretet, sebességet és az oldalirányú kilengést.
 */
public class Snowflake {
    /** A hópehely vízszintes pozíciója. */
    int x;
    /** A hópehely függőleges pozíciója. */
    int y;
    /** A hópehely mérete pixelben. */
    int size;
    /** A hópehely esési sebessége. */
    int speed;
    /** Az oldalirányú kilengés aktuális eltolása. */
    int swayOffset;
    /** Az oldalirányú kilengés számlálója a szinuszgörbe alapú mozgáshoz. */
    int swayCounter;

    /**
     * Létrehoz egy új hópelyhet a megadott kiindulási pozícióval,
     * véletlenszerű mérettel és sebességgel.
     *
     * @param startX a kezdő vízszintes pozíció
     * @param startY a kezdő függőleges pozíció
     */
    public Snowflake(int startX, int startY) {
        Random rand = new Random();
        this.x = startX;
        this.y = startY;
        this.size = rand.nextInt(3) + 2;
        this.speed = rand.nextInt(3) + 1;
        this.swayCounter = rand.nextInt(100);
    }

    /**
     * Frissíti a hópehely pozícióját: lefelé esik és oldalirányban kileng.
     * Ha a hópehely kiesik a képernyő aljáról, felülről újraindul.
     *
     * @param screenWidth  a képernyő szélessége pixelben
     * @param screenHeight a képernyő magassága pixelben
     */
    public void update(int screenWidth, int screenHeight) {
        y += speed;

        swayCounter++;
        swayOffset = (int) (Math.sin(swayCounter * 0.05) * 2);
        x += swayOffset;

        if (y > screenHeight) {
            y = -size;
            Random rand = new Random();
            x = rand.nextInt(screenWidth);
            speed = rand.nextInt(3) + 1;
        }
    }
}
