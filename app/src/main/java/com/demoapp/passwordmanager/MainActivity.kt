package com.demoapp.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.commonsware.cwac.saferoom.SQLCipherUtils
import com.demoapp.passwordmanager.core.utils.DATABASE_NAME
import com.demoapp.passwordmanager.ui.auth.AuthenticationActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseState = SQLCipherUtils.getDatabaseState(applicationContext, DATABASE_NAME)
        if (databaseState == SQLCipherUtils.State.UNENCRYPTED) {
            SQLCipherUtils.encrypt(applicationContext, DATABASE_NAME, "key_passphrase".toCharArray())
        } else {
            Log.d(TAG, "database encryption status: ${databaseState.name}")
        }

        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "cek_MainActivity"
    }
}