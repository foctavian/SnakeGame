
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Model {
    private ArrayList<Rectangle> snake;
    private int appleX, appleY;
    private int scale = View.SCALE;
    private int w  = View.WIDTH;
    private int h = View.HEIGHT;

    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    private int move;

    public String gameState;

    private final File score = new File("score.txt");

    Model() {
        snake = new ArrayList<>();
        for(int i=0;i<3;i++){
            Rectangle temp = new Rectangle(scale, scale);
            temp.setLocation((w/2-i)*scale,(h/2)*scale);
            snake.add(temp);
        }
        locate(snake);
        this.move = -1;
        gameState = "start";

    }


    //****************Extras****************
    public void start(){
        gameState = "running";
    }

    public void update(){
        if(gameState.equals("running")){
            if(checkAppleCollision()) {
                grow();
                writeScore(snake.size()-3);
                locate(snake);
            }
            else if(checkWallCollision() || checkSelfCollision()){
                gameState = "end";
            }
            else{
                move();
            }
        }
    }

    public void reset(){
        start();
        snake = new ArrayList<>();
        for(int i=0;i<3;i++){
            Rectangle temp = new Rectangle(scale, scale);
            temp.setLocation((w/2-i)*scale,(h/2)*scale);
            snake.add(temp);
        }
        locate(snake);
        this.move = -1;
    }

    public void writeScore(int s)  {
        FileWriter fw = null;
        FileReader fr = null;
        try{
            fw = new FileWriter(score);
            fr = new FileReader(score);
            if(fr.read() <= s){
                fw.write(String.valueOf(s));
            }
            fw.close();
            fr.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    //****************Apple****************

    public void locate(ArrayList<Rectangle> snake){
        boolean collision = true;
        while(collision){
            collision = false;
            appleX = (int)(Math.random() * (View.WIDTH-View.SCALE));
            appleY = (int)(Math.random() * (View.HEIGHT-View.SCALE));
            for(Rectangle r:snake){
                if (r.x == appleX && r.y == appleY) {
                    collision = true;
                    break;
                }
            }
        }
    }


    //****************Snake****************
    public boolean checkWallCollision(){
        int x = snake.get(0).x;
        int y = snake.get(0).y;
        if( x<0 || x>=View.WIDTH*View.SCALE ||
                y<0 || y>=View.HEIGHT*View.SCALE){
            return true;
        }
        return false;
    }

    public boolean checkSelfCollision(){
        int x = snake.get(0).x;
        int y = snake.get(0).y;
        for(int i=1;i<snake.size();i++){
            if(x == snake.get(i).x && y == snake.get(i).y){
                return true;
            }
        }
        return false;
    }

    public boolean checkAppleCollision(){
        int x = snake.get(0).x;
        int y = snake.get(0).y;
        if(x == appleX*View.SCALE && y == appleY*View.SCALE){
            return true;
        }
        return false;
    }

    public void move(){
        if(move!=-1){
            Rectangle first = snake.get(0);
            Rectangle r = new Rectangle(View.SCALE, View.SCALE);

            if(move == UP){
                r.setLocation(first.x, first.y-View.SCALE);
            }else if(move == DOWN){
                r.setLocation(first.x, first.y+View.SCALE);
            }else if(move == LEFT){
                r.setLocation(first.x - View.SCALE, first.y);
            }else {
                r.setLocation(first.x+View.SCALE, first.y);
            }
            snake.add(0, r);
            snake.remove(snake.size()-1);
        }
    }

    public void grow(){
        if(move!=-1){
            Rectangle first = snake.get(0);
            Rectangle r = new Rectangle(View.SCALE, View.SCALE);

            if(move == UP){
                r.setLocation(first.x, first.y-View.SCALE);
            }else if(move == DOWN){
                r.setLocation(first.x, first.y+View.SCALE);
            }else if(move == LEFT){
                r.setLocation(first.x - View.SCALE, first.y);
            }else {
                r.setLocation(first.x+View.SCALE, first.y);
            }
            snake.add(0, r);
        }
    }

    public void up(){
        if(move!=DOWN){
            move =UP;
        }
    }

    public void down(){
        if(move!=UP){
            move = DOWN;
        }
    }

    public void left(){
        if(move != RIGHT){
            move = LEFT;
        }
    }

    public void right(){
        if(move!=LEFT){
            move = RIGHT;
        }
    }

    //****************Getters****************

    public int getAppleX() {
        return appleX;
    }

    public int getAppleY() {
        return appleY;
    }

    public ArrayList<Rectangle> getSnake() {
        return snake;
    }

    public File getScore() {
        return score;
    }
}
