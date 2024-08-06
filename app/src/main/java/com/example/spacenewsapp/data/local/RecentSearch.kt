package com.example.spacenewsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class RecentSearch(
    val query: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    )
