package com.lang98.floatingactionmenu.FloatingActionMenu

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.annotation.ColorRes
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.lang98.floatingactionmenu.R
import com.lang98.floatingactionmenu.R.*
import java.util.concurrent.atomic.AtomicInteger

class FloatingActionMenu : FrameLayout {

    private var mActions: ArrayList<FloatingActionModel> = ArrayList()
    private var mRows: ArrayList<ConstraintLayout> = ArrayList()
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var floatingActionButton: FloatingActionButton? = null
    var isOpen = false
    var dismissOnTapOutside = false


    private val mAnimationCount = AtomicInteger()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setupMainFloatingActionButton()
    }

    fun addSubFloatingActionButton(action: FloatingActionModel,
                                   onClickListener: OnClickListener): FloatingActionMenu {
        mActions.add(action)
        val row = mInflater.inflate(layout.floating_action_menu_row, this, false) as ConstraintLayout
        row.setOnClickListener {
            toggleOpenClose(OnRowClickListener(it, onClickListener))
        }
        mRows.add(row)
        return this
    }

    fun getSize(): Int {
        return mActions.size
    }

    fun setMainButtonDrawableId(resId: Int): FloatingActionMenu {
        floatingActionButton?.setImageDrawable(ContextCompat.getDrawable(context, resId))
        return this
    }

    fun setColorFilter() {

    }

    fun apply(): FloatingActionMenu {
        mRows.forEachIndexed { index, it ->
            with(it.findViewById<ImageView>(R.id.floating_action_menu_image)) {
                setImageResource(mActions[index].iconResId)
                setColorFilter(Color.argb(255,255,255,255))
                ViewCompat.setBackground(this, generateBackgroundShape(mActions[index].iconBgColorResId))
            }
            it.findViewById<TextView>(R.id.floating_action_menu_label).setText(mActions[index].labelResId)
            if (getChildAt(index) != it) {
                addView(it, getDefaultParam())
            }
            it.visibility = View.GONE
        }
        return this
    }

    fun setDissmissOnTapOutside(toDismiss: Boolean): FloatingActionMenu {
        dismissOnTapOutside = toDismiss
        return this
    }

    private fun generateBackgroundShape(@ColorRes colorResId: Int): Drawable {
        val drawable = ShapeDrawable(OvalShape())
        drawable.paint.color = ContextCompat.getColor(context, colorResId)
        return drawable
    }

    private fun getAnimationHeight(miniFabIndex: Int): Float {
        val base = resources.getDimension(dimen.fab_size_normal) - resources.getDimension(dimen.space)
        val spacing = miniFabIndex.toFloat() * resources.getDimension(dimen.fab_size_mini) + (miniFabIndex + 1).toFloat() * resources.getDimension(
            dimen.fab_menu_spacing)
        return -(base + spacing)
    }

    private fun setupMainFloatingActionButton() {
        floatingActionButton = FloatingActionButton(context)
        floatingActionButton?.setImageDrawable(ContextCompat.getDrawable(context, drawable.ic_add_black_24dp))

        addView(floatingActionButton, getDefaultParam())
        floatingActionButton?.setOnClickListener {
            toggleOpenClose()
        }
    }

    private fun getDefaultParam(): FrameLayout.LayoutParams {
        val gravity = Gravity.BOTTOM or Gravity.END
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.bottomMargin = getMargin()
        params.marginEnd = getMargin()
        params.gravity = gravity
        return params
    }

    private fun getMargin(): Int {
        return context.resources.getDimensionPixelOffset(dimen.space)
    }

    private fun toggleOpenClose() {
        toggleOpenClose(null)
    }

    private fun toggleOpenClose(onRowClickListener: OnRowClickListener? = null) {
        isOpen = !isOpen
        floatingActionButton?.animate()?.rotation(if (isOpen) 135f else 0f)
        setBackgroundColor(ContextCompat.getColor(context, if (isOpen) color.white_abb else android.R.color.transparent))

        mRows.forEachIndexed { index, it ->
            if (isOpen) {
                showMenuRow(it, index)
            } else {
                hideMenuRow(it, onRowClickListener)
            }
        }
    }

    private fun showMenuRow(layout: ConstraintLayout, index: Int) {
        layout.visibility = View.VISIBLE
        layout.animate().alpha(1f).translationY(getAnimationHeight(index))
        if (dismissOnTapOutside) {
            setOnClickListener { toggleOpenClose() }
        } else {
            isClickable = true
        }
    }

    private fun hideMenuRow(layout: ConstraintLayout, onRowClickListener: OnRowClickListener? = null) {
        mAnimationCount.incrementAndGet()
        setOnClickListener(null)
        isClickable = false

        layout.animate().translationY(0f).alpha(0f).withEndAction {
            layout.visibility = View.GONE

            if (mAnimationCount.decrementAndGet() == 0) {
                onRowClickListener?.performClick()
            }
        }
    }

    private open class OnRowClickListener(val view: View, val onClickListener: OnClickListener?) {
        internal fun performClick() {
            onClickListener?.onClick(view)
        }
    }
}