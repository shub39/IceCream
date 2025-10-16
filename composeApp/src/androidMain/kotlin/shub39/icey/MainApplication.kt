package shub39.icey

import android.app.Application
import org.koin.android.ext.koin.androidContext
import shub39.icey.di.initKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MainApplication)
        }
    }
}