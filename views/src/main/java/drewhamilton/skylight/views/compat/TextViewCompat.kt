package drewhamilton.skylight.views.compat

import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

internal fun TextView.setCompatTextAppearance(@StyleRes resId: Int) = TextViewCompat.setTextAppearance(this, resId)

internal fun TextView.setCompatAutoSizeTextTypeUniformWithConfiguration(
    minTextSize: Int,
    maxTextSize: Int,
    stepGranularity: Int,
    unit: Int
) = TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
    this,
    minTextSize,
    maxTextSize,
    stepGranularity,
    unit
)
