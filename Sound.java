import java.applet.AudioClip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.applet.*;
import java.net.MalformedURLException;
class Sound
{
    AudioClip sound0;
    AudioClip sound1;
    AudioClip sound2;
    AudioClip sound3;
    AudioClip sound4;
    AudioClip sound5;
    public void load()
    {
        String temp=System.getProperty("user.dir")+"/Sounds/";
        
        temp=temp.replace('\\','/');
        try
        {
            sound0=Applet.newAudioClip((new URL("file:" + temp + "a.mid")));
            sound1=Applet.newAudioClip((new URL("file:" + temp + "b.mid")));
            sound2=Applet.newAudioClip((new URL("file:" + temp + "sound2.mid")));
            sound3=Applet.newAudioClip((new URL("file:" + temp + "laser.wav")));
            sound4=Applet.newAudioClip((new URL("file:" + temp + "d.wav")));
            sound5=Applet.newAudioClip((new URL("file:" + temp + "e.wav")));
        }
        catch (MalformedURLException e)
        {
            System.err.println(e.getMessage());
        }
    }
    public void play(byte clip)
    {
        if(clip==1)
            sound1.play();
        else if(clip==0)
            sound0.play();
        else if(clip==2)
            sound2.play();
        else if(clip==3)
            sound3.play();
        else if(clip==4)
            sound4.play();
        else if(clip==5)
            sound5.play();
    }
    public void loop(byte clip)
    {
        if(clip==1)
            sound1.loop();
        else if(clip==0)
            sound0.loop();
        else if(clip==2)
            sound2.loop();  
        else if(clip==3)
            sound3.loop();
        else if(clip==4)
            sound4.loop();
        else if(clip==5)
            sound5.loop();
    }
    public void stop(byte clip)
    {
        if(clip==1)
            sound1.stop();
        else if(clip==0)
            sound0.stop();
        else if(clip==2)
            sound2.stop();
        else if(clip==3)
            sound3.stop();
        else if(clip==4)
            sound4.play();
        else if(clip==5)
            sound5.stop();
    }
}
    