package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
            rollDice(R.id.imageView)
            rollDice(R.id.imageView2)
            val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            toast.show()
        }

        // Do a dice roll when the app starts
        rollDice(R.id.imageView)
        rollDice(R.id.imageView2)
    }

    private fun rollDice(imageView: Int) {
//        create new dice object with 6 side and use roll method from Dice class
        val dice = Dice(6)
        val diceRoll = dice.roll()

//      update the imageView with new number from dice
        val diceImage: ImageView = findViewById(imageView)
        val drawableResource = when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = diceRoll.toString()
    }
}

// dice class
class Dice(val numSides: Int) {

//    roll method
    fun roll(): Int {
        return (1..numSides).random()
    }
}