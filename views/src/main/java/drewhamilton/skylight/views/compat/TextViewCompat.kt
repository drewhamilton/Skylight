package drewhamilton.skylight.views.compat

import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

// TODO Move these to a library

internal fun TextView.setCompatTextAppearance(@StyleRes resId: Int) = TextViewCompat.setTextAppearance(this, resId)
