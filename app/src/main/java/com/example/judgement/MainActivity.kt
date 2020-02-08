package com.example.judgement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val SUIT_IMAGE: Array<Int> = arrayOf(R.drawable.spade, R.drawable.diamond, R.drawable.club, R.drawable.heart, R.drawable.no_suit)
    private val SUIT_IMAGE_GRAY: Array<Int> = arrayOf(R.drawable.spade_gray, R.drawable.diamond_gray, R.drawable.club_gray, R.drawable.heart_gray, R.drawable.no_suit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var players = intent.getIntExtra("p", 1)
        var rounds = intent.getIntExtra("r", 1)

        gameNum = 1
        roundNum = 1
        handsLeftNum = rounds
        cardsNum = rounds

        SUIT_VIEW = arrayOf(imageView, imageView2, imageView3, imageView4, imageView5)
        suitIndex = 0

        gameVal.setText(gameNum.toString())
        roundVal.setText(roundNum.toString())
        handsLeftVal.setText(handsLeftNum.toString())
        cardsVal.setText(cardsNum.toString())

        nextRoundButton.setOnClickListener {
            var tempIdx = suitIndex     //declare local for multithreading workaround
            tempIdx = (tempIdx!! + 1) % SUIT_IMAGE.size     //can be null, so !! to allow nullpointerexcep
            updateSuits(tempIdx)        //update as needed
            suitIndex = tempIdx         //update instance var

            var tempCardsNum = cardsNum     //update cards to hand out
            tempCardsNum = tempCardsNum!! - 1
            cardsVal.setText(tempCardsNum.toString())
            cardsNum = tempCardsNum

            handsLeftVal.setText(tempCardsNum.toString())   //update available hands
            handsLeftNum = tempCardsNum
        }
    }

    /**
     * Updates trump suite order
     * @param idx The current trump
     */
    private fun updateSuits(idx: Int) {
        var tempIdx = idx
        for (imageView in SUIT_VIEW!!) {  //set all to gray
            imageView.setImageResource(SUIT_IMAGE_GRAY[tempIdx])
            tempIdx = (tempIdx + 1) % SUIT_IMAGE.size
        }
        SUIT_VIEW!![0].setImageResource(SUIT_IMAGE[idx])  //set actual color for first
    }

    private var SUIT_VIEW: Array<ImageView>? = null
    private var suitIndex: Int? = null
    private var gameNum: Int? = null
    private var roundNum: Int? = null
    private var handsLeftNum: Int? = null
    private var cardsNum: Int? = null
}
