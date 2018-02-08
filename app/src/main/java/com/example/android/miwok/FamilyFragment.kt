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
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import java.util.ArrayList

class FamilyFragment : Fragment() {
    var itemAdapter: WordAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.word_list, container, false)

        val words = ArrayList<WordContainer>()
        words.add(WordContainer("әpә", "father", R.raw.family_father, R.drawable.family_father))
        words.add(WordContainer("әṭa", "mother", R.raw.family_mother, R.drawable.family_mother))
        words.add(WordContainer("angsi", "son", R.raw.family_son, R.drawable.family_son))
        words.add(WordContainer("tune", "daughter", R.raw.family_daughter, R.drawable.family_daughter))
        words.add(WordContainer("taachi", "older brother", R.raw.family_older_brother, R.drawable.family_older_brother))
        words.add(WordContainer("chalitti", "younger brother", R.raw.family_younger_brother, R.drawable.family_younger_brother))
        words.add(WordContainer("teṭe", "older sister", R.raw.family_older_sister, R.drawable.family_older_sister))
        words.add(WordContainer("kolliti", "younger sister", R.raw.family_younger_sister, R.drawable.family_younger_sister))
        words.add(WordContainer("ama", "grandmother", R.raw.family_grandmother, R.drawable.family_grandmother))
        words.add(WordContainer("paapa", "grandfather", R.raw.family_grandfather, R.drawable.family_grandfather))

        val mp = MediaPlayer()
        context?.let { context ->
            val itemAdapter = WordAdapter(context, words, R.color.category_family, mp)
            val listView = rootView?.findViewById<ListView>(R.id.word_list)
            listView?.adapter = itemAdapter
        }
        return rootView
    }

    override fun onStop() {
        super.onStop()
        Log.v("FamilyFragment", "onStop")
        itemAdapter?.stopAndResetMediaPlayer()
    }
}
