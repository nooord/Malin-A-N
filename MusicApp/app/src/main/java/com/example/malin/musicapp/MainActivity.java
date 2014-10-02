package com.example.malin.musicapp;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class MainActivity extends ListActivity implements View.OnClickListener{

    public final static String TRACK = "track";
    public final static String ACTION = "action";

    // Const Variables
    private int currentTrack = 0;
    private boolean isPlaying = false;
    private boolean isPaused = false;

    final static int PLAY = 1;
    final static int PAUSE = 2;
    final static int PLAY_SONG = 3;

    ImageButton playBtn;
    ImageButton nextBtn;
    ImageButton prevBtn;

    ArrayList<Track> trackList;


    // On create method, it create and check if a track list is available.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkIfStorageAvailable()){
            Log.d("MusicActivity", "Ja, den är tillgänglig!");

            trackList = getPlaylist();
            // Create a array adapter for the track list.
            ArrayAdapter<Track> musicAdapter = new ArrayAdapter<Track>(this, android.R.layout.simple_list_item_1, trackList);

            setListAdapter(musicAdapter);

            // Buttons
            playBtn = (ImageButton)findViewById(R.id.playBtn);
            nextBtn = (ImageButton)findViewById(R.id.nextBtn);
            prevBtn = (ImageButton)findViewById(R.id.prevBtn);

            // Buttons with click listener.
            playBtn.setOnClickListener(this);
            nextBtn.setOnClickListener(this);
            prevBtn.setOnClickListener(this);
        }

    }
     // Change the playlist from a cursor to an arraylist.
    private ArrayList<Track> getPlaylist() {
        Cursor musicResult = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] {
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.DATA },
                MediaStore.Audio.Media.IS_MUSIC + " > 0 ",
                null,
                null
        );

        ArrayList<Track> trackList = new ArrayList<Track>();

        if (musicResult.getCount() > 0) {
            musicResult.moveToFirst();
            Track prev = null;
            do {
                Track track = new Track(
                        musicResult.getString(musicResult.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                        musicResult.getString(musicResult.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                        musicResult.getString(musicResult.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                        musicResult.getString(musicResult.getColumnIndex(MediaStore.Audio.Media.DATA))
                );

                if (prev != null) //Here prev song linked to current one. To simple play them in list
                    prev.setNext(track);

                prev = track;
                trackList.add(track);
            }
            while (musicResult.moveToNext());

            prev.setNext(trackList.get(0)); // Play in cycle;
        }
        Log.d("MusicActivity", "" + musicResult.getCount());
        musicResult.close();

        return trackList;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        Intent intentService = new Intent(this, MusicService.class);
        currentTrack = position;
        intentService.putExtra(TRACK, currentTrack).putExtra(ACTION, PLAY_SONG);
        startService(intentService);

        playBtn.setImageResource(R.drawable.ic_action_pause);
        isPlaying = true;
    }



    private boolean checkIfStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intentService = new Intent(this, MusicService.class);
        //When press play button
        if (view.getId() == R.id.playBtn) {
            if (isPlaying) {
                intentService.putExtra(ACTION, PAUSE);
                playBtn.setImageResource(R.drawable.ic_action_play);
                isPlaying = false;
                isPaused = true;
                startService(intentService);
            }
            // If the song is paused, it will start again from paused place.
            else {
                if (isPaused) {
                    intentService.putExtra(ACTION, PLAY);
                    playBtn.setImageResource(R.drawable.ic_action_pause);
                    isPlaying = true;
                    startService(intentService);
                    }
               else {
                    intentService.putExtra(ACTION, PLAY_SONG);
                    playBtn.setImageResource(R.drawable.ic_action_pause);
                    isPlaying = true;
                    startService(intentService);
                }
            }
          //When press next button, it´s going to the next track.
        } else if (view.getId() == R.id.nextBtn){
                if (currentTrack < trackList.size()-1) {
                    currentTrack++;
                    intentService.putExtra(TrackListHelper.TRACK, currentTrack);
                    intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                    startService(intentService);
                }
           //When presses previous button it go to the previous track.
        }else if (view.getId() == R.id.prevBtn){
                if(currentTrack != 0){
                     currentTrack--;
                    intentService.putExtra(TrackListHelper.TRACK, currentTrack);
                    intentService.putExtra(TrackListHelper.ACTION, TrackListHelper.PLAY_SONG);
                    startService(intentService);
                }

            }

        }

    }

