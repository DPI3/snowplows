package skeleton.src;

public class Skeleton {
    private static int depth = 0; // Ez követi, hogy milyen mélyen vagyunk a hívásokban

    // Metódus belépés naplózása
    public static void printCall(String caller, String method) {
        printIndent();
        System.out.println(">>> [" + caller + "]." + method);
        depth++;
    }

    // Visszatérés naplózása
    public static void printReturn(String returnVal) {
        depth--;
        printIndent();
        System.out.println("<<< return " + returnVal);
    }

    // Állapotváltozás naplózása
    public static void printState(String stateInfo) {
        printIndent();
        System.out.println("[STATE] " + stateInfo);
    }

    // Segédmetódus a szép behúzásokhoz (pl. tabulátorok)
    private static void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  "); // Két szóköz minden mélységnél
        }
    }
}