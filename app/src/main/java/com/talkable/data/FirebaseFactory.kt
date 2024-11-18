package com.talkable.data

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.talkable.data.FirebaseKey.TALK_FEEDBACK


object FirebaseFactory {
    private val db = Firebase.database
    private val ref = db.getReference("0")
    val feedbackRef = ref.child(TALK_FEEDBACK)
}

object FirebaseKey {
    const val TALK_FEEDBACK = "talk_feedback"
}