import java.applet.Applet;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CarAnim extends Applet implements Runnable {
    Car theCar;

    public void init() {
        theCar = new Car(250, 250);
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void paint(Graphics g) {
        int[] polyx = new int[4];
        int[] polyy = new int[4];

        // road
        Color color=new Color(108, 45, 0);
        g.setColor(color);
        polyx[0] = 0;
        polyx[1] = 1366;
        polyx[2] = 1366;
        polyx[3] = 0;
        polyy[0] = 300;
        polyy[1] = 300;
        polyy[2] = 400;
        polyy[3] = 400;
        g.fillPolygon(polyx, polyy, 4);

        theCar.draw(g);

        // sun
        g.setColor(Color.YELLOW);
        g.fillOval(650, 25, 120, 120);

        // tree stem
        color=new Color(209, 76, 8);
        g.setColor(color);
        polyx[0] = 855;
        polyx[1] = 865;
        polyx[2] = 865;
        polyx[3] = 855;
        polyy[0] = 200;
        polyy[1] = 200;
        polyy[2] = 400;
        polyy[3] = 400;
        g.fillPolygon(polyx, polyy, 4);

        // tree leaves
        g.setColor(Color.GREEN);
        g.fillOval(840, 150, 100, 100);
        g.fillOval(780, 150, 100, 100);
        g.fillOval(800, 70, 100, 100);
        g.fillOval(775, 60, 100, 100);
    }

    public void run() {

        for(int i=0; i<500; i++){
            theCar.move();
            repaint();
            try{
                TimeUnit.MILLISECONDS.sleep( 10);
            }catch (InterruptedException iex){
                iex.printStackTrace();
            }
            i=0;
        }
        /*while (true) {
            try {
                theCar.move();
                Thread.sleep(50);
                repaint();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }*/
    }
}

class Coordinate {
    int x, y;

    public Coordinate(int xx, int yy) {
        x = xx;
        y = yy;
    }
}

class Car {
    public Coordinate body;
    public Coordinate[] cockpit;
    public Coordinate[] window1;
    public Coordinate[] window2;
    public Coordinate tyre1;
    public Coordinate tyre2;
    public Coordinate rim1;
    public Coordinate rim2;
    public int[] arr_x;
    public int[] arr_y;
    int movement;

    public Car(int startX, int startY) {
        movement = 1; // move right
        arr_x = new int[4];
        arr_y = new int[4];
        body = new Coordinate(startX, startY);

        cockpit = new Coordinate[4];
        cockpit[0] = new Coordinate(startX+50, startY);
        cockpit[1] = new Coordinate(startX + 300, startY);
        cockpit[2] = new Coordinate(startX + 300, startY - 100);
        cockpit[3] = new Coordinate(startX + 125, startY - 100);

        window1 = new Coordinate[4];
        window1[0] = new Coordinate(cockpit[0].x + 20, cockpit[0].y);
        window1[1] = new Coordinate(cockpit[0].x + 100, cockpit[0].y);
        window1[2] = new Coordinate(cockpit[0].x + 100, cockpit[3].y + 20);
        window1[3] = new Coordinate(cockpit[3].x + 5, cockpit[3].y + 20);

        window2 = new Coordinate[4];
        window2[0] = new Coordinate(window1[1].x + 20, window1[1].y);
        window2[1] = new Coordinate(cockpit[1].x - 20, window1[1].y);
        window2[2] = new Coordinate(cockpit[2].x - 20, window1[2].y);
        window2[3] = new Coordinate(window1[2].x + 20, window1[2].y);

        tyre1 = new Coordinate(body.x + 40, body.y + 65);
        tyre2 = new Coordinate(body.x + 290, body.y + 65);

        rim1 = new Coordinate(tyre1.x + 10, tyre1.y + 10);
        rim2 = new Coordinate(tyre2.x + 10, tyre2.y + 10);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(body.x, body.y, 400, 100);

        coords2Arrays(arr_x, arr_y, cockpit);
        g.fillPolygon(arr_x, arr_y, 4);

        g.setColor(Color.WHITE);
        coords2Arrays(arr_x, arr_y, window1);
        g.fillPolygon(arr_x, arr_y, 4);
        coords2Arrays(arr_x, arr_y, window2);
        g.fillPolygon(arr_x, arr_y, 4);

        g.setColor(Color.BLACK);
        g.fillOval(tyre1.x, tyre1.y, 70, 70);
        g.fillOval(tyre2.x, tyre2.y, 70, 70);
        g.setColor(Color.WHITE);
        g.fillOval(rim1.x, rim1.y, 50, 50);
        g.fillOval(rim2.x, rim2.y, 50, 50);
    }

    private void coords2Arrays(int[] array_x, int[] array_y, Coordinate[] coordinates) {
        for (int i = 0; i < coordinates.length; i++) {
            array_x[i] = coordinates[i].x;
            array_y[i] = coordinates[i].y;
        }
    }

    public void move() {
        int check=body.x;
        if(check<=-500){
            updateValues(1900, 0);
        }else {
            updateValues(-10, 0);
        }
    }

    public void updateValues(int x, int y) {
        body.x += x;
        body.y += y;
        for (Coordinate c : cockpit) {
            c.x += x;
            c.y += y;
        }
        for (Coordinate c : window1) {
            c.x += x;
            c.y += y;
        }
        for (Coordinate c : window2) {
            c.x += x;
            c.y += y;
        }
        tyre1.x += x;
        tyre1.y += y;
        tyre2.x += x;
        tyre2.y += y;
        rim1.x += x;
        rim1.y += y;
        rim2.x += x;
        rim2.y += y;
    }
},,