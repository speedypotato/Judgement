package com.example.judgement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
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

        var tempEditText = EditText(this)   // player name list
        tempEditText.setHint(R.string.player.toString() + " 1")
        playerList = mutableListOf(tempEditText)
//        pSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                var tempPlayerList = playerList
//                while (tempPlayerList!!.size > players[position]) {   //add if needed
//                    var temp = EditText(this@StartupActivity)
//                    temp.setHint(R.string.player.toString() + " " + tempPlayerList.size)
//                    tempPlayerList!!.add(temp)
//                }
//                while (tempPlayerList!!.size < players[position]) {   //remove if needed
//                    tempPlayerList!!.removeAt(tempPlayerList.size - 1)
//                }
//                playerList = tempPlayerList     //update new list
//                aa!!.notifyDataSetChanged()     //update view
//            }
//
//            /**
//             * Required abstract
//             */
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
        aa = ArrayAdapter<EditText>(this, android.R.layout.simple_list_item_1, playerList!!)
        playersListView.adapter = aa

        startButton.setOnClickListener {        //launch new activity
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("p", pSpinner.getSelectedItem().toString().toInt())    //send info to new activity
            i.putExtra("r", rSpinner.getSelectedItem().toString().toInt())
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

    private var playerList: MutableList<EditText>? = null
    private var aa: ArrayAdapter<EditText>? = null
}
