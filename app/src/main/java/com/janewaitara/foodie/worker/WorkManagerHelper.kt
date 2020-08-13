package com.janewaitara.foodie.worker

import android.os.Build
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerHelper() {

    /** define constraints to prevent work from occurring when
    there is no network access or the device is low on battery.
    Add the constraints to the repeatingRequest definition.*/
    private fun buildConstraints(): Constraints{
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
    }

    private fun buildWorker (constraints: Constraints): PeriodicWorkRequest{
        return PeriodicWorkRequestBuilder<SynchronizeRecipeWorker>(
            6,// repeating interval
            TimeUnit.HOURS,
            15,// flex interval - worker will run some when within this period of time, but at the end of repeating interval
            TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
    }

    fun setUpSynchronization(){
        val constraints = buildConstraints()
        val synchronizationWorker = buildWorker(constraints)

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(
                SynchronizeRecipeWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                synchronizationWorker
            )
    }

    fun stopSynchronization(){
        WorkManager.getInstance().cancelUniqueWork(SynchronizeRecipeWorker.WORK_NAME)
    }

}