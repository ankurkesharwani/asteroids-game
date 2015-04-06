import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
class Laser
{
    int x[]=new int[10];
    int y[]=new int[10];
    int theeta[]=new int[10];
    int number;
    void shootLaser( int x, int y, int dir)
    {

        for(int i=0;i<10;i++)
        {
            if(this.x[i]==0&&this.y[i]==0)
            {
                this.x[i]=x;
                this.y[i]=y;
                theeta[i]=dir-90;
                break;
            }
        }

    }
    void move()
    {
        for(int i=0;i<10;i++)
        {
            if(x[i]!=0||y[i]!=0)
            {
                x[i]=x[i]+(int)(25*Math.cos(Math.toRadians(theeta[i])));  
                y[i]=y[i]+(int)(25*Math.sin(Math.toRadians(theeta[i])));  
                if(x[i]<0||x[i]>800||y[i]<0||y[i]>600)
                {
                    x[i]=0;
                    y[i]=0;
                    theeta[i]=0;
                }
            }
        } 
    }
    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.red);
        int x1,x2,y1,y2;
        move(); 
        for(int i=0;i<10;i++)
        {
            if(x[i]!=0||y[i]!=0)
            {
                        x1=x[i]+(int)((-2*Math.cos(Math.toRadians(theeta[i])))-(0*Math.sin(Math.toRadians(theeta[i]))));
                        y1=y[i]+(int)((0*Math.cos(Math.toRadians(theeta[i])))+(-2*Math.sin(Math.toRadians(theeta[i]))));
                        
                        x2=x[i]+(int)((2*Math.cos(Math.toRadians(theeta[i])))-(0*Math.sin(Math.toRadians(theeta[i]))));
                        y2=y[i]+(int)((0*Math.cos(Math.toRadians(theeta[i])))+(2*Math.sin(Math.toRadians(theeta[i]))));
                        g2.drawLine(x1,y1,x2,y2);
            }
        }
    }
}