package com.example.corngrain.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.corngrain.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navcController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navcController = Navigation.findNavController(this,R.id.navigation_host_container)
        bottom_navigation.setupWithNavController(navcController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navcController,null)
    }

}
