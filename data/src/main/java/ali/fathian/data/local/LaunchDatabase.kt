package ali.fathian.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LaunchEntity::class], version = 1)
abstract class LaunchDatabase: RoomDatabase() {

    abstract fun launchDao(): LaunchDao

}