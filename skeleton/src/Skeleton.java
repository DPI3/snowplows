package skeleton.src;

import java.util.Scanner;

/**
 * Egy központi segédosztály, amely a szkeleton program futási folyamatának 
 * formázásáért és naplózásáért felelős. 
 * <p>
 * Segít a szekvenciadiagramok vizualizálásában azáltal, hogy az aktuális hívási 
 * mélység alapján automatikusan behúzza a metódushívásokat és a visszatéréseket. 
 * Emellett központosítja a felhasználói bemenetek kezelését a tesztek során hozott döntésekhez.
 */
public class Skeleton {

    /**
     * Nyomon követi a metódushívások aktuális mélységét a megfelelő behúzás biztosítása érdekében.
     */
    private static int depth = 0;

    /**
     * Naplózza egy metódushívás belépési pontját.
     * Automatikusan növeli a behúzás mélységét a későbbi hívásokhoz.
     * Kimeneti formátum: {@code >>> [className].methodName}
     *
     * @param className  a hívást kezdeményező osztály neve (pl. "Car")
     * @param methodName a meghívott metódus neve, a paraméterekkel együtt (pl. "move()")
     */
    public static void printCall(String className, String methodName) {
        printIndent();
        System.out.println(">>> [" + className + "]." + methodName);
        depth++;
    }

    /**
     * Naplózza a visszatérést egy metódushívásból.
     * Automatikusan csökkenti a behúzás mélységét.
     * Kimeneti formátum: {@code <<< return returnValue}
     *
     * @param returnValue a visszatérési érték szöveges reprezentációja. 
     * Ha a metódus visszatérési típusa void, egy üres stringet ("") kell átadni.
     */
    public static void printReturn(String returnValue) {
        depth--;
        printIndent();
        if (returnValue == null || returnValue.isEmpty()) {
            System.out.println("<<< return");
        } else {
            System.out.println("<<< return " + returnValue);
        }
    }

    /**
     * Naplózza egy objektum belső állapotának megváltozását.
     * Kimeneti formátum: {@code [STATE] leírás}
     *
     * @param stateDescription az állapotváltozás rövid leírása (pl. "Sebesség beállítva 0-ra")
     */
    public static void printState(String stateDescription) {
        printIndent();
        System.out.println("[STATE] " + stateDescription);
    }

    /**
     * Kiír egy szabványosított kérdést a konzolra, és beolvas egy egész szám (integer) 
     * választ a felhasználótól. Hasznos az interaktív teszteléshez.
     *
     * @param scanner a felhasználói bemenet olvasásához használt Scanner objektum
     * @param question a felhasználónak megjelenítendő kérdés
     * @return a felhasználó által megadott egész szám
     */
    public static int requestInput(Scanner scanner, String question) {
        printIndent();
        System.out.println("Decision: " + question);
        printIndent();
        System.out.print(">> ");
        return scanner.nextInt();
    }

    /**
     * Segédmetódus, amely az aktuális hívási mélység alapján megfelelő számú 
     * szóközt ír ki. Mélységi szintenként két szóközt nyomtat.
     */
    private static void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  "); 
        }
    }
}