package com.example.kotlincoroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincoroutines.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class Person(
    val name: String = "",
    val age: Int = 1
)

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val tutorialDoc = Firebase.firestore.collection("coroutines").document("tutorial")
        val peter = Person("Peter", 25)
        GlobalScope.launch(Dispatchers.IO) {
            delay(3000L)
            tutorialDoc.set(peter).await()
            val person = tutorialDoc.get().await().toObject(Person::class.java)

            withContext(Dispatchers.Main){
                binding.tvData.text = person.toString()
            }
        }

    }



}