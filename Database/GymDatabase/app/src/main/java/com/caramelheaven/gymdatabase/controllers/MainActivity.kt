package com.caramelheaven.gymdatabase.controllers

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

import com.caramelheaven.gymdatabase.R
import com.caramelheaven.gymdatabase.controllers.clients.ClientsFragment
import com.caramelheaven.gymdatabase.controllers.group.GroupFragment
import com.caramelheaven.gymdatabase.controllers.individuals.IndividualsFragment
import com.caramelheaven.gymdatabase.controllers.login.LoginActivity
import com.caramelheaven.gymdatabase.controllers.trainers.TrainersFragment
import com.caramelheaven.gymdatabase.datasourse.model.ClientDirectory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.IndexOutOfBoundsException

import java.util.ArrayList
import java.util.HashMap

class MainActivity : BaseActivity() {

    private var drawerLayout: DrawerLayout? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference = getSharedPreferences("GYM", Context.MODE_PRIVATE)
        val login = sharedPreference.getString("login", null)

        if (login.equals("admin") || login.equals("user")) {
            //nothing
        } else {
            Toast.makeText(baseContext, "This is person is not exists now", Toast.LENGTH_SHORT).show()
            finish()
        }

        val list = ArrayList<String>()
        val listView = findViewById<ListView>(R.id.listView)

        drawerLayout = findViewById(R.id.drawerLayout)
        list.add("Клиенты")
        list.add("Тренера")
        list.add("Индивидуальные занятия")
        list.add("Групповые занятия")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        listView.adapter = adapter

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ClientsFragment.newInstance())
                .commit();

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, ClientsFragment.newInstance())
                        .commit();
                1 -> supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, TrainersFragment.newInstance())
                        .commit();
                2 -> supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, IndividualsFragment.newInstance())
                        .commit();
                3 -> supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, GroupFragment.newInstance())
                        .commit();
            }
        }
    }
}