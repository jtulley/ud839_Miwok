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

class NumbersActivity : AppCompatActivity() {
    var itemAdapter: WordAdapter? = null

    init {
        Log.v("NumbersActivity", "in init")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_list)
        val words = ArrayList<WordContainer>()
        words.add(WordContainer("lutti", "one", R.raw.number_one, R.drawable.number_one))
        words.add(WordContainer("otiiko", "two", R.raw.number_two, R.drawable.number_two))
        words.add(WordContainer("tolookosu", "three", R.raw.number_three, R.drawable.number_three))
        words.add(WordContainer("oyyisa", "four", R.raw.number_four, R.drawable.number_four))
        words.add(WordContainer("massokka", "five", R.raw.number_five, R.drawable.number_five))
        words.add(WordContainer("temmokka", "six", R.raw.number_six, R.drawable.number_six))
        words.add(WordContainer("kenekaku", "seven", R.raw.number_seven, R.drawable.number_seven))
        words.add(WordContainer("kawinta", "eight", R.raw.number_eight, R.drawable.number_eight))
        words.add(WordContainer("wo'e", "nine", R.raw.number_nine, R.drawable.number_nine))
        words.add(WordContainer("na'aacha", "ten", R.raw.number_ten, R.drawable.number_ten))
        Log.v("NumbersActivity", "about to create the itemAdapter ")

        val mp = MediaPlayer()
        val itemAdapter = WordAdapter(this, words, R.color.category_numbers, mp)
        val listView = findViewById<ListView>(R.id.word_list)
        listView.adapter = itemAdapter
    }

    override fun onStop() {
        super.onStop()
        Log.v("NumbersActivity", "onStop")
        itemAdapter?.stopAndResetMediaPlayer()
    }
}
