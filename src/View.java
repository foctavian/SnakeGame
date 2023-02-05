import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class View extends JPanel implements ActionListener {
    public Timer timer= new Timer(100, this);
    private JFrame frame = new JFrame();
    public static final int WIDTH = 30;
    public static final int HEIGHT =30;
    public static final int SCALE = 20;

    private Model model;

    View(Model model){
        frame.setTitle("Snake");
        frame.add(this);
        timer.start();
        this.model = model;
        this.setFocusable(true);
        addKeyListener(new Controller(model, this));
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("src/resources/icon.png").getImage());
        frame.setSize(WIDTH*SCALE, HEIGHT*SCALE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2d = (Graphics)g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0, View.WIDTH *View.SCALE,View.HEIGHT*View.SCALE);
        if(model.gameState.equals("start")){
            g2d.setColor(Color.white);
            g2d.drawString("Press any key", View.WIDTH/2 * View.SCALE - 40, View.HEIGHT / 2 * View.SCALE - 20);
        }
        else if (model.gameState.equals("running")) {
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, View.WIDTH * View.SCALE, View.HEIGHT * View.SCALE);
            g2d.setColor(Color.red);
            g2d.fillRect(model.getAppleX() * View.SCALE, model.getAppleY() * View.SCALE, View.SCALE, View.SCALE);

            g2d.setColor(Color.green);
            for (Rectangle r : model.getSnake()) {
                g2d.fillRect(r.x, r.y, View.SCALE, View.SCALE);
            }

            g2d.setColor(Color.white);
            String hs;
            try {
                FileReader fr = new FileReader(model.getScore());
                hs = "High Score: "+Character.getNumericValue(fr.read());
                fr.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g2d.drawString(hs,0,10 );
            String s = "Score: "+ (model.getSnake().size()-3);
            g2d.drawString(s,0, 30);
        }
        else{
            g2d.setColor(Color.white);
            g2d.drawString("Score : "+(model.getSnake().size() - 3), View.WIDTH/2 * View.SCALE - 40, View.HEIGHT / 2 * View.SCALE - 20);

            g2d.drawString("Press ENTER to try again!",  View.WIDTH/2 * View.SCALE - 80, View.HEIGHT / 2 * View.SCALE - 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        model.update();
    }
}
