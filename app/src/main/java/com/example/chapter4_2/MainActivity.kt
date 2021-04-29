package com.example.chapter4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val tag = "High and Low" //タグはログ出力で使用

    //自分と相手のカードの目を保存する変数
    private var yourCard = 0
    private var droidCard = 0

    private var gameStarted = false //ゲームを開始したかどうか
    private var answered = false //出された問題に答えたかどうか

    private var money = 1000 //最初に持っているお金
    private var bestScore by Delegates.notNull<Int>() //ベストスコアの初期値は0円

    override fun onCreate(savedInstanceState: Bundle?) {
        //アクティビティの生成時に一度だけ呼出される。初期化処理を行う
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moneyText.text = "${money}円"

        highBtn.setOnClickListener {
            if ((gameStarted && !answered)) {
                //ゲーム開始状態かつ未回答であればhighAndLowメソッドを実行する
                highAndLow('H')
            }
        }
        lowBtn.setOnClickListener {
            if ((gameStarted && !answered)) {
                highAndLow('L')
            }
        }

        nextBtn.setOnClickListener {
            //「次へ」が押されたら新しいカードをめくる
            if (gameStarted) {
                drawCard()
            }
        }

        //「終わる」ボタンが押されたら本当に終わるかもう一度聞く
        //それでも終わるときは現在の所持金を表示してアプリを終了する
        finishBtn.setOnClickListener {
            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                    .setMessage("本当に終了しますか？")
                    .setPositiveButton("はい") { dialog, which ->
                        AlertDialog.Builder(this)
                                .setMessage("今回の結果は${money}円でした")
                                .setPositiveButton("OK") { _, _ ->
                                    checkScore(money) //ゲーム終了時に最高記録チェック関数を呼出す
                                }
                                .show()
                    }
                    .setNegativeButton("いいえ") { dialog, which ->
                    //「いいえ」ならばゲームを続ける
                    }
                    .show()
        }
    }

    override fun onResume() {
        //ユーザーの処理を受け付ける直前に呼出される(=ゲーム開始時にすること)
        super.onResume()
        gameStarted = true
        drawCard()

        //ゲーム開始時にこれまでの最高記録を取得して画面に表示する
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        this.bestScore = pref.getInt("bestScore", 0)
        bestScoreText.text = "ここまでの億万長者：${bestScore} 円"
    }

    private fun drawCard(){
        //自分も相手も裏向きのカードを表示する
        yourCardImage.setImageResource(R.drawable.z02)
        droidCardImage.setImageResource(R.drawable.z01)

        yourCard = (1..13).random() //乱数生成
        Log.d(tag, "You:"+yourCard)

        when (yourCard) {
            //yourCardに保存された数によって表示するカードが決まる
            1 -> yourCardImage.setImageResource(R.drawable.d01)
            2 -> yourCardImage.setImageResource(R.drawable.d02)
            3 -> yourCardImage.setImageResource(R.drawable.d03)
            4 -> yourCardImage.setImageResource(R.drawable.d04)
            5 -> yourCardImage.setImageResource(R.drawable.d05)
            6 -> yourCardImage.setImageResource(R.drawable.d06)
            7 -> yourCardImage.setImageResource(R.drawable.d07)
            8 -> yourCardImage.setImageResource(R.drawable.d08)
            9 -> yourCardImage.setImageResource(R.drawable.d09)
            10 -> yourCardImage.setImageResource(R.drawable.d10)
            11 -> yourCardImage.setImageResource(R.drawable.d11)
            12 -> yourCardImage.setImageResource(R.drawable.d12)
            13 -> yourCardImage.setImageResource(R.drawable.d13)
        }

        droidCard = (1..13).random() //乱数生成
        Log.d(tag, "droid:"+droidCard)

        answered = false
        //自分のカードが決まった時点ではHIGH/LOWは未回答
    }

    private fun highAndLow(answer: Char) {
        //相手のカードの目を確認する
        showDroidCard()
        answered = true

        //自分と相手のカードの目の差によって勝敗を決定する
        val balance = droidCard - yourCard

        if (balance == 0) {
            //自分のカードと相手のカードの目が同じとき何もしない
        } else if (balance > 0 && answer == 'H') {
            if (money >= 1000) {
                //お金が1000円以上なら正解するたびにお金が2倍になる
                money *= 2
                moneyText.text = "${money}円"
            } else {
                //お金が1000円以下なら正解でも1円ずつしか増えない～～・・・
                money++
                moneyText.text = "${money}円"
            }
        } else if (balance < 0 && answer == 'L') {
            if (money >= 1000) {
                money *= 2
                moneyText.text = "${money}円"
            } else {
                money++
                moneyText.text = "${money}円"
            }
        } else {
                //一度でも間違えるとお金は０になる・・・
                money = 0
                moneyText.text = "${money}円"
            }
    }

    //裏向きのままの相手のカードをめくる関数
    private fun showDroidCard(){
        when (droidCard) {
            //droidCardに保存された数によって表示するカードが決まる
            1 -> droidCardImage.setImageResource(R.drawable.c01)
            2 -> droidCardImage.setImageResource(R.drawable.c02)
            3 -> droidCardImage.setImageResource(R.drawable.c03)
            4 -> droidCardImage.setImageResource(R.drawable.c04)
            5 -> droidCardImage.setImageResource(R.drawable.c05)
            6 -> droidCardImage.setImageResource(R.drawable.c06)
            7 -> droidCardImage.setImageResource(R.drawable.c07)
            8 -> droidCardImage.setImageResource(R.drawable.c08)
            9 -> droidCardImage.setImageResource(R.drawable.c09)
            10 -> droidCardImage.setImageResource(R.drawable.c10)
            11 -> droidCardImage.setImageResource(R.drawable.c11)
            12 -> droidCardImage.setImageResource(R.drawable.c12)
            13 -> droidCardImage.setImageResource(R.drawable.c13)
        }
    }

    //ゲームの最高記録をチェックする関数
    private fun checkScore(money: Int){
        if (money > bestScore) {
            //今回の記録が今までの最高記録を上回ったら、最高記録のデータを上書きする
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = pref.edit()
            editor.putInt("bestScore", money)
                    .apply() //非同期でデータを書き込み

            AlertDialog.Builder(this)
                    .setMessage("最高記録を更新しました！${money}円です！")
                    .setPositiveButton("OK"){ _, _ ->
                        finish()
                    }
                    .show()
        } else {
            //最高記録に変化がなければアプリを終了する
            finish()
        }
    }
}