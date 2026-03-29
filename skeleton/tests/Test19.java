package skeleton.tests;

import skeleton.src.*;

/**
 * A 19. teszteset (Pontszerzés takarítással teszt) implementációja.
 * Ez a teszt azt a folyamatot ellenőrzi, amely során a takarító szerepkör 
 * egy hókotró és a rászerelt sárkányfej segítségével megtisztít egy sávot, 
 * majd a sikeres takarításért cserébe pontot szerez.
 */
public class Test19 implements TestCase {

    /**
     * Futtatja a tesztesetet.
     * Létrehozza a szükséges szereplőket, felszereli a sárkányfejet a hókotróra,
     * majd elindítja a takarítási parancsot és lekérdezi a megszerzett pontszámot.
     */
    @Override
    public void run() {
        CleanerRole cleanerRole = new CleanerRole();
        Snowplow sp = new Snowplow();
        DragonHead dh = new DragonHead();

        sp.changeHead(dh);

        cleanerRole.controlSnowplow(sp);
        cleanerRole.getScore();
    }
}