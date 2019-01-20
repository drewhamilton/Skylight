package drewhamilton.skylight.views.event

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.card.MaterialCardView
import drewhamilton.skylight.views.R
import kotlinx.android.synthetic.main.view_skylight_event.view.*
import java.text.DateFormat
import java.util.*

class SkylightEventView : MaterialCardView {

    var labelText: CharSequence
        get() = label.text
        set(text) {
            label.text = text
        }

    var timeText: CharSequence
        get() = time.text
        set(text) {
            time.text = text
            label.visibility = if (time.text.isEmpty()) View.INVISIBLE else View.VISIBLE
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_skylight_event, this, true)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributeSet(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributeSet(attrs)
    }

    fun setLabelText(@StringRes resId: Int) = label.setText(resId)

    fun setTimeText(@StringRes resId: Int) {
        time.setText(resId)
        label.visibility = if (time.text.isEmpty()) View.INVISIBLE else View.VISIBLE
    }

    private fun initAttributeSet(attrs: AttributeSet) {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SkylightEventView)
        try {
            label.text = styledAttributes.getString(R.styleable.SkylightEventView_skylightEventLabelText)
            time.text = styledAttributes.getString(R.styleable.SkylightEventView_skylightEventTimeText)
        } finally {
            styledAttributes.recycle()
        }
    }
}

fun SkylightEventView.setTime(time: Date?) = setTime(time, DateFormat.getTimeInstance(DateFormat.SHORT))

fun SkylightEventView.setTime(dateTime: Date?, format: DateFormat) {
    timeText = dateTime?.let { format.format(dateTime) } ?: ""
}
