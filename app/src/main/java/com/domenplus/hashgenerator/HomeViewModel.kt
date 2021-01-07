package com.domenplus.hashgenerator

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel: ViewModel() {
    fun getHash(plainText:String,algorithm:String):String{
        val byte=MessageDigest.getInstance(algorithm).digest(plainText.toByteArray())
        return toHex(byte)
    }
    fun toHex(byteArray: ByteArray):String{
        Log.d("VViewModel",byteArray.joinToString("") { "%02x".format(it) })
        return byteArray.joinToString("") { "%02x".format(it) }
    }
}