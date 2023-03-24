import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import java.util.Random;

public class CaveApplication extends JFrame implements Runnable{

    private Graphics g;
    private Random random = new Random();

    private boolean initialised = false;

    private boolean caveCells[][] = new boolean[200][200];

    public CaveApplication(){
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width/2 - 400;
        int y = screensize.height/2 - 400;
        setBounds(x, y, 800, 800);
        setVisible(true);
        this.setTitle("Procedurally Generated Caves");

        for(int a = 0; a < 200; a++){
            for(int b = 0; b < 200; b++){
                caveCells[a][b] = false;
            }
        }


        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {

                caveCells[i][j] = randomiseCell();
            }
        }

        Thread t = new Thread(this);
        t.start();

        initialised = true;
    }

    public void run(){
        while(1 == 1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }

            applyRules();
            this.repaint();
        }
    }

    public void applyRules() {


        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {

                int liveNeighbours = 0;
                for (int xx = -1; xx <= 1; xx++) {
                    for (int yy = -1; yy <= 1; yy++) {
                        if (xx != 0 || yy != 0) {
                            int xxx = x + xx;
                            if (xxx < 0)
                                xxx = 199;
                            else if (xxx > 199)
                                xxx = 0;
                            int yyy = y + yy;
                            if (yyy < 0)
                                yyy = 199;
                            else if (yyy > 199)
                                yyy = 0;

                            if(caveCells[xxx][yyy]){
                                liveNeighbours++;
                            }
                        }
                    }

                }

                if(caveCells[x][y]){
                    if(liveNeighbours < 4){
                        caveCells[x][y] = false;
                    }
                    else {
                        caveCells[x][y] = true;
                    }
                }
                else {
                    if(liveNeighbours >= 5){
                        caveCells[x][y] = true;
                    }
                    else {
                        caveCells[x][y] = false;
                    }
                }

            }

        }


    }

    public boolean randomiseCell(){
        int rand = random.nextInt(10);
        if (rand <= 4) {
            return false; // 40% chance of floor cell
        } else {
            return true;  // 60% chance of wall cell
        }
    }



        public void paint (Graphics g){
            if (!initialised) {
                return;
            }

            //Set background to black
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 800);


            g.setColor(Color.WHITE);
            for (int i = 0; i < 200; i++) {
                for (int j = 0; j < 200; j++) {
                    if(caveCells[i][j]){
                        g.fillRect(i*4, j*4, 4, 4);
                    }
                }
            }
        }

        public static void main (String args[]){
            CaveApplication w = new CaveApplication();
        }


    }