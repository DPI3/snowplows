package skeleton.src;
/**
 * A Tunnel osztaly egy alagut tipusu utszakaszt reprezental.
 *
 * Az alagutak idojarasi hatasai elterhetnek a nyilt utszakaszoktol,
 * ezert kulon kezelesuk indokolt az allapotvaltozasok soran.
 */
public class Tunnel extends Road{

    /**
     * Alkalmazza az idojaras hatasat az alagut utallapotara.
     *
     * @param weather az aktualis idojarasi allapot
     */
    @Override
    public void applyWeatherEffect(Weather weather){
        
    }
}