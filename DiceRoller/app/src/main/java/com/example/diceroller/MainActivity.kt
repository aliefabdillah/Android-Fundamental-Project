package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
* method to view the result on the screen
* */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        variable for button clicked
        val rollButton: Button = findViewById(R.id.button)

//        the method when button clicked
        rollButton.setOnClickListener {
            rollDice(R.id.textView)
            rollDice(R.id.textView2)
            val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

//    private fun rollDice2() {
//        val dice2= Dice(6)
//        val diceRoll2 = dice2.roll()
//        val resultTextView: TextView = findViewById(R.id.textView)
//        resultTextView.text = diceRoll2.toString()
//    }

    private fun rollDice(textView: Int) {
//        create new dice object with 6 side and use roll method from Dice class
        val dice = Dice(6)
        val diceRoll = dice.roll()

//      update the textView with new number from dice
        val resultTextView: TextView = findViewById(textView)
        resultTextView.text = diceRoll.toString()
    }
}

// dice class
class Dice(val numSides: Int) {

//    roll method
    fun roll(): Int {
        return (1..numSides).random()
    }
}