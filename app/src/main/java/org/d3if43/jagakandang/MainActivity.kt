package org.d3if43.jagakandang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.d3if43.jagakandang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUI()


        binding.profileIcon.setOnClickListener{
            startActivity(Intent(this, InfoActivity::class.java))
        }


        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_manage
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI()
    }


    private fun updateUI() {
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            Glide.with(this).load(user.photoUrl).into(binding.profileIcon)
        } else {
            finishAffinity()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        // [END check_current_user]
    }


    fun createWorkRequest(message: String) {
        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(
                workDataOf(
                    "title" to "Reminder",
                    "message" to message,
                )
            )
            .build()

        Log.wtf("Notip", "$myWorkRequest")
        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }

}