package `fun`.inaction.ordersystemclient.network.service

import `fun`.inaction.ordersystemclient.network.bean.Result
import `fun`.inaction.ordersystemclient.network.bean.Suggestion
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SuggestionService {

    @FormUrlEncoded
    @POST("/suggestion/commit")
    fun commitSuggestion(@Field("suggestion")text:String):Call<Suggestion>

}