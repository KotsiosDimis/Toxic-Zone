package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Afysikos
 */
public class AudioPlayer {
    
    //metablhtes 3 diafores song gia menu kai levels gia ayto apo to 0 mexri 2
    /////Levels & UI  //////
    public static int MENU = 0;
    public static int LEVEL_1=1;
    public static int LEVEL_2=2;
    public static int LEVEL_3=3;
    public static int LVL_COMPLETED=3;
    
    
    
    /////Player Actions//////
    public static int DIE = 0;
    public static int JUMP =1;
    public static int GAMEOVER = 2;
    public static int ATTACK_1=4;
      
      //tous hxoys ua toys baloyme se pinakes gia na toys xeiristoyme
      //tous hous toys pairneis apto inputstream kai tous anoigeis mesa se ayto to clip
      //me to clip mporoyme na stamathsoyme ton hxo na allaksoyme to volume na to stamathsoyme gia ayto to bazoyme se clip
      //den einai h kalyterh meuodos to clip mporei na yparxoyn kapoia delays me ton typo arxeioy poy mono painrei poy einai wav
      private Clip[] songs,effects;
      private int currentSongID;//gia na kseroyme to twrino sound poy paizei
      private float volume=0.5f; //1 //edw dinoyme timh sto synoliko volume   0.7 kanoniko    0.5 testing 
      private boolean songMute,effectMute;
      private Random rand = new Random();//ayth h meuodo xrhsisimopoieitai gia atack sounds gia na allazei random toys hxoys
      
      
    public AudioPlayer(){
      loadSongs();
      loadEffects();
      playSong(MENU);//edw  paizei to song kaue fora poy arxizoyme to game dld to menu
    }
    
    private void loadSongs(){
        String[ ] names = {"menu","level1","level2","level3"};//ftiaxnoyme pinaka me tous hxous 
        songs = new Clip[names.length];//dhlwnoyme ton pianaka
        for(int  i = 0; i < songs.length ; i++){
                songs[i] = getClip(names[i]);   
        }
    }
    
    private void loadEffects(){
        String[] effectNames = {"deathplayer","jump","gameover","lvlcompleted","attack1"};
        effects = new Clip[effectNames.length];
        
        for (int i = 0; i  < effects.length; i++){
                effects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();//edw bazoyme thn entash poy exoume settarei afou exoyme fortwsei ola mas ta effects
            //wste otan arxizoyme to game oi hxoi na mhn einai terma         
    }
    
    private Clip getClip(String name) 
           
   
    {//meuodo poy ua xrhsimopoihsoyme otan fortwnoyme songs h effects
        
        //ua xreiastoyme url gia na exoyme access sta wav files
        //me to /audio/ eimaste mesa sto audio folder
        URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
                          //edw bazoyme clip  gia na apouhkefsoyme ayto to audio
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	
    }
    
    public void setVolume(float volume)//otan allazoume me to slider to volume ayth h meuodos einai poy to kanei
    {
     this.volume=volume;
     //otan mpainoyme sthn meuodo dld allazei kai to volume twn song kai twn effects
     updateSongVolume();
     updateEffectsVolume();
    }
    
    public void stopSong()//ayth h meuodos stamataei to tragoudi poy paizei
   {
         if(songs[currentSongID].isActive())//ean edw ena prohgoumeno song paizei apo thn prohgoumenh pista
           songs[currentSongID].stop();//stamataei
    }

        public void setLevelSong(int lvlIndex)//bazoyme ta if dioti se kaue level ueloyme na paizei mono ena song
        {
           // if(lvlIndex % 2 == 0)
            if(lvlIndex == 0)
            {
                playSong(LEVEL_1);
            }
            else if(lvlIndex == 1){
                playSong(LEVEL_2);
            }
            else if(lvlIndex == 2){
                playSong(LEVEL_3);
        }
        }
        public void lvlCompleted()//edw  otan teleiwnei to level ua kanei kati sygkekrimeno
        {
            stopSong();//ueloyme na stamaei to song 
            playEffect(LVL_COMPLETED);//mpainei to effect otan teleiwnei to level
        }
        
    public void playAttackSound()//o paikths kaue fora poy baraei ua akougetai o hxos
    {
        int start = 4;//to 4 einai sto effectnames poy balame to atack1 
        playEffect(start);
    }
    
    public void playEffect(int effect){
        effects[effect].setMicrosecondPosition(0);//kanoyme reset na paei sthn arxh
        effects[effect].start();//kai paizoyme to effect
    }
    
    public void playSong(int song){
       stopSong();//kaloyme thn meuodo gia na stamathsei to prohgoymeno tragoudi poy paizei
        
      currentSongID = song;//allazoyme to id sto kainourgio song
      updateSongVolume();//kanoyme update thn entash
      songs[currentSongID].setMicrosecondPosition(0);//kanoyme reset to neo tragoudi gia na paei sthn arxh
      songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);//kai ksanapaizoyme to tragoudi otan teleiwnei ksnapaizei apthn arxh poy balame mesa to loop
    }
    
    //aytoi oi meuodoi einai gia na mutarume ton hxo
    //ua mutarume ta panta kateyuian
    public void toggleSongMute()
    {
        this.songMute = !songMute;
        for (Clip c : songs){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }
    
    public void toggleEffectMute()
    {
        this.effectMute = !effectMute;
        for (Clip c : effects){
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if(!effectMute){
            playEffect(JUMP);
        }
    }
    private void updateSongVolume()//edw kanoyme update to trexon song pou paizei dioti mono ena song ua paizei sto background
    {//to songs[currentSongID] einai to clip  poy paizei kai toy dinoyme control me to getcontrol sto gain
        FloatControl gainControl = (FloatControl)  songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }
    private void updateEffectsVolume()//edw kanoyme update ta effects ola mazi gt otan paizoyme ua akougontai ola mazi
    {
        for(Clip c : effects){
        FloatControl gainControl = (FloatControl)  c.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain =  (range*volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
        }
    }
}
