import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller extends JPanel implements KeyListener {
    private View view;
    private Model model;

    Controller(Model model,View view){
        this.view = view;
        this.model = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(model.gameState.equals("running")) {
            if (keyCode == KeyEvent.VK_W) {
                model.up();
            } else if (keyCode == KeyEvent.VK_S) {
                model.down();
            } else if (keyCode == KeyEvent.VK_A) {
                model.left();
            } else if (keyCode == KeyEvent.VK_D) {
                model.right();
            }
        }
        else if(model.gameState.equals("end")){

            if(keyCode == KeyEvent.VK_ENTER){
                model.reset();
            }
        }
        else{
            model.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
