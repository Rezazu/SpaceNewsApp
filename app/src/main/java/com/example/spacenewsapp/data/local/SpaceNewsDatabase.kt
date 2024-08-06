package com.example.spacenewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities =
    [RecentSearch::class],
    version = 1
)
abstract class SpaceNewsDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}