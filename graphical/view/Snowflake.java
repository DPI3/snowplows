package view;

import java.util.Random;

/**
* Belső osztály egyetlen hópehely adatainak tárolására.
*/
public class Snowflake {
    int x, y;
    int size;
    int speed;
    int swayOffset; // Oldalirányú kilengés
    int swayCounter;

    public Snowflake(int startX, int startY) {
        Random rand = new Random();
        this.x = startX;
        this.y = startY;
        this.size = rand.nextInt(3) + 2; // 2 és 4 pixel közötti méret
        this.speed = rand.nextInt(3) + 1; // Eltérő esési sebességek
        this.swayCounter = rand.nextInt(100);
    }

    public void update(int screenWidth, int screenHeight) {
        y += speed; // Lefelé esik
                
        // Finom oldalirányú mozgás szinuszgörbe alapján
        swayCounter++;
        swayOffset = (int) (Math.sin(swayCounter * 0.05) * 2);
        x += swayOffset;

        // Ha kiesik alul, újraindul felülről, véletlenszerű X koordinátával
        if (y > screenHeight) {
            y = -size;
            Random rand = new Random();
            x = rand.nextInt(screenWidth);
            speed = rand.nextInt(3) + 1; // Új sebesség, hogy változatos maradjon
        }
    }
}
