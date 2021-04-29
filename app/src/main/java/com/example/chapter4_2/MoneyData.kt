@file:Suppress("DEPRECATION")

package com.example.chapter4_2

import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.S3ObjectSummary
import java.io.File

@Suppress("DEPRECATION")
class MoneyData: AppCompatActivity() {
    //AWSのデータベースに所持金の最高記録を保存したい

    val pref = PreferenceManager.getDefaultSharedPreferences(this)


    // 認証情報を用意
    /*var credentials: AWSCredentials = BasicAWSCredentials( // アクセスキー
            "AKI*************",  // シークレットキー
            "fd7**************************************"
    )

    // クライアントを生成
    var client: AmazonS3 = AmazonS3ClientBuilder
            .standard() // 認証情報を設定
            .withCredentials(AWSStaticCredentialsProvider(credentials)) // リージョンを AP_NORTHEAST_1(東京) に設定
            .withRegion(Regions.AP_NORTHEAST_1)
            .build()

    val objListing = client.listObjects("stsd-devblog") // バケット名を指定

    val file: File = File()*/
}