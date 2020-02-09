package com.example.judgement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_startup.*

class StartupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        var players = Array(9) { it + 2}
        var rounds = Array(20) { it + 1}
        val pAA = ArrayAdapter(this, android.R.layout.simple_spinner_item, players)
        val rAA = ArrayAdapter(this, android.R.layout.simple_spinner_item, rounds)
        pSpinner.setAdapter(pAA)
        rSpinner.setAdapter(rAA)

        editTexts = mutableListOf<EditText>()
        pSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var tempEditTexts = editTexts   //local var to change and reassign
                for (i in playerNames.childCount until players[position]) {   //add if needed
                    var tempEditText = EditText(this@StartupActivity)
                    tempEditText.hint = resources.getString(R.string.player)+ " " + (i + 1)
                    playerNames.addView(tempEditText)
                    tempEditTexts.add(tempEditText)
//                    Toast.makeText(this@StartupActivity, " " + playerList.childCount + " " + players[position] + " " + i, Toast.LENGTH_SHORT).show()
                }
                for (i in (playerNames.childCount - 1) downTo players[position]) { //remove if needed
                    playerNames.removeView(tempEditTexts.get(i))
                    tempEditTexts.removeAt(i)
                }
                editTexts = tempEditTexts
            }
            /**
             * Required abstract
             */
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        startButton.setOnClickListener {        //launch new activity
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("p", pSpinner.getSelectedItem().toString().toInt())    //send info to new activity
            i.putExtra("r", rSpinner.getSelectedItem().toString().toInt())

            val tempEditText = editTexts
            var playerList = ArrayList<String>()
            for (element in tempEditText) {
                playerList.add(element.text.toString())
            }
            i.putStringArrayListExtra("pNames", playerList)

            startActivity(i)
        }
    }

    /**
     * Required Abstract Method (AdapterView.OnItemSelectedListener)
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    /**
     * Required Abstract Method (AdapterView.OnItemSelectedListener)
     */
    override fun onNothingSelected(arg0: AdapterView<*>) {
    }

    private lateinit var editTexts: MutableList<EditText>
}
