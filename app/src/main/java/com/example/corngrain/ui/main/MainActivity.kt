package com.example.corngrain.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.corngrain.R
import com.example.corngrain.data.bus.NoNetworkBus
import com.example.corngrain.data.fcm.NotificationRecevier
import com.example.corngrain.data.network.di.LanguageQuery
import com.example.corngrain.data.network.di.LanguageQueryImpl
import com.example.corngrain.utilities.EventBus
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val languageQuery by instance<LanguageQuery>()
    private lateinit var navController: NavController
    private lateinit var disposable: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settingNavigationWithButtonNavigationView()
        if (languageQuery.getAppLocale() == "ar-EG"){
            arabic_lang.visibility=View.INVISIBLE
            english_lang.visibility=View.VISIBLE
        }else{
            arabic_lang.visibility=View.VISIBLE
            english_lang.visibility=View.INVISIBLE

        }

        arabic_lang.setOnClickListener {
            languageQuery.setAppLocale("ar-EG")
            arabic_lang.visibility=View.INVISIBLE
            english_lang.visibility=View.VISIBLE
            bottom_navigation.findViewById<View>(R.id.movies_tab).performClick()
        }
        english_lang.setOnClickListener {
            languageQuery.setAppLocale("en")
            arabic_lang.visibility=View.VISIBLE
            english_lang.visibility=View.INVISIBLE

            bottom_navigation.findViewById<View>(R.id.movies_tab).performClick()
        }
        setDailyNotification()
        addingToken()
        try_again_btn.setOnClickListener {
            EventBus.post(NoNetworkBus())
        }


    }

    private fun addingToken() {

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("tokenFailed", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d("tokenGenerated", token)
            })
    }

    private fun settingNavigationWithButtonNavigationView() {
        navController = Navigation.findNavController(this, R.id.navigation_host_container)
        bottom_navigation.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun isOnline(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

    }


    override fun onResume() {
        super.onResume()
        disposable =
            EventBus.subscribe<NoNetworkBus>()
                // if you want to receive the event on main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isOnline()) {
                        main_views_group.visibility = View.VISIBLE
                        bottom_navigation.findViewById<View>(R.id.movies_tab).performClick()
                        no_networkView.visibility = View.INVISIBLE
                    } else {
                        Toast.makeText(
                            this,
                            "Sorry,But your connection to network is broken,try again when network is available",
                            Toast.LENGTH_SHORT
                        ).show()
                        main_views_group.visibility = View.INVISIBLE
                        no_networkView.visibility = View.VISIBLE
                    }
                }

    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()

    }

    private fun setDailyNotification() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 30)
        calendar.set(Calendar.SECOND, 0)
        setupAlarmManager(calendar)
    }

    private fun setupAlarmManager(calendar: Calendar) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentToReceiver = Intent(this, NotificationRecevier::class.java)
        intentToReceiver.putExtra("alarm", "open_alarm")
        val pendingIntent = PendingIntent.getBroadcast(this, 1000, intentToReceiver, 0)
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }


}
