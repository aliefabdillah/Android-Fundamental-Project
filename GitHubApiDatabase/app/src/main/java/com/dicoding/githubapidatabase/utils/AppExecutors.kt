package com.dicoding.githubapidatabase.utils

import android.os.Looper
import java.util.concurrent.Executor
import android.os.Handler

class AppExecutors {
    val mainThread: Executor = MainThreadExecutor()

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}