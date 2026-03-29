package skeleton.tests;

import skeleton.src.*;

/**
 * A 25. teszteset (Autó célba érése teszt) implementációja.
 * Szigorúan a hivatalos specifikáció és UML alapján!
 */
public class Test25 implements TestCase {

    @Override
    public void run() {
        Workplace workplace = new Workplace();
        
        Car testCar = new Car("c1", null, 0.0, 0.0, null, workplace, null);
        
        testCar.move(); 
    }
}