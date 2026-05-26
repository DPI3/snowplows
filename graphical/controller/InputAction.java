package controller;

/**
 * A játékos által végrehajtható bemeneti műveletek felsorolása.
 * Az egyes értékek a billentyűzetről vagy más beviteli eszközről érkező parancsokat reprezentálják.
 */
public enum InputAction {
    /** Mozgás felfelé. */
    MOVE_UP,
    /** Mozgás lefelé. */
    MOVE_DOWN,
    /** Mozgás balra. */
    MOVE_LEFT,
    /** Mozgás jobbra. */
    MOVE_RIGHT,
    /** Megállás. */
    STOP,
    /** Bolt megnyitása. */
    OPEN_STORE,
    /** Beállítások megnyitása. */
    OPEN_SETTINGS,
    /** Menü megnyitása. */
    OPEN_MENU,
    /** Szüneteltetés. */
    PAUSE,
    /** Megerősítés. */
    CONFIRM,
    /** Visszavonás. */
    CANCEL,
    /** Fej cseréje. */
    CHANGE_HEAD,
    /** Újraindítás. */
    RESTART
}
