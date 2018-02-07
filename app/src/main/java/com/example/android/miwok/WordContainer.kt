package com.example.android.miwok

/**
 * Created by jefftulley on 2/2/18.
 */

data class WordContainer (val miwokTranslation: String, val defaultTranslation: String, val sound: Int?, val image: Int?) {
    constructor(miwokTranslation: String, defaultTranslation: String, sound: Int?): this(miwokTranslation, defaultTranslation, sound, null)
}
