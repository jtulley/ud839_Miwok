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

class ColorsActivity : AppCompatActivity() {
    var itemAdapter: WordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_list)

        val words = ArrayList<WordContainer>()

        words.add(WordContainer("weṭeṭṭi", "red", R.raw.color_red, R.drawable.color_red))
        words.add(WordContainer("chokokki", "green", R.raw.color_green, R.drawable.color_green))
        words.add(WordContainer("ṭakaakki", "brown", R.raw.color_brown, R.drawable.color_brown))
        words.add(WordContainer("ṭopoppi", "gray", R.raw.color_gray, R.drawable.color_gray))
        words.add(WordContainer("kululli", "black", R.raw.color_black, R.drawable.color_black))
        words.add(WordContainer("kelelli", "white", R.raw.color_white, R.drawable.color_white))
        words.add(WordContainer("ṭopiisә", "dusty yellow", R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow))
        words.add(WordContainer("chiwiiṭә", "mustard yellow", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow))

        val mp = MediaPlayer()
        val itemAdapter = WordAdapter(this, words, R.color.category_colors, mp)
        val listView = findViewById<ListView>(R.id.word_list)
        listView.adapter = itemAdapter
    }

    override fun onStop() {
        super.onStop()
        Log.v("ColorsActivity", "onStop")
        itemAdapter?.stopAndResetMediaPlayer()
    }
}
