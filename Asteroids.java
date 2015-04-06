import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
class Asteroids
{
    int x[]=new int[100];
    int y[]=new int[100];
    byte radius[]=new byte[100];
    int theeta[]=new int[100];
    byte x_disp[]=new byte[100];
    byte y_disp[]=new byte[100];
    int number;
    int angle=0;
    int imgNo[]=new int[100];
    ImageIcon a[]=new ImageIcon[6];
    Asteroids(int stage)
    {
        a[0]=new ImageIcon("Sprites/0.png");
        a[1]=new ImageIcon("Sprites/1.png");
        a[2]=new ImageIcon("Sprites/2.png");
        a[3]=new ImageIcon("Sprites/3.png");
        a[4]=new ImageIcon("Sprites/3.png");
        a[5]=new ImageIcon("Sprites/4.png");
        number=stage;

        for(int i=0;i<number;i++)
        {
            x[i]=(int)(Math.random()*1000);
            y[i]=(int)(Math.random()*1000);
            radius[i]=30;       
            theeta[i]=(int)(Math.toRadians(Math.random()*1000));
            x_disp[i]=(byte)(4*Math.cos(theeta[i]));
            y_disp[i]=(byte)(4*Math.sin(theeta[i]));
            if(Math.random()>.05)
                imgNo[i]=0;
            else
                imgNo[i]=1;
            }
    }
    public void explode(int index)
    {
        
        int temp_x,temp_y,temp_theeta,temp_radius;
        temp_x=x[index];
        temp_y=y[index];
        temp_theeta=theeta[index];
        temp_radius=radius[index];
        
        x[index]=x[number-1];
        y[index]=y[number-1];
        theeta[index]=theeta[number-1];
        x_disp[index]=x_disp[number-1];
        y_disp[index]=y_disp[number-1];
        radius[index]=radius[number-1];
        imgNo[index]=imgNo[number-1];
        
        if(temp_radius==7)
        {
            x[number-1]=0;
            y[number-1]=0;
            theeta[number-1]=0;
            x_disp[number-1]=0;
            y_disp[number-1]=0;
            radius[number-1]=0;
            imgNo[number-1]=0;
            number--;
        }    
        else
        {
            number++;
            x[number-2]=temp_x;
            y[number-2]=temp_y;
            theeta[number-2]=temp_theeta-90;
            x_disp[number-2]=(byte)(4*Math.cos(temp_theeta-90));
            y_disp[number-2]=(byte)(4*Math.sin(temp_theeta-90));
            radius[number-2]=(byte)(temp_radius/2);
            if(radius[number-2]==15)
            {
                if(Math.random()>.5)
                    imgNo[number-2]=2;
                else
                    imgNo[number-2]=3;
            }
            else
            {
                //if(Math.random()>.5)
                //    imgNo[number-2]=4;
                //else
                    imgNo[number-2]=5;            
            }
            
            x[number-1]=temp_x;
            y[number-1]=temp_y;
            theeta[number-1]=temp_theeta+90;
            x_disp[number-1]=(byte)(4*Math.cos(temp_theeta));
            y_disp[number-1]=(byte)(4*Math.sin(temp_theeta));
            radius[number-1]=(byte)(temp_radius/2);
            if(radius[number-1]==15)
            {
                if(Math.random()>.5)
                    imgNo[number-1]=2;
                else
                    imgNo[number-1]=3;
            }
            else
            {
                //if(Math.random()>.5)
                //    imgNo[number-1]=4;
                //else
                    imgNo[number-1]=5;            
            }
        }

    }
    public void move()
    {
        angle+=4;
        if(angle>=360)
            angle=0;
        for(int i=0;i<number;i++)
        {
           x[i]=(int)(x[i]+x_disp[i]);
           y[i]=(int)(y[i]+y_disp[i]);
           if(x[i]>750)
           {
                x[i]=750;
                x_disp[i]=(byte)((-1)*x_disp[i]);
           }
           else if(x[i]<50)
           {
                x[i]=50;
                x_disp[i]=(byte)((-1)*x_disp[i]);
           }
           if(y[i]>550)
           {
                y[i]=550;
                y_disp[i]=(byte)((-1)*y_disp[i]);
           }
           else if(y[i]<50)
           {
                y[i]=50;
                y_disp[i]=(byte)((-1)*y_disp[i]);
           }
        }
    }     
    public void draw(Graphics2D g2)
    {
            AffineTransform at;
            AffineTransform saveXform = g2.getTransform();
            g2.setColor(Color.red);
            for(int i=0;i<number;i++)
            {
                at = g2.getTransform();
                
                at.rotate(Math.toRadians(angle),x[i],y[i]);
                at.translate(x[i]-32,y[i]-32);
                g2.drawImage(a[imgNo[i]].getImage(),at,null);
                //g2.drawOval(x[i]-radius[i],y[i]-radius[i],2*radius[i],2*radius[i]);
                g2.setTransform(saveXform);
            }
    }
}