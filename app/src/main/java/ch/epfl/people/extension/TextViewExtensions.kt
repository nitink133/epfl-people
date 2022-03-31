package ch.epfl.people.extension

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView

fun TextView.setUnderLineText(text: String?) {
    if (text.isNullOrEmpty()) {
        this.text = text
        return
    }
    this.text = SpannableString(text).apply {
        setSpan(UnderlineSpan(), 0, text.length, 0)
    }
}
