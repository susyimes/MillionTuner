package com.george.pitch_estimator.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.george.pitch_estimator.ObjectBox
import com.george.pitch_estimator.R
import com.george.pitch_estimator.scores.Score
import com.george.pitch_estimator.utils.DisplayUtil
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
        val all = boxFor.all
        val sampleAdapter = SampleAdapter(this, all)
        history_List.adapter = sampleAdapter

        close.setOnClickListener {
            finish()
        }
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.adapterPosition
                    val toPos = target.adapterPosition
                    // move item in `fromPos` to `toPos` in adapter.
                    val removeAt = all.removeAt(viewHolder.adapterPosition)
                    all.add(toPos,removeAt)

                    sampleAdapter.notifyItemMoved(fromPos,toPos)
                    //not work
//                    boxFor.removeAll()
//                    boxFor.put(all)

                    return true// true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // remove from adapter
                    boxFor.remove(all[viewHolder.adapterPosition])
                    all.removeAt(viewHolder.adapterPosition)
                    sampleAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                }
            })

        mIth.attachToRecyclerView(history_List)

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
            holder.initData(scores[position],position)
        }

    }

    class ScoreViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val mainText by lazy { view.findViewById<TextView>(R.id.mainText) }
        private val name by lazy { view.findViewById<TextView>(R.id.name) }
        private val time by lazy { view.findViewById<TextView>(R.id.time) }
        private val root by lazy { view.findViewById< FrameLayout>(R.id.root) }
        fun initData(score: Score,position:Int) {
            if (position==0){
                val lp:  RecyclerView.LayoutParams =
                    root.layoutParams as RecyclerView.LayoutParams
                lp.topMargin=DisplayUtil.dip2px(view.context,10f)
            }
            mainText.text = score.text
            time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(score.time)
            name.text = score.name

        }
    }

}
