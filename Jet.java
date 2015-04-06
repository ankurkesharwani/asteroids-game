import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
class Jet
{
    int x=0;
    int y=0;
    int x1[]=new int[6];
    int y1[]=new int[6];
    int x2[]=new int[6];
    int y2[]=new int[6];
    int theeta=0;
    int index=0;
    //int theeta1=0;
    int speed=0;
    int x_disp=0;
    int y_disp=0;
    //boolean explode=false;

    ImageIcon a=new ImageIcon("Sprites/sprite0.png");
    Jet(int x,int y)
    {
        this.x=x;
        this.y=y;

    }

    public void draw(Graphics2D g2)
    {

            AffineTransform at=g2.getTransform();
            AffineTransform saveXform = g2.getTransform();
            at.rotate(Math.toRadians(theeta),x,y);
            at.translate(x-13,y-14);
            g2.drawImage(a.getImage(),at,null); 
            g2.setTransform(saveXform);
            g2.setColor(Color.red); 
            move();

 
    }
    public void move()
    {
        x=x+x_disp;
        y=y+y_disp;
        

        if(x<10)
        {
            x=10;
            x_disp*=-1;
        }
        else if(x>780)
        {
            x=780;
            x_disp*=-1;
        }
        if(y<10)
        {
            y=10;
            y_disp*=-1;
        }
        else if(y>580)
        {
            y=580;
            y_disp*=-1;
        }
    }
}   
    