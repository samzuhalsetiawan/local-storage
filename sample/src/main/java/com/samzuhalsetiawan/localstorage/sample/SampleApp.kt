package com.samzuhalsetiawan.localstorage.sample

import android.app.Application
import com.samzuhalsetiawan.localstorage.LocalStorage

class SampleApp: Application() {

    val localStorage by lazy { LocalStorage(applicationContext) }

}