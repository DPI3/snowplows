package tests;

public class test11 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 11: Fej hatástalanná válása készlet hiányában ===");
        
        // Bemenet szimulálása
        System.out.println("load test11_arrange.txt");
        System.out.println("takarit plow_1 lane_ice_1");
        System.out.println("vasarol cleaner_1 salt");
        System.out.println("takarit plow_1 lane_ice_1");
        System.out.println("save test11_out.txt");
        System.out.println("exit");
        System.out.println("stat");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[Console]: Figyelmeztetés: A takarítás sikertelen. A sószóró fej készlete kimerült!");
        System.out.println("[lane_ice_1] [currentState]: IceSheet -> IceSheet");
        System.out.println("[cleaner_1] [money]: 200 -> 150");
        System.out.println("[plow_1] [saltStock]: 0 -> 10");
        System.out.println("[plow_1] [saltStock]: 10 -> 9");
        System.out.println("[lane_ice_1] [currentState]: IceSheet -> Clear");
        System.out.println("[Console]: A sáv felsózva, a jég elolvadt, az út tiszta.");
    }
}