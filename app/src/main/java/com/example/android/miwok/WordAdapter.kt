package com.example.android.miwok

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import java.io.IOException
import java.util.ArrayList

/**
 * Created by jefftulley on 2/2/18.
 */

class WordAdapter(context: Context, words: ArrayList<WordContainer>, private val backgroundColor: Int, var mediaPlayer: MediaPlayer) : ArrayAdapter<WordContainer>(context, 0, words) {
    internal var mediaPlayerPaused = false
    internal var mediaFocusDelayed = false
    internal val mediaPlayerLock: Any
    internal val am: AudioManager = getContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
    internal val afChangeListener: AudioManager.OnAudioFocusChangeListener
    internal var audioFocusRequest: AudioFocusRequest? = null

    init {

        mediaPlayerLock = Any()

        afChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            synchronized(mediaPlayerLock) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    Log.v("WordAdapter", "Focus lost(transient)")
                    if (mediaPlayer.isPlaying) {
                        Log.v("WordAdapter", "Pausing player")
                        mediaPlayer.pause()
                        mediaPlayerPaused = true
                    }
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    Log.v("WordAdapter", "Focus lost")
                    if (mediaPlayer.isPlaying) {
                        Log.v("WordAdapter", "Stopping player")
                        mediaPlayer.stop()
                    }
                    mediaPlayer.reset()
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    Log.v("WordAdapter", "can duck")
                    if (mediaPlayer.isPlaying) {
                        Log.v("WordAdapter", "Pausing player")
                        mediaPlayer.pause()
                        mediaPlayerPaused = true
                    }
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    Log.v("WordAdapter", "focus gain")
                    if (mediaPlayerPaused || mediaFocusDelayed) {
                        mediaFocusDelayed = false
                        mediaPlayerPaused = mediaFocusDelayed
                        Log.v("WordAdapter", "Starting player")
                        mediaPlayer.start()
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    .setOnAudioFocusChangeListener(afChangeListener)
                    .setWillPauseWhenDucked(true)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .build()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.word_layout, parent, false)
            listItemView!!.setBackgroundResource(backgroundColor)
        }

        val wordContainer = getItem(position)


        val theImage = listItemView.findViewById<ImageView>(R.id.miwok_image)
        if (wordContainer!!.image != null) {
            theImage.setImageResource(wordContainer.image!!)
        } else {
            theImage.visibility = View.GONE
        }

        val miwokTextView = listItemView.findViewById<TextView>(R.id.miwok_word)
        miwokTextView.text = wordContainer.miwokTranslation



        listItemView.setOnClickListener {
            try {
                synchronized(mediaPlayerLock) {

                    try {
                        if (mediaPlayer.isPlaying) {
                            Log.v("WordAdapter", "Found the player playing. Stopping.")
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                                am.abandonAudioFocus(afChangeListener)
                            } else if (audioFocusRequest != null) {
                                am.abandonAudioFocusRequest(audioFocusRequest!!)
                            }
                            mediaPlayer.stop()
                        }

                        Log.v("WordAdapter", "Resetting the player player.")
                        mediaPlayer.reset()

                    } catch (e: java.lang.IllegalStateException) {
                        // ignore
                        Log.v("WordAdapter", "Ignored an illegal state exception.")
                    }

                    try {
                        Log.v("WordAdapter", "Setting datasource.")
                        mediaPlayer.setDataSource(context, Uri.parse("android.resource://com.example.android.miwok/" + wordContainer.sound!!))
                        Log.v("WordAdapter", "preparing")
                        mediaPlayer.prepare()
                    } catch (e: java.lang.IllegalStateException) {
                        Log.v("WordAdapter", "Found the player already set up.  Resetting")
                        // already prepared
                        mediaPlayer.reset()
                        Log.v("WordAdapter", "Setting datasource.")
                        mediaPlayer.setDataSource(context, Uri.parse("android.resource://com.example.android.miwok/" + wordContainer.sound!!))
                        Log.v("WordAdapter", "preparing")
                        mediaPlayer.prepare()
                    }

                    Log.v("WordAdapter", "Setting the volume.")
                    mediaPlayer.setVolume(1.0f, 1.0f)
                }

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    requestAudioFocus()
                } else {
                    mediaFocusDelayed = requestAudioFocusOreoPlus()
                }
            } catch (e: IOException) {
                Log.e("WordAdapter", "Error loading resource file", e)
                e.printStackTrace()
            }
        }

        val englishTextView = listItemView.findViewById<TextView>(R.id.english_word)
        englishTextView.text = wordContainer.defaultTranslation

        return listItemView
    }

    private fun requestAudioFocus(): Boolean {
        val focusRequestResult = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
        Log.v("WordAdapter", "Setting the onCompletionLIstener.")
        mediaPlayer.setOnCompletionListener {
            synchronized(mediaPlayerLock) {
                am.abandonAudioFocus(afChangeListener)
                Log.v("WordAdapter", "Resetting the media player in onCompletionLIstener(1).")
                mediaPlayer.reset()
            }
        }
        return handleFocusResult(focusRequestResult)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestAudioFocusOreoPlus(): Boolean {
        val focusRequestResult: Int

        focusRequestResult = am.requestAudioFocus(audioFocusRequest!!)
        Log.v("WordAdapter", "Setting the onCompletionLIstener(2).")
        mediaPlayer.setOnCompletionListener {
            synchronized(mediaPlayerLock) {
                Log.v("WordAdapter", "music completed. Abandoning focus(2)")
                am.abandonAudioFocusRequest(audioFocusRequest!!)
                Log.v("WordAdapter", "music completed. resetting player")
                mediaPlayer.reset()
            }
        }

        return handleFocusResult(focusRequestResult)
    }

    private fun handleFocusResult(focusRequestResult: Int): Boolean {
        if (focusRequestResult == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
            Log.w("WordAdapter", "GOT DELAYED")
            return true
        } else if (focusRequestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            synchronized(mediaPlayerLock) {
                Log.v("WordAdapter", "REQUEST GRANTED.  Starting player")
                mediaPlayer.start()
            }
        } else if (focusRequestResult == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            Log.e("WordAdapter", "REQUEST FAILEDh")
            Log.e("WordAdapter", "Could not start audio file")
        }
        return false
    }

    fun stopAndResetMediaPlayer() {
        synchronized(mediaPlayerLock) {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
            }
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer()
        }
    }

}
