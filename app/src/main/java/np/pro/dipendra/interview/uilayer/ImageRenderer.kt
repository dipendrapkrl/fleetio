package np.pro.dipendra.interview.uilayer

import android.widget.ImageView
import com.squareup.picasso.Picasso
import np.pro.dipendra.interview.R

fun ImageView.displayImageFrom(url: String) {
    if (url.trim().isNotEmpty()) Picasso.get()
        .load(url)
        .error(R.drawable.car)
        .placeholder(R.drawable.car)
        .into(this)
    else
        setImageResource(R.drawable.car)
}