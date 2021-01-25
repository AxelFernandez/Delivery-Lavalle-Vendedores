package com.axelfernandez.deliverylavallevendedores.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.axelfernandez.deliverylavallevendedores.R
import kotlinx.android.synthetic.main.component_title.view.*

/**
 * TODO: document your custom view class.
 */
class TitleComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null

) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_title, this, true)

        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TitleComponent, 0, 0)
            val title = typedArray.getString(R.styleable.TitleComponent_title)
            component_title_title.text = title
            typedArray.recycle()
        }
    }

    var title: String
        get() {
            return component_title_title.text.toString()
        }
        set(value) {
            component_title_title.text = value

        }
}
