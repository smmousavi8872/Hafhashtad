package com.github.smmousavi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.smmousavi.database.dao.ProductDao
import com.github.smmousavi.database.entity.ProductEntity
import com.github.smmousavi.database.entity.RatingEntity

@Database(entities = [ProductEntity::class, RatingEntity::class], version = 1, exportSchema = true)
internal abstract class HafashtadDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}