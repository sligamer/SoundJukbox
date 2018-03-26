package com.lab56.sligamer.soundjukbox

import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseIntArray
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import android.media.AudioAttributes
import android.os.Build



/**
 * Created by Justin Freres on 3/24/2018.
 * Lab 5-6 Juke Box
 * Plugin Support with kotlin_version = '1.2.21'
 */

class MainActivity : AppCompatActivity() {

    private lateinit var bellClangBtn: ImageButton
    private lateinit var funkyGongBtn: ImageButton
    private lateinit var spookyCryBtn: ImageButton
    private lateinit var randomHaBtn: ImageButton
    private lateinit var drumSoloBtn: ImageButton

    lateinit var soundPool: SoundPool
    lateinit var soundMap: SparseIntArray
    lateinit var mMediaPlayer: MediaPlayer
    lateinit var mMediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureSounds()
        initializeJukeBoxBtns()
    }

    private fun initializeJukeBoxBtns() {

        // SET REFERENCES TO THE SOUND EFFECTS BUTTONS ON THE LAYOUT
        bellClangBtn = findViewById<ImageButton>(R.id.bellClangBtn)
        funkyGongBtn = findViewById(R.id.funkyGongBtn)
        spookyCryBtn = findViewById(R.id.spookyCryBtn)
        randomHaBtn = findViewById(R.id.randomHaBtn)
        drumSoloBtn = findViewById(R.id.drumSoloBtn)

        // REGISTER LISTENER EVENTS FOR THE BUTTONS ON THE LAYOUT
        bellClangBtn.setOnClickListener(playSoundEffect)
        funkyGongBtn.setOnClickListener(playSoundEffect)
        spookyCryBtn.setOnClickListener(playSoundEffect)
        randomHaBtn.setOnClickListener(playSoundEffect)
        drumSoloBtn.setOnClickListener(playSoundEffect)

    }

    val playSoundEffect = View.OnClickListener { view ->

        // IDENTIFY THE SOUND TO BE PLAYED
        var soundName = view.contentDescription

        // PLAY THE SOUND
        when {
            soundName.equals("Bell Clang") -> soundPool.play(1,1.0f,1.0f,1,0,1.0f)
            soundName.equals("Funky Gong") -> soundPool.play(2,1.0f,1.0f,1,0,1.0f)
            soundName.equals("Random Ha") -> soundPool.play(3,1.0f,1.0f,1,0,1.0f)
            soundName.equals("Spooky Cry") -> soundPool.play(4,1.0f,1.0f,1,0,1.0f)
            soundName.equals("Drum Solo") -> {
                mMediaController.show()
                mMediaPlayer.start()
            }
        }
    }


    private fun configureSounds() {
        // CONFIG SOUNDS TO USE IN THE JUKEBOX
        // PRE LOAD THE FIRST FOUR SOUNDS

        // SOUNDPOOL DEPRECATED IN JAVA
        //https://stackoverflow.com/questions/28210921/set-audio-attributes-in-soundpool-builder-class-for-api-21
        //soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)

        soundPool = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {

                val audioAttrib = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                SoundPool.Builder().setAudioAttributes(audioAttrib).setMaxStreams(1).build()
            }
            else -> SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }


        soundMap = SparseIntArray(4)

        soundMap.put(1, soundPool.load(this, R.raw.bell_clang, 1))
        soundMap.put(2, soundPool.load(this, R.raw.funky_gong, 1))
        soundMap.put(3, soundPool.load(this, R.raw.spooky_cry, 1))
        soundMap.put(4, soundPool.load(this, R.raw.random_ha, 1))

        // FIFTH SOUND WILL BE PLAYED IN MEDIA PLAYER
        mMediaPlayer = MediaPlayer.create(this, R.raw.drum)
        mMediaController = MediaController(this)
        mMediaController.isEnabled = true

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId
        if(id == R.string.action_settings){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
