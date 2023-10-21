package ali.fathian.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunch(launchEntity: LaunchEntity)

    @Delete
    suspend fun deleteLaunch(launchEntity: LaunchEntity)

    @Query("SELECT * FROM launches")
    fun getAllLaunches(): Flow<List<LaunchEntity>>
}