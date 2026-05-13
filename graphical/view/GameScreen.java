package view;

import javax.swing.*;

import src.CleanerRole;
import src.GravelSpreaderHead;
import src.IcebreakerHead;
import src.SaltSpreaderHead;
import src.ThrowerHead;
import src.SweeperHead;
import src.DragonHead;
import src.Snowplow;
import src.Lane;
import src.Role;
import src.BusdriverRole;
import src.Store;
import src.Buyable;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JFrame{

    private static Font silkscreenTitle;
    private static Font silkscreenNormal;
    private static Font silkscreenSmall;

    // Közös színek a dizájnhoz
    private final Color TEXT_COLOR = Color.decode("#E2E874"); // Sárgás-zöldes pixel szöveg
    private final Color PINK_COLOR = Color.decode("#EE8695");
    private final Color DARK_SHADOW = new Color(25, 25, 30);

    
    private TopPill roundTopPill;
    private TopPill modeTopPill;
    private TopPill moneyTopPill;
    private GrayInfoBox infoBox;
    private Role role;
    private StorePanel storePanel;

    public void roundChanged(int round){
        roundTopPill.setText("Kör: "+round);
    }
    
    public void moneyChanged(){
        if(role instanceof CleanerRole){
            CleanerRole c=(CleanerRole)role;
            moneyTopPill.setText(Integer.toString(c.getMoney()));
        }
        if(role instanceof BusdriverRole){
            BusdriverRole c=(BusdriverRole)role;
            moneyTopPill.setText(Integer.toString(c.getMoney()));
        }       
    }

    public void headChanged(){
        if(role instanceof CleanerRole){
            CleanerRole c=(CleanerRole) role;
            
        }
            
    }

    public void roleChanged(Role role){
        this.role=role;
        if (role instanceof CleanerRole) {
               modeTopPill.setText("SNOWPOW MODE");
        }
        if(role instanceof BusdriverRole){
            modeTopPill.setText("BUS MODE");
        }
    }

    public Role getRole(){return role;}

    public GameScreen(Role role, Store store) {
        this.role=role;
        setTitle("Snowplow - Game Screen");
        setSize(1000, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadCustomFont();

        // Fő háttér panel, ami megrajzolja a sötét bal oldalt és a menta zöld jobb oldalt
        MainBgPanel mainBg = new MainBgPanel();
        mainBg.setLayout(new BorderLayout());

        // Pénz ikon betöltése
        Image moneyIcon = null;
        try {
            // Cseréld ki a saját elérési útvonaladra, ha szükséges!

            //moneyIcon = ImageIO.read(new File("graphical/money.png")); 
            moneyIcon = ImageIO.read(new File("money.png")); 
        } catch (Exception e) {
            System.err.println("Nem található a money.png!");
        }

        // --- FELSŐ SÁV (TOP BAR) ---
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topBar.setOpaque(false);
        
        roundTopPill=new TopPill("KÖR: 1", 220, null);
        topBar.add(roundTopPill);
        modeTopPill=new TopPill("SNOWPLOW MODE", 380, null);
        topBar.add(modeTopPill);
        moneyTopPill=new TopPill("", 160, moneyIcon);
        moneyChanged();
        topBar.add(moneyTopPill);
        
        mainBg.add(topBar, BorderLayout.NORTH);

        // --- JOBB OLDALI SÁV (RIGHT SIDEBAR) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(260, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        

        // 1. STORE gomb
        StyledButton storeBtn = new StyledButton("STORE", 200, 55, Color.decode("#E2E874"));
        storeBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (role instanceof CleanerRole) {
                //StoreScreen storeScreen= new StoreScreen();
                //storeScreen.setVisible(true);
            }
        }
        });
        rightPanel.add(storeBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // 2. Szürke "HEAD" doboz (Most már türkizes-szürke)
        infoBox=new GrayInfoBox();
        headChanged();
        rightPanel.add(infoBox);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 3. SETTINGS és MENU gombok
        StyledButton settingsBtn = new StyledButton("SETTINGS", 200, 55, Color.decode("#E2E874"));
        rightPanel.add(settingsBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        StyledButton menuBtn = new StyledButton("MENU", 200, 55, Color.decode("#E2E874"));
        rightPanel.add(menuBtn);

        mainBg.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainBg);
    }

    private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf"); 
            File fontFile = new File("Silkscreen-Regular.ttf"); 

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 26f);
            silkscreenNormal = customFont.deriveFont(Font.PLAIN, 22f);
            silkscreenSmall = customFont.deriveFont(Font.PLAIN, 16f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus!");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(26f);
            silkscreenNormal = fallback.deriveFont(22f);
            silkscreenSmall = fallback.deriveFont(16f);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            CleanerRole role= new CleanerRole("Cleaner-1", 300, new Snowplow("1", new Lane(), 10,new SweeperHead()));
            java.util.List<Buyable> l=new ArrayList<>();
            l.add(new SaltSpreaderHead());
            l.add(new GravelSpreaderHead());
            l.add(new IcebreakerHead());
            l.add(new ThrowerHead());
            l.add(new SweeperHead());
            l.add(new DragonHead());
            Store store= new Store(l);
            GameScreen screen = new GameScreen(role, store);
            screen.setVisible(true);
        });
    }
}