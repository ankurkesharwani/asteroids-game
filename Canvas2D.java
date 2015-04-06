import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.*;
class Canvas2D
{
    private JFrame canvasFrame;
    private CanvasPanel canvasPanel;
    
    private Graphics2D graphics;
    private BufferedImage bi;
    private static GameThread gameThread;
    
    boolean isKeyPressed=false;
    boolean isKeyReleased=false;
    boolean up=false;
    boolean down=false;
    boolean left=false;
    boolean right=false;
    boolean space=false;
    boolean y=false;
    boolean n=false;
    boolean temp=false;
       
    int keyDown;
    int keyUp;
  
    Canvas2D(String title,int width,int height,Color bgColor)
    {
        canvasFrame=new JFrame();
        final int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvasFrame.setBounds ( inset, inset, screenSize.width - inset*2, screenSize.height - inset*2 );
        canvasPanel=new CanvasPanel();
        canvasPanel.setPreferredSize(new Dimension(width, height));
        canvasFrame.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                keyDown=e.getKeyCode();
                if(keyDown==37)
                {
                    left=true;
                    right=false;
                }
                else if(keyDown==39)
                {
                    right=true;
                    left=false;
                }
                if(keyDown==38)
                {
                    up=true;
                    down=false;
                    //temp=true;
                }
                else if(keyDown==40)
                {
                    down=true;
                    up=false;
                    //temp=true;
                }
                
                if(keyDown==89)
                {

                    y=true;
                    n=false;
                }
                if(keyDown==78)
                {

                    y=false;
                    n=true;
                }
                if(keyDown==32)
                {
                    space=true;   
                }
                
            }
            public void keyReleased(KeyEvent e)
            {
                keyUp=e.getKeyCode();
                if(keyUp==37)
                {
                    left=false;
                }
                if(keyUp==38)
                {
                    up=false;
                    temp=true;
                }
                if(keyUp==39)
                {
                    right=false;
                }
                if(keyUp==40)
                {
                    down=false;
                    temp=true;
                }
                if(keyUp==89)
                {
                    y=false;
                }
                if(keyUp==78)
                {
                    n=false;
                }
                if(keyUp==32)
                {
                    space=false;   
                }
            }
        });
        canvasFrame.setContentPane(canvasPanel);
        bi=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        graphics=bi.createGraphics();
        graphics.setColor(bgColor);
        graphics.setColor(Color.black);

        canvasFrame.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });

        canvasFrame.pack();
        canvasFrame.setVisible(true);
        
        gameThread=new GameThread("Game");
        gameThread.run();
        
    }    
    public void erase()
    {
        Dimension size = canvasPanel.getSize();
        graphics.setColor(Color.white);
        graphics.fill(new Rectangle(0, 0, size.width, size.height));
        graphics.setColor(Color.black);
    } 
    public static void main(String []args)
    {
        Canvas2D gameCanvas=new Canvas2D("Ateroids, Developed By: Ankur Kesharwani",800,600,Color.blue);      
    }
    

    private class CanvasPanel extends JPanel
    {
        public void paint(Graphics g)
        {
            g.drawImage(bi, 0, 0, null);
        }
    }
    
    public class GameThread extends Thread
    {
        
        Background b=new Background();
        int stage=1;
        Asteroids a=new Asteroids(stage+1);
        Jet j=new Jet(400,300);
        Laser l=new Laser();
        Sound sound=new Sound();
        Enemy e=new Enemy();
        boolean stageClear=false;
        boolean firstScreen=true;
        boolean gameOver=false;
        int score=0;
        byte life=3;
        public GameThread(String str)
        {
            super(str);
        }
        
        public void run()
        {
                sound.load();
                sound.loop((byte)0);
            while(true)
            {
                try
                {         
                    sleep(60);
                    if(firstScreen==true)
                    {
                        startup(graphics);
                        
                    }
                    else if(stageClear==false&&gameOver!=true)
                        gameScript();
                    else if(stageClear==true)
                        nextStage(graphics);
                    else if(gameOver==true)
                        gameOverScript(100,graphics);
                    canvasPanel.repaint();                     
                }
                catch(Exception error)
                {
                    System.out.println(error);
                }
            }
        }
        private void startup(Graphics g2D)throws Exception
        {
            ImageIcon spaceImage=new ImageIcon("Sprites/startup.png");
            g2D.drawImage(spaceImage.getImage(),0,0,null);
            g2D.setColor(Color.cyan);
            g2D.drawString("Play the game: Y/N (Pressing N will close the application)",258,410);
            if(y)
            {  
                firstScreen=false;               
                sound.stop((byte)0);
                sound.stop((byte)1);
                sound.stop((byte)2);
                sleep(1000);
                restartStage(graphics,1);
                //sound.play((byte)4);
                sound.loop((byte)1);
            }
            else if(n)
                System.exit(0);            
            
        }
        public void gameOverScript(int value,Graphics2D g2D)throws Exception
        {
            b.draw(g2D);
            a.move();
            a.draw(g2D);
            g2D.setColor(Color.red);
            graphics.drawString("Game Over !",373,280);
            String score1="Your Score: "+String.valueOf(score);
            graphics.drawString(score1,364,300);
            graphics.drawString("Developed by: Ankur kesharwani",320,320);
            graphics.setColor(Color.yellow);
            graphics.drawString("Restart the game: Y/N (Pressing N will close the application)",245,360);
            if(y)
            {    
                gameOver=false;
                firstScreen=false;
                restartStage(graphics,1);
                life=3;
                score=0;

            }
            else if(n)
                System.exit(0);
        }
        private void restartStage(Graphics g2D,int stage)throws Exception
        {
            sound.stop((byte)0);
            //sound.stop((byte)1);
            sound.stop((byte)2);
           // sound.play((byte)4);
            
            stageClear=false;
            b.draw(graphics);
            g2D.setColor(Color.red);
            g2D.drawString("Stage " + String.valueOf(stage),385,300);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.yellow);
            g2D.drawString("Stage " + String.valueOf(stage),385,300);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.blue);
            g2D.drawString("Stage " + String.valueOf(stage),385,300);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.magenta);
            g2D.drawString("Stage " + String.valueOf(stage),385,300);
            sleep(500);canvasPanel.repaint();
            a=new Asteroids(stage+1);
            j=new Jet(400,300);
            l=new Laser(); 
            e=new Enemy();
            //sound.loop((byte)1);
        }
        private void nextStage(Graphics2D g2D)throws Exception
        {
            
            sound.stop((byte)0);
            sound.stop((byte)2);
            sound.stop((byte)1);
            
            sleep(1000);
            sound.play((byte)4);
            stage++;
            stageClear=false;
            b.draw(g2D);
            g2D.setColor(Color.red);
            graphics.drawString("Stage Clear !..",370,270);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.yellow);
            g2D.drawString("Stage Clear !..",370,270);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.blue);
            g2D.drawString("Stage Clear !..",370,270);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.magenta);
            g2D.drawString("Stage Clear !..",370,270);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.red);
            g2D.drawString("Stage " + String.valueOf(stage),383,300);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.yellow);
            g2D.drawString("Stage " + String.valueOf(stage),383,300);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.blue);
            g2D.drawString("Stage " + String.valueOf(stage),383,300);
            sleep(500);canvasPanel.repaint();
            g2D.setColor(Color.magenta);
            g2D.drawString("Stage " + String.valueOf(stage),383,300);
            sleep(500);canvasPanel.repaint();
            a=new Asteroids(stage+1);
            j=new Jet(400,300);
            l=new Laser();
            e=new Enemy();
            sound.play((byte)1);
        }
        public void drawScore(int value,Graphics2D g2D)
        {
            g2D.setColor(Color.red);
            String score1="Your Score: "+String.valueOf(value);
            g2D.drawString(score1,10,20);
            g2D.drawString("Developed by: Ankur kesharwani",610,20);
            if(life==3)
            {
                g2D.drawImage(new ImageIcon("life.png").getImage(),10,25,null);
                g2D.drawImage(new ImageIcon("life.png").getImage(),30,25,null);
                g2D.drawImage(new ImageIcon("life.png").getImage(),50,25,null);
            }
            else if(life==2)
            {
                g2D.drawImage(new ImageIcon("life.png").getImage(),10,25,null);
                g2D.drawImage(new ImageIcon("life.png").getImage(),30,25,null);
            }
            else if(life==1)
                g2D.drawImage(new ImageIcon("life.png").getImage(),10,25,null);
                 
        }
        public void explodeShip(Graphics2D g2D)throws Exception
        {
            ImageIcon e[]=new ImageIcon[8];
            e[0]=new ImageIcon("Sprites/e1.png");
            e[1]=new ImageIcon("Sprites/e2.png");
            e[2]=new ImageIcon("Sprites/e3.png");
            e[3]=new ImageIcon("Sprites/e4.png");
            e[4]=new ImageIcon("Sprites/e5.png");
            e[5]=new ImageIcon("Sprites/e6.png");
            e[6]=new ImageIcon("Sprites/e7.png");
            e[7]=new ImageIcon("Sprites/e8.png");
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[0].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[1].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[2].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[3].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[4].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[5].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[6].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();sleep(100);
            b.draw(graphics);a.draw(graphics);g2D.drawImage(e[7].getImage(),j.x-16,j.y-16,null);canvasPanel.repaint();
        }
        private void checkCollision()throws Exception
        {
            int dist;
            for(int i=0;i<a.number;i++)
            {
                for(int k=0;k<10;k++)
                {
                    if(l.x[k]!=0||l.y[k]!=0)
                    {
                        dist=((l.x[k]-a.x[i])*(l.x[k]-a.x[i]))+((l.y[k]-a.y[i])*(l.y[k]-a.y[i]));
                        if(dist<(a.radius[i]*a.radius[i]))
                        {
                            l.x[i]=0;
                            l.y[i]=0;
                            a.explode(i);
                            score+=10;
                            sound.play((byte)5);
                        }
                    }
                }
                if((((j.x-a.x[i])*(j.x-a.x[i]))+((j.y-a.y[i])*(j.y-a.y[i])))<(a.radius[i]*a.radius[i])+10)
                {
                    sound.play((byte)5);
                    explodeShip(graphics);
                    decreaseLife();
                }
            }
            if((((j.x-e.x)*(j.x-e.x))+((j.y-e.y)*(j.y-e.y)))<100)
            {
                sound.play((byte)5);
                explodeShip(graphics);
                decreaseLife();
            }
            for(int k=0;k<10;k++)
            {
               if(l.x[k]!=0||l.y[k]!=0)
               {
                    dist=((l.x[k]-e.x)*(l.x[k]-e.x))+((l.y[k]-e.y)*(l.y[k]-e.y));
                        if(dist<(100))
                        {
                            l.x[k]=0;
                            l.y[k]=0;
                            //a.explode(i);
                            e.explode=true;
                            score+=50;
                            sound.play((byte)5);
                        }
                    }
                }
        }
        private void decreaseLife()throws Exception
        {
            
            //sleep(1000);            
            erase();
            life--;
            b.draw(graphics);canvasPanel.repaint();
            sleep(500);
            graphics.setColor(Color.red);
            graphics.drawString("You are Hit !..",373,300);canvasPanel.repaint();
            graphics.setColor(Color.yellow);
            sleep(250);
            graphics.setColor(Color.yellow);
            graphics.drawString("Life remaining: " + String.valueOf(life),363,320);canvasPanel.repaint();
            sleep(250);
            graphics.setColor(Color.blue);
            graphics.drawString("Life remaining: " + String.valueOf(life),363,320);canvasPanel.repaint();
            sleep(250);
            graphics.setColor(Color.magenta);
            graphics.drawString("Life remaining: " + String.valueOf(life),363,320);canvasPanel.repaint();
            sleep(250);
            if(life<=0)
            {
                gameOver=true;
                sound.stop((byte)0);
                sound.stop((byte)1);
                sound.loop((byte)2);
                
            }
            else
            restartStage(graphics,stage);
        }
        public void gameScript()throws Exception
        {
            b.draw(graphics);
            a.move();
            a.draw(graphics);
            j.draw(graphics);
            //e.move(graphics,j.x,j.y);
            //e.draw(graphics,j.x,j.y);

            e.draw(graphics,j.x,j.y);
            if(left)
            {
                j.theeta=j.theeta-10;
            }
            else if(right)
            {
                j.theeta=j.theeta+10;
            }
            if(up)
            {   
                j.x_disp+=2*Math.cos(Math.toRadians(j.theeta-90));
                j.y_disp+=2*Math.sin(Math.toRadians(j.theeta-90));
            }
            else
            {
                   
            }
            if(space)
            {
                 l.shootLaser(j.x,j.y,j.theeta);  
                 sound.play((byte)3);
            }
            l.draw(graphics);
            checkCollision();
            canvasPanel.repaint(); 
            graphics.setColor(Color.red);
            if(a.number==0)
            {
                stageClear=true;
            }
            drawScore(score,graphics);
        }
    } 
}