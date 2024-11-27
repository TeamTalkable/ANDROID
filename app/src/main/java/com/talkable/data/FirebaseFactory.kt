package com.talkable.data

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.talkable.data.FirebaseKey.TALK_FEEDBACK
import com.talkable.data.FirebaseKey.TALK_SAVED


object FirebaseFactory {
    private val db = Firebase.database
    private val ref = db.getReference("0")
    val feedbackRef = ref.child(TALK_FEEDBACK)
    val savedRef = ref.child(TALK_SAVED)
}

object FirebaseKey {
    const val TALK_FEEDBACK = "talk_feedback"
    const val TALK_SAVED = "talk_saved"
}