package skeleton.tests;

import java.util.Scanner;


/**
     * A tesztszám alapján visszaadja a megfelelő tesztobjektumot.
     *
     * A kiválasztás switch-case szerkezet segítségével történik.
     *
     * @param testNumber a teszt sorszáma
     * @return a megfelelő TestCase példány, vagy null ha nincs implementálva
     */
public interface TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     *
     * A metódus a konzolra írja ki a végrehajtás lépéseit.
     * Szükség esetén felhasználói döntést is kérhet.
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    void run(Scanner scanner);
}