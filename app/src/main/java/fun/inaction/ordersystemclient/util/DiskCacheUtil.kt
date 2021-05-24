package `fun`.inaction.ordersystemclient.util

import `fun`.inaction.ordersystemclient.MyApplication
import android.content.Intent
import android.os.Parcelable
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import java.io.*
import java.util.*

object DiskCacheUtil {

    private val rootPath: String = MyApplication.context.externalCacheDir.toString()

    public val gson:Gson = Gson()

    val mmkv: MMKV? = MMKV.mmkvWithID("cache", rootPath)

    fun clearAllCache(){
        mmkv?.clearAll()
    }

    fun write(key: String, data: String) =
        mmkv?.encode(key, data)

    fun <T> writeObj(key:String,obj:T){
        mmkv?.encode(key,gson.toJson(obj))
    }

    /**
     * 缓存 Parcelable 对象列表
     */
    fun writeParcelableList(key:String, data: List<Parcelable>){
        val temp = mutableListOf<String>()
        data.forEach {
            temp.add(it.toBase64String())
        }
        mmkv?.encode(key,gson.toJson(temp))
    }

    fun writeSerializableList(key:String,data:List<Serializable>){
        val temp = mutableListOf<String>()
        data.forEach {
            temp.add(it.toBase64Str())
        }
        mmkv?.encode(key,gson.toJson(temp))
    }

    fun write(key:String,obj:Serializable){
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(obj)
        val bytes = bos.toByteArray()
        mmkv?.encode(key,bytes)
        oos.close()
        bos.close()
    }


    fun getString(key: String): String? =
        mmkv?.getString(key, null)

    fun <T> getSerializableObj(key:String):T?{
        mmkv?.getBytes(key,null)?.let {
            val bais = ByteArrayInputStream(it)
            val ois = ObjectInputStream(bais)
            val obj = ois.readObject() as T
            ois.close()
            bais.close()
            return obj
        }
        return null
    }

    fun <T:Serializable> getSerializableList(key:String):List<T>?{
        getString(key)?.let {
            val result = mutableListOf<T>()
            val stringList = gson.fromJson<List<String>>(it, object : TypeToken<List<String>>(){}.type)
            stringList.forEach { str ->
                result.add(str.toSerializableObj())
            }
            return result
        }
        return null
    }

    fun  <T:Parcelable> getParcelableList(key: String,creator:Parcelable.Creator<T>):List<T>?{
        getString(key)?.let {
            val result = mutableListOf<T>()
            val stringList = gson.fromJson<List<String>>(it, object : TypeToken<List<String>>(){}.type)
            stringList.forEach { str ->
                result.add(decodeParcelString(str,creator))
            }
            return result
        }
        return null
    }

    inline fun <reified T> getObj(key:String):T?{
        getString(key)?.let{
            return gson.fromJson<T>(it, object : TypeToken<T>(){}.type)
        }
        return null
    }

    fun Serializable.toBytes():ByteArray{
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(this)
        val bytes = bos.toByteArray()
        oos.close()
        bos.close()
        return bytes
    }

    fun <T> ByteArray.toSerializableObj():T{
        val bais = ByteArrayInputStream(this)
        val ois = ObjectInputStream(bais)
        val obj = ois.readObject() as T
        ois.close()
        bais.close()
        return obj
    }

    fun Serializable.toBase64Str():String{
        return Base64.encodeToString(this.toBytes(),Base64.DEFAULT)
    }

    fun <T> String.toSerializableObj():T{
        return Base64.decode(this,Base64.DEFAULT).toSerializableObj<T>()
    }

}