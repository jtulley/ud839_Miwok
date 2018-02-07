/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView

import java.util.ArrayList

class PhrasesActivity : AppCompatActivity() {
    var itemAdapter: WordAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_list)

        val words = ArrayList<WordContainer>()
        words.add(WordContainer("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going))
        words.add(WordContainer("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name))
        words.add(WordContainer("oyaaset...", "My name is...", R.raw.phrase_my_name_is))
        words.add(WordContainer("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling))
        words.add(WordContainer("kuchi achit", "I’m feeling good.", R.raw.phrase_im_feeling_good))
        words.add(WordContainer("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming))
        words.add(WordContainer("hәә’ әәnәm", "Yes, I’m coming.", R.raw.phrase_yes_im_coming))
        words.add(WordContainer("әәnәm", "I’m coming.", R.raw.phrase_im_coming))
        words.add(WordContainer("yoowutis", "Let’s go.", R.raw.phrase_lets_go))
        words.add(WordContainer("әnni'nem", "Come here.", R.raw.phrase_come_here))

        val mp = MediaPlayer()
        itemAdapter = WordAdapter(this, words, R.color.category_phrases, mp)
        val listView = findViewById<ListView>(R.id.word_list)
        listView.adapter = itemAdapter
    }

    override fun onStop() {
        super.onStop()
        Log.v("PhrasesActivity", "onStop")
        itemAdapter?.stopAndResetMediaPlayer()
    }
}
