package com.george.pitch_estimator.scores

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Score(
    @Id var id: Long = 0,
    var text: String? = null,
    var name:String?=null,
    var time: Long = 0
)