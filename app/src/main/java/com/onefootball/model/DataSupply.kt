package com.onefootball.model

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Single
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .into(view)
}
class DataSupply
@Inject constructor(private val context: Context) {

    fun getNews(): Single<List<News>> {
        return Single.fromObservable {
            var inputStream: InputStream? = null
            try {
                inputStream = context.assets.open("news.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)

                inputStream.read(buffer)
                val jsonString = buffer.toString(Charset.defaultCharset())

                val newsList = Gson().fromJson<NewsList>(
                    jsonString,
                    NewsList::class.java
                )
                it.onNext(newsList.news)
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e.fillInStackTrace())
            } finally {
                inputStream?.close()
            }
        }
    }

}
