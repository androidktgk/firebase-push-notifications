package com.example.explorefirebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Firebase.database.reference

        //Firebase Messaging Block
        FirebaseMessaging.getInstance().subscribeToTopic("Notification")
        //Firebase Messaging End Block

        //read data changes
        database.child("auth_users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<HashMap<String,HashMap<String,Any>>>()
                Log.d("DATA_RECIEVED", "Value is: " + user?.get("user3")?.get("name"))
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("FAILURE", "Failed to read value.", error.toException())
            }
        })

        findViewById<Button>(R.id.btnSetData).setOnClickListener{
            Firebase.auth.signOut()
        }
    }

}
