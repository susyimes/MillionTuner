package com.george.pitch_estimator.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.george.pitch_estimator.ObjectBox
import com.george.pitch_estimator.R
import com.george.pitch_estimator.scores.Score
import com.gyf.immersionbar.ImmersionBar
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_history.*
import java.text.SimpleDateFormat

class HistoryActivity : AppCompatActivity() {
    private val boxFor by lazy { ObjectBox.boxStore.boxFor<Score>() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        ImmersionBar.with(this).statusBarDarkFont(true).navigationBarColor("#ffffff")
            .navigationBarDarkIcon(true).init()


        history_List.layoutManager = LinearLayoutManager(this)
        history_List.adapter = SampleAdapter(this, boxFor.all)

        close.setOnClickListener {
            finish()
        }

    }

    class SampleAdapter(val context: Context, private val scores: List<Score>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ScoreViewHolder(
                LayoutInflater.from(context).inflate(R.layout.history_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            Log.e(",,,", scores.size.toString())
            return scores.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ScoreViewHolder) {
                holder.initData(scores[position])
            }

        }

        class ScoreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            private val mainText by lazy { view.findViewById<TextView>(R.id.mainText) }
            private val name by lazy { view.findViewById<TextView>(R.id.name) }
            private val time by lazy { view.findViewById<TextView>(R.id.time) }
            fun initData(score: Score) {
                mainText.text = score.text
                time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(score.time)
                name.text = score.name

            }
        }

    }
}
