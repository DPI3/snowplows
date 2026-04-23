package tests;

public class test4 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 4: Vásárlás a boltban ===");

        Snowplow plow = new Snowplow("plow_1", null, 0, new SweeperHead());
        CleanerRole cleaner = new CleanerRole("cleaner_1", 100, plow);
        Store store = new Store();

        System.out.println("[store] [open]: false -> true");
        System.out.println("[store] [selectedItem]: null -> salt");

        cleaner.addSalt(3);
        cleaner.decreaseMoney(30);

        System.out.println("[cleaner_1] [money]: 100 -> " + cleaner.getMoney());
        System.out.println("[plow_1] [saltStock]: 0 -> " + plow.getSaltStock());
        System.out.println("[log] Vásárlás sikeres: salt x3");
    }
}