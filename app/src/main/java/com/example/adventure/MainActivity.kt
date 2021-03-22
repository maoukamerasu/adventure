package com.example.adventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import kotlinx.android.synthetic.*

class MainActivity : AppCompatActivity() {
    var step=200
    var mode =1//1:歩行 2:戦闘
    var encounter=10//エーカウント
    lateinit var text:String
    var enemyStatus= mutableMapOf<String,Int?>("maxHp" to 10,"hitPoint" to 10,"strength" to 12,"weapon" to 3,"vitality" to 2,"armour" to 0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var message= findViewById<TextView>(R.id.message)
        var count =findViewById<TextView>(R.id.count)
        var button1=findViewById<Button>(R.id.button1)
        var button2=findViewById<Button>(R.id.button2)
        var button3=findViewById<Button>(R.id.button3)
        var button4=findViewById<Button>(R.id.button4)
        var image=findViewById<ImageView>(R.id.imageView)
        var enemyGraphic=findViewById<ImageView>(R.id.imageView4)
        var scrollView=findViewById<ScrollView>(R.id.scrollview)
        var showHp=findViewById<TextView>(R.id.showhp)
        var skill=arrayOf("防御","捨て身","ヒール")
        var status=mutableMapOf<String,Int?>("maxHp" to 20,"hitPoint" to 20,"strength" to 18,"weapon" to 5,"vitality" to 18,"armour" to 3)
        var items=arrayOf(mutableMapOf("name" to "薬草","quantity" to 0,"type" to 1,"damage" to 20),
            mapOf("name" to "こん棒","quantity" to 0,"type" to 4,"damage" to 3),
            mapOf("name" to "毛皮の服","quantity" to 0,"type" to 5,"damage" to 2))
        text="アルスは森の中に彷徨ってる"
        showHp.setText("HP:"+status["hitPoint"]+"/"+status["maxHp"])
        message.setText(text)
        count.setText(step.toString())
        button1.setOnClickListener{
            if(mode==1){
                Advance(message,count,scrollView)
                ChangeMode(mode,button1,button2,button3,message,scrollView,enemyGraphic)
            }
            else if(button1.text.equals("攻撃")){
                enemyStatus["hitPoint"] = enemyStatus["hitPoint"]?.minus(5)
                text+="\nアルスの攻撃\nコボルドに5のダメージ\n"
                message.setText(text)
                scrollView.fullScroll(View.FOCUS_DOWN)
                if(enemyStatus["hitPoint"]!! <=0){
                    text+="\nコボルドを殺した"
                    message.setText(text)
                    scrollView.fullScroll(View.FOCUS_DOWN)
                    mode=1
                    encounter=5+(0..10).random()//エンカウントリセット 5~15
                    ChangeMode(mode,button1,button2,button3,message,scrollView,enemyGraphic)
                }else {
                    mode = 3
                    ChangeMode(mode, button1, button2, button3, message, scrollView, enemyGraphic)
                }
            }
            else if(button1.text.equals("続き")){
                text+="\nコボルドの攻撃1\nアルスに3のダメージ"
                status["hitPoint"] = status["hitPoint"]?.minus(3)
                showHp.setText("HP:"+status["hitPoint"]+"/"+status["maxHp"])
                message.setText(text)
                scrollView.fullScroll(View.FOCUS_DOWN)
                mode = 2
                ChangeMode(mode, button1, button2, button3, message, scrollView, enemyGraphic)
            }
        }
        button2.setOnClickListener{
            if(mode==1){
                Return(message,count,scrollView)
            }
            if(mode==2){
                AlertDialog.Builder(this)
                    .setTitle("スキル")
                    .setSingleChoiceItems(skill,0, { dialog, which ->})
                    .setPositiveButton("選択",{dialog,which-> })
                    .show()

            }
        }
    }

    fun Advance(message: TextView,count:TextView,scrollView: ScrollView) {
        step-=1
        text+="\nアルスは前に進んだ"
        message.setText(text)
        count.setText(step.toString())
        encounter-=1
        if(encounter<=0) {
            mode = 2
            text = "コボルドが現れた！"
            message.setText(text)
            enemyStatus["hitPoint"]=10
        }
        scrollView.fullScroll(View.FOCUS_DOWN)
    }
    private fun Return(message: TextView,count: TextView,scrollView: ScrollView){
        if(step<200) {
            step += 1
            text+="\nアルスは後ろに戻った"
            message.setText(text)
            scrollView.fullScroll(View.FOCUS_DOWN)
        }else{
            text+="\nこれ以上戻れない"
            message.setText(text)
            scrollView.fullScroll(View.FOCUS_DOWN);
        }
        count.setText(step.toString())
    }
    private fun ChangeMode(//
        mode:Int,
        button1: Button,
        button2: Button,
        button3: Button,
        message: TextView,
        scrollView: ScrollView,
        enemyGraphic:ImageView
    ){
        if(mode==2) {
            button1.setText("攻撃")
            button2.setText("スキル")
            button3.setText("道具")
            scrollView.fullScroll(View.FOCUS_DOWN);
            enemyGraphic.setImageResource(R.drawable.kobold)
        }
        else if(mode==1){
            button1.setText("進む")
            button2.setText("戻る")
            button3.setText(null)
            enemyGraphic.setImageDrawable(null)
        }
        else if(mode==3){
            button1.setText("続き")
            button2.setText(null)
            button3.setText(null)
        }
    }
}



