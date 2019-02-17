package drewhamilton.skylight.views.event

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.card.MaterialCardView
import drewhamilton.skylight.views.R
import drewhamilton.skylight.views.compat.setCompatTextAppearance
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
            label.visibility = if (shouldShowLabel) View.VISIBLE else View.INVISIBLE
        }

    internal var timeHint: CharSequence?
        get() = time.hint
        set(hint) {
            time.hint = hint
            label.visibility = if (shouldShowLabel) View.VISIBLE else View.INVISIBLE
        }

    private val shouldShowLabel
        get() = !(time.text.isEmpty() && time.hint?.isEmpty() ?: true)

    init {
        inflate(context, R.layout.view_skylight_event, this)
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
        label.visibility = if (shouldShowLabel) View.VISIBLE else View.INVISIBLE
    }

    private fun initAttributeSet(attrs: AttributeSet) {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SkylightEventView)
        try {
            val labelTextAppearance = styledAttributes.getResourceId(
                R.styleable.SkylightEventView_skylightEventLabelTextAppearance,
                R.style.TextAppearance_AppCompat_Caption
            )
            label.setCompatTextAppearance(labelTextAppearance)
            label.text = styledAttributes.getString(R.styleable.SkylightEventView_skylightEventLabelText)

            val timeTextAppearance = styledAttributes.getResourceId(
                R.styleable.SkylightEventView_skylightEventTimeTextAppearance,
                R.style.TextAppearance_AppCompat_Display3
            )
            time.setCompatTextAppearance(timeTextAppearance)
            time.text = styledAttributes.getString(R.styleable.SkylightEventView_skylightEventTimeText)

            if (shouldShowLabel) label.visibility = View.VISIBLE
        } finally {
            styledAttributes.recycle()
        }
    }
}

fun SkylightEventView.setTime(time: Date?, @StringRes fallback: Int) =
    setTime(time, fallback = context.getString(fallback))

fun SkylightEventView.setTime(dateTime: Date?, format: DateFormat, @StringRes fallback: Int) =
    setTime(dateTime, format, context.getString(fallback))

fun SkylightEventView.setTime(
    dateTime: Date?,
    format: DateFormat = DateFormat.getTimeInstance(DateFormat.SHORT),
    fallback: String = ""
) {
    dateTime?.let {
        timeText = format.format(dateTime)
        timeHint = ""
    } ?: run {
        timeHint = fallback
        timeText = ""
    }
}
