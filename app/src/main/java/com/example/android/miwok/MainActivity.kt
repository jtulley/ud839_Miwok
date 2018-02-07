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

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("MainActivity", "onCreate")

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main)

        // Find the View that shows the family category
        val family = findViewById<View>(R.id.family) as TextView
        // The code in this method will be executed when the family category is clicked on.
        family.setOnClickListener({ startMiwokActivity(FamilyActivity::class.java) })

        // Find the View that shows the colors category
        val colors = findViewById<View>(R.id.colors) as TextView
        // Set a click listener on that View
        colors.setOnClickListener( { startMiwokActivity(ColorsActivity::class.java) })

        // Find the View that shows the phrases category
        val phrases = findViewById<View>(R.id.phrases) as TextView
        // Set a click listener on that View
        phrases.setOnClickListener( { startMiwokActivity(PhrasesActivity::class.java) })
    }

    fun startMiwokActivity(activityClass: Class<*>) {
        val theIntent = Intent(this@MainActivity, activityClass);
        startActivity(theIntent)
    }

    // this is done different than the other just to see another alternative
    // the reference to this is in activity_main.xml
    fun onNumbersList(view: View) {
        startMiwokActivity(NumbersActivity::class.java)
    }


    override fun onStop() {
        super.onStop()
        Log.v("MainActivity", "onStop")
    }
}
