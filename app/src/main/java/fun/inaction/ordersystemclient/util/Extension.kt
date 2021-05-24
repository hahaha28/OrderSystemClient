package `fun`.inaction.ordersystemclient.util

import android.app.Activity
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Base64
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.util.regex.Pattern

fun Parcelable.toBase64String():String{
    val parcel = Parcel.obtain()
    writeToParcel(parcel,0)
    val bytes = parcel.marshall()
    parcel.recycle()
    return Base64.encodeToString(bytes,Base64.DEFAULT)
}

fun <T> decodeParcelString(str:String,creator:Parcelable.Creator<T>):T{
    val bytes = Base64.decode(str,Base64.DEFAULT)
    val parcel = Parcel.obtain()
    parcel.unmarshall(bytes,0,bytes.size)
    parcel.setDataPosition(0)
    return creator.createFromParcel(parcel)
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun String.isPhoneNumber():Boolean{
    val regular = "\\d{11}"
    return Pattern.compile(regular).matcher(this).matches()
}

fun Fragment.appCompatActivity():AppCompatActivity{
    return requireActivity() as AppCompatActivity
}

fun ImageView.load(url:String){
    Glide.with(this)
        .load(url)
        .into(this)
}

fun Fragment.startActivity(targetActivity:Class<*>){
    val intent = Intent(this.activity,targetActivity)
    this.startActivity(intent)
}

fun Activity.startActivity(targetActivity:Class<*>){
    val intent = Intent(this,targetActivity)
    this.startActivity(intent)
}