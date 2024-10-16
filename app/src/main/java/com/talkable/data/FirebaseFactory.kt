package com.talkable.data

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.talkable.data.FirebaseKey.TALK_FEEDBACK


object FirebaseFactory {
    private val db = Firebase.database
    val feedbackRef = db.getReference(TALK_FEEDBACK)
}

object FirebaseKey {
    const val TALK_FEEDBACK = "talk_feedback"
}