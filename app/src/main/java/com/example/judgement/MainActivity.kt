package com.example.judgement

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val SUIT_IMAGE: Array<Int> = arrayOf(R.drawable.spade, R.drawable.diamond, R.drawable.club, R.drawable.heart, R.drawable.no_suit)
    private val SUIT_IMAGE_GRAY: Array<Int> = arrayOf(R.drawable.spade_gray, R.drawable.diamond_gray, R.drawable.club_gray, R.drawable.heart_gray, R.drawable.no_suit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val players = intent.getIntExtra("p", 1)    //grab info from intent
        val rounds = intent.getIntExtra("r", 1)
        val playerNames = intent.getStringArrayListExtra("pNames")

        gameNum = 1
        roundNum = rounds
        handsLeftNum = rounds
        cardsNum = rounds

        SUIT_VIEW = arrayOf(imageView, imageView2, imageView3, imageView4, imageView5)
        suitIndex = 0

        gameVal.text = gameNum.toString()
        roundVal.text = roundNum.toString()
        handsLeftVal.text = handsLeftNum.toString()
        handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorLightGreen))
        cardsVal.text = cardsNum.toString()

        //labels
        var labelTr = TableRow(this)
        labelTr.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        var tvNameLabel = TextView(this)
        tvNameLabel.text = resources.getString(R.string.name)
        TextViewCompat.setTextAppearance(tvNameLabel, android.R.style.TextAppearance_DeviceDefault_Medium)
        tvNameLabel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

        var tvHandsLabel = TextView(this)
        tvHandsLabel.text = resources.getString(R.string.hands)
        TextViewCompat.setTextAppearance(tvHandsLabel, android.R.style.TextAppearance_DeviceDefault_Medium)
        tvHandsLabel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)

        var tvWinLoseLabel = TextView(this)
        tvWinLoseLabel.text = resources.getString(R.string.lose) + "/" + resources.getString(R.string.win)
        TextViewCompat.setTextAppearance(tvWinLoseLabel, android.R.style.TextAppearance_DeviceDefault_Medium)
        tvWinLoseLabel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.55f)

        var tvScoreLabel = TextView(this)
        tvScoreLabel.text = resources.getString(R.string.score)
        TextViewCompat.setTextAppearance(tvScoreLabel, android.R.style.TextAppearance_DeviceDefault_Medium)
        tvScoreLabel.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)
        tvScoreLabel.gravity = Gravity.RIGHT

        labelTr.addView(tvNameLabel)
        labelTr.addView(tvHandsLabel)
        labelTr.addView(tvWinLoseLabel)
        labelTr.addView(tvScoreLabel)
        playerList.addView(labelTr)

        handsButtons = mutableListOf()
        winSwitches = mutableListOf()
        scoreViews = mutableListOf()

        for (name in playerNames) {     //add player list
            var tr = TableRow(this)
            tr.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            var tvName = TextView(this)
            tvName.text = name
            TextViewCompat.setTextAppearance(tvName, android.R.style.TextAppearance_Large)
            tvName.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

            var hands = Button(this)
            hands.text = "0"
            hands.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)
            hands.setOnClickListener {  //increment hand bet
                var nextHand = (Integer.parseInt(hands.text.toString()) + 1) % (cardsNum + 1)
                hands.text = nextHand.toString()

                var tempHandsLeft = Integer.parseInt(handsLeftVal.text.toString()) - 1
                if (hands.text.toString() == ("0")) {
                    tempHandsLeft += cardsNum + 1
                }
                handsLeftVal.text = tempHandsLeft.toString()
                if (tempHandsLeft > 0) {    //update color
                    handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorLightGreen))
                } else if (tempHandsLeft == 0) {
                    handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorMustard))
                } else {
                    handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                }
            }
            hands.setOnLongClickListener {  //decrement hand bet with long hold
                var prevHand = Integer.parseInt(hands.text.toString()) - 1
                if (prevHand < 0)
                    prevHand = cardsNum
                hands.text = prevHand.toString()

                var tempHandsLeft = Integer.parseInt(handsLeftVal.text.toString()) + 1
                if (hands.text.toString() == cardsNum.toString()) {
                    tempHandsLeft -= (cardsNum + 1)
                }
                handsLeftVal.text = tempHandsLeft.toString()
                if (tempHandsLeft > 0) {    //update color
                    handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorLightGreen))
                } else if (tempHandsLeft == 0) {
                    handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorMustard))
                } else {
                    handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                }
                return@setOnLongClickListener true
            }

            var sw = Switch(this)
            sw.textOn = resources.getString(R.string.win)
            sw.textOff = resources.getString(R.string.lose)
            sw.showText = true
            sw.gravity = Gravity.LEFT
            sw.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.55f)

            var tvScore = TextView(this)
            tvScore.text = "0"
            TextViewCompat.setTextAppearance(tvScore, android.R.style.TextAppearance_Large)
            tvScore.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)
            tvScore.gravity = Gravity.CENTER
            //todo: set color

            tr.addView(tvName)
            tr.addView(hands)
            tr.addView(sw)
            tr.addView(tvScore)

            handsButtons.add(hands)
            winSwitches.add(sw)
            scoreViews.add(tvScore)

            playerList.addView(tr)
        }

        nextRoundButton.setOnClickListener {
            if (cardsNum == 0) {    //game has already ended, return to startup screen
                finish()
            } else if (handsLeftVal.text.toString().toInt() == 0) {
                AlertDialog.Builder(this)
                    .setTitle("Invalid Bets")
                    .setMessage("Last player must make at least 1 more or less bet.")
                    .setPositiveButton("Ok", null)
                    .show()
            } else {
                for (i in 0 until handsButtons.size) {  //add to score, update score color
                    if (winSwitches[i].isChecked) {
                        scoreViews[i].text =
                            (scoreViews[i].text.toString().toInt() + handsButtons[i].text.toString().toInt() + 1).toString()
                        scoreViews[i].setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.colorLightGreen
                            )
                        )
                    } else {
                        scoreViews[i].setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.colorAccent
                            )
                        )
                    }

                    //reset
                    handsButtons[i].text = "0"
                    winSwitches[i].isChecked = false
                }

                suitIndex = (suitIndex + 1) % SUIT_IMAGE.size         //update instance var
                updateSuits(suitIndex)        //update as needed

                cardsNum -= 1       //update cards to hand out
                cardsVal.text = cardsNum.toString()

                handsLeftVal.text = cardsNum.toString()    //update available hands
                handsLeftNum = cardsNum
                handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorLightGreen))

                if (cardsNum == 0) {    //end of game
                    var max = -1
                    var idx = -1
                    for ((index, elem) in scoreViews.withIndex()) {
                        if (elem.text.toString().toInt() > max) {
                            max = elem.text.toString().toInt()
                            idx = index
                        }
                    }
                    AlertDialog.Builder(this)
                        .setTitle("Game Finished")
                        .setMessage(playerNames[idx] + " won with " + max + " points!")
                        .setPositiveButton( //reset and increment game num
                            "Continue Game",
                            DialogInterface.OnClickListener { dialog, id ->
                                gameNum += 1
                                roundNum = rounds
                                handsLeftNum = rounds
                                cardsNum = rounds
                                gameVal.text = gameNum.toString()
                                roundVal.text = roundNum.toString()
                                handsLeftVal.text = handsLeftNum.toString()
                                handsLeftVal.setTextColor(ContextCompat.getColor(this, R.color.colorLightGreen))
                                cardsVal.text = cardsNum.toString()
                                suitIndex = 0         //update instance var
                                updateSuits(suitIndex)        //update as needed
                                for (i in 0 until handsButtons.size) {
                                    handsButtons[i].text = "0"
                                    winSwitches[i].isChecked = false
                                    scoreViews[i].text = "0"
                                    scoreViews[i].setTextColor(ContextCompat.getColor(
                                        this@MainActivity,
                                        android.R.color.black
                                    ))
                                }
                            })
                        .setNegativeButton( //return to score screen
                            "End",
                            DialogInterface.OnClickListener { dialog, id ->
                                nextRoundButton.text = resources.getText(R.string.return_home)
                        })
                        .show()
                }
            }
        }
    }

    override fun onBackPressed() {  //confirm back button exit
        AlertDialog.Builder(this)
            .setTitle("Closing Game")
            .setMessage("Are you sure you want to return to the start page?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                finish();
            })
            .setNegativeButton("No", null)
            .show()
    }

    /**
     * Updates trump suite order
     * @param idx The current trump
     */
    private fun updateSuits(idx: Int) {
        var tempIdx = idx
        for (imageView in SUIT_VIEW) {  //set all to gray
            imageView.setImageResource(SUIT_IMAGE_GRAY[tempIdx])
            tempIdx = (tempIdx + 1) % SUIT_IMAGE.size
        }
        SUIT_VIEW[0].setImageResource(SUIT_IMAGE[idx])  //set actual color for first
    }

    private lateinit var SUIT_VIEW: Array<ImageView>
    private var suitIndex: Int = 0
    private var gameNum: Int = 0
    private var roundNum: Int = 0
    private var handsLeftNum: Int = 0
    private var cardsNum: Int = 0
    private lateinit var handsButtons: MutableList<Button>
    private lateinit var winSwitches: MutableList<Switch>
    private lateinit var scoreViews: MutableList<TextView>
}
