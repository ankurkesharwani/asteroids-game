import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
class Enemy
{
    int x=0;
    int y=0;
    double theeta=0;
    int speed=0;
    double x_disp=0;
    double y_disp=0;
    ImageIcon a=new ImageIcon("Sprites/sprite1.png");
    ImageIcon e[]=new ImageIcon[8];
    boolean free=true;
    boolean explode=false;
    int index=0;
    Enemy()
    {
        e[0]=new ImageIcon("Sprites/e1.png");
        e[1]=new ImageIcon("Sprites/e2.png");
        e[2]=new ImageIcon("Sprites/e3.png");
        e[3]=new ImageIcon("Sprites/e4.png");
        e[4]=new ImageIcon("Sprites/e5.png");
        e[5]=new ImageIcon("Sprites/e6.png");
        e[6]=new ImageIcon("Sprites/e7.png");
        e[7]=new ImageIcon("Sprites/e8.png");
    }
    
    public void sendEnemy(int j_x,int j_y)
    {
        if(free)
        {
            if(Math.random()>=0.5)
            {
                x=0;
                y=(int)(Math.random()*100);
            }
            else
            {
                x=800;
                y=(int)(Math.random()*100);
            }
            if(Math.random()>=0.5)
            {
                y=0;
                x=(int)(Math.random()*100);
            }
            else
            {
                y=600;
                x=(int)(Math.random()*100);
            }
            theeta=Math.atan((double)((double)(j_y-y)/(double)(j_x-x)));
            x_disp=(byte)(10*Math.cos(theeta));
            y_disp=(byte)(10*Math.sin(theeta));
            free=false;
            explode=false;
        }
        
    }
    public void draw(Graphics2D g2,int j_x,int j_y)
    {
        if(!explode)
        move(j_x,j_y);
        if(!free)
        {
            
            if(!explode)
            {           
                AffineTransform at=g2.getTransform();
                AffineTransform saveXform = g2.getTransform();
                at.rotate(theeta+Math.toRadians(90),x,y);
                at.translate(x-13,y-14);
                g2.drawImage(a.getImage(),at,null); 
                g2.setTransform(saveXform);
                g2.setColor(Color.red); 
            }
            else
            {
                g2.drawImage(e[index].getImage(),x-16,y-16,null);        
                index++;
                if(index>=8)
                {
                    free=true;
                    explode=false;
                    index=0;
                    x=0;
                    y=0;
                }
            }
        }
    }
    public void move(int j_x,int j_y)
    {
        if(free&&(Math.random()*100)<5)
        {
            sendEnemy(j_x,j_y);
        }
        if(!free)
        {
            theeta=Math.atan((double)((double)(j_y-y)/(double)(j_x-x)));
            x_disp=(byte)(7*Math.cos(theeta));
            y_disp=(byte)(7*Math.sin(theeta));
            x=(int)(x+x_disp);
            y=(int)(y+y_disp);

        }
                    if(x<0||x>800||y<0||y>600)
                free=true;
    }
}
