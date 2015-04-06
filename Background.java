import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
class Background
{
    ImageIcon spaceImage;
    Background()
    {
        spaceImage=new ImageIcon("Sprites/space.png");
    }
    public void draw(Graphics2D g2)
    {
        g2.drawImage(spaceImage.getImage(),0,0,null);       
    }
}