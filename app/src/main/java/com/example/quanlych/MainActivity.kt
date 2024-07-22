package com.example.quanlych

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quanlych.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_cart, R.id.nav_slideshow, R.id.nav_login, R.id.nav_register
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Add a listener to change the toolbar visibility based on the destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_login, R.id.nav_register, R.id.nav_admin_home ,R.id.nav_admin_chart,R.id.nav_admin_user,R.id.nav_admin_product,R.id.nav_admin_order-> {
                    binding.appBarMain.toolbar.visibility = View.GONE
                }
                else -> {
                    binding.appBarMain.toolbar.visibility = View.VISIBLE
                }
            }
        }
        //
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fab: View = findViewById(R.id.fab)

            when (destination.id) {
                R.id.nav_admin_user -> fab.visibility = View.GONE
                else -> fab.visibility = View.VISIBLE
            }
        }
        // Handle navigation item clicks
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    // Navigate to login screen and close the drawer
                    navController.navigate(R.id.nav_login)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_logout -> {
                    // Clear user data from SharedPreferences
                    val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        clear()
                        apply()
                    }

                    // Navigate to login screen
                    navController.navigate(R.id.nav_login)

                    // Close the drawer
                    drawerLayout.closeDrawers()
                    true
                }
                else -> {
                    // Handle other menu items
                    navController.navigate(item.itemId)
                    drawerLayout.closeDrawers()
                    true
                }
            }
        }

        // Update Navigation Header with user info
        updateNavHeader()
    }

    public fun updateNavHeader() {
        val headerView = binding.navView.getHeaderView(0)
        val navUsername = headerView.findViewById<TextView>(R.id.nav_username)
        val navEmail = headerView.findViewById<TextView>(R.id.nav_email)

        val sharedPref = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        navUsername.text = sharedPref.getString("username", "Username")
        navEmail.text = sharedPref.getString("email", "email@example.com")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
