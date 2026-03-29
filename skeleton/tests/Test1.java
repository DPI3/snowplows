package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 1. teszteset : Játék indítása, inicializáció teszt.
 * A teszteset ellenőrzi, hogy a játék indulásakor minden szükséges objektum helyesen jön létre:
 * buszok, buszvezetők, autók, hókotrók és takarítók.
 */
public class Test1 implements TestCase{

    /**
     * A tesztszekvencia futtatása.
     * Elindítja az objektumorientált világ felépítését. A folyamat során sorban létrejönnek a szerepkörök 
     * és a járművek a szekvenciadiagramon látható sorrendben.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához (nem használt)
     */
    @Override
    public void run(Scanner scanner) {
        
        // A diagram alapján a Skeleton a Game objektum start() metódusát hívja
        Game game = new Game();
        
        // Meghívjuk a start metódust, ami elindítja a láncreakciót
        game.start();
    }
}
