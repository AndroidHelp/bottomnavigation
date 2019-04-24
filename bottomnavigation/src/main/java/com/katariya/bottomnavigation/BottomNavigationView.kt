package com.katariya.bottomnavigation

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.*


class BottomNavigationView : LinearLayout {

    private lateinit var bottomMenuListener: BottomMenuListener
    private var listItems: ArrayList<Item> = ArrayList()
    private var parentLayout: LinearLayout

    private var tab_background_color: Int
    private var tab_selected_item_background_color: Int
    private var tab_selected_item_text_color: Int
    private var tab_unselected_item_text_color: Int
    private var tab_badge_item_text_color: Int
    private var tab_badge_background_color: Int

    private var tab_item_text_size: Float
    private var tab_badge_text_size: Float
    private var tab_badge_background_border_size: Int

    private var tab_icon_unselected_width: Int
    private var tab_icon_unselected_height: Int
    private var tab_icon_selected_width: Int
    private var tab_icon_selected_height: Int

    private var tab_margin_in_icon_text_in_selected: Int
    private var tab_margin_in_icon_text_in_unselected: Int

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0) :
            super(context, attributeSet, defStyle, defStyleRes) {
        var view = LayoutInflater.from(context).inflate(R.layout.bottom_navigation, this, true)
        parentLayout = view.findViewById(R.id.linear_parent)
        parentLayout.elevation = 10F

        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomNavigationView)
        tab_background_color =
            attributes!!.getColor(R.styleable.BottomNavigationView_tab_background_color, Color.LTGRAY)
        tab_selected_item_background_color =
            attributes.getColor(R.styleable.BottomNavigationView_tab_selected_item_background_color, Color.GRAY)
        tab_selected_item_text_color =
            attributes.getColor(R.styleable.BottomNavigationView_tab_selected_item_text_color, Color.BLUE)
        tab_unselected_item_text_color =
            attributes.getColor(R.styleable.BottomNavigationView_tab_unselected_item_text_color, Color.BLACK)
        tab_badge_item_text_color =
            attributes.getColor(R.styleable.BottomNavigationView_tab_badge_item_text_color, Color.WHITE)
        tab_badge_background_color =
            attributes.getColor(R.styleable.BottomNavigationView_tab_badge_background_color, Color.RED)

        tab_item_text_size = attributes.getFloat(R.styleable.BottomNavigationView_tab_item_text_size, 12F)
        tab_badge_text_size = attributes.getFloat(R.styleable.BottomNavigationView_tab_badge_text_size, 9F)
        tab_badge_background_border_size =
            attributes.getInteger(R.styleable.BottomNavigationView_tab_badge_background_border_size, 2)
        tab_icon_unselected_width =
            attributes.getInteger(R.styleable.BottomNavigationView_tab_icon_unselected_width, 55)
        tab_icon_unselected_height =
            attributes.getInteger(R.styleable.BottomNavigationView_tab_icon_unselected_height, 55)
        tab_icon_selected_width = attributes.getInteger(R.styleable.BottomNavigationView_tab_icon_selected_width, 55)
        tab_icon_selected_height = attributes.getInteger(R.styleable.BottomNavigationView_tab_icon_selected_height, 55)

        tab_margin_in_icon_text_in_selected =
            attributes.getInteger(R.styleable.BottomNavigationView_tab_margin_in_icon_text_in_selected, 5)
        tab_margin_in_icon_text_in_unselected =
            attributes.getInteger(R.styleable.BottomNavigationView_tab_margin_in_icon_text_in_unselected, 5)

        parentLayout.setBackgroundColor(tab_background_color)
    }

    fun initItemListener(bottomMenuListener: BottomMenuListener) {
        this.bottomMenuListener = bottomMenuListener
        if (!listItems.isEmpty())
            createNavigationView()
    }

    fun addChild(iconUnselected: Int?, iconSelected: Int?, title: String?) {
        listItems.add(Item(iconUnselected, iconSelected, title))
    }

    private fun createNavigationView() {
        parentLayout.weightSum = listItems.size.toFloat()

        listItems.forEachIndexed { index, item ->
            val paramsMM = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            val paramsWW = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            val paramsMMW =
                LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0.toFloat())

            var linearLayout = LinearLayout(context)
            linearLayout.orientation = VERTICAL
            linearLayout.gravity = Gravity.CENTER
            linearLayout.id = index

            parentLayout.addView(linearLayout, paramsMMW)

            if (item.drawableUnselected != null) {
                val frameLayout = FrameLayout(context)
                frameLayout.id = index
                frameLayout.layoutParams = paramsWW

                linearLayout.addView(frameLayout)

                val icon = ImageView(context)
                icon.id = index

                val txtBadge = TextView(context)
                txtBadge.id = index
                txtBadge.gravity = Gravity.CENTER
                txtBadge.textSize = tab_badge_text_size
                txtBadge.setTextColor(tab_badge_item_text_color)

                val paramsBadge = LinearLayout.LayoutParams(25, 25)
                paramsBadge.gravity = Gravity.CENTER
                txtBadge.layoutParams = paramsBadge
                txtBadge.visibility = View.INVISIBLE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    txtBadge.background = getBadgeBackground()
                }

                val paramsBadgeM = FrameLayout.LayoutParams(25, 25)
                paramsBadgeM.gravity = Gravity.RIGHT

                val paramsIconM = LinearLayout.LayoutParams(tab_icon_unselected_width, tab_icon_unselected_height)
                paramsIconM.gravity = Gravity.CENTER
                paramsIconM.setMargins(10, 10, 10, tab_margin_in_icon_text_in_unselected)
                frameLayout.layoutParams = paramsIconM

                frameLayout.addView(icon)
                frameLayout.addView(txtBadge, paramsBadgeM)
                icon.setImageResource(item.drawableUnselected!!)
            }

            if (item.name != null) {

                if (item.drawableUnselected == null) {
                    val frameLayout = FrameLayout(context)
                    frameLayout.id = index
                    frameLayout.layoutParams = paramsMM

                    linearLayout.addView(frameLayout)

                    val txtBadge = TextView(context)
                    txtBadge.id = index
                    txtBadge.gravity = Gravity.CENTER
                    txtBadge.textSize = tab_badge_text_size
                    txtBadge.setTextColor(tab_badge_item_text_color)

                    val paramsBadge = LinearLayout.LayoutParams(25, 25)
                    paramsBadge.gravity = Gravity.CENTER
                    txtBadge.layoutParams = paramsBadge
                    txtBadge.visibility = View.INVISIBLE

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtBadge.background = getBadgeBackground()
                    }
                    val paramsBadgeM = FrameLayout.LayoutParams(25, 25)
                    paramsBadgeM.gravity = Gravity.RIGHT
                    paramsBadgeM.setMargins(0, 10, 10, 0)

                    //   val paramsTextM = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                    //   paramsTextM.gravity = Gravity.CENTER
                    //  paramsTextM.setMargins(0, 0, 0, 0)

                    frameLayout.addView(txtBadge, paramsBadgeM)

                    val title = TextView(context)
                    title.gravity = Gravity.CENTER
                    title.id = index
                    title.textSize = tab_item_text_size
                    title.text = item.name
                    title.setTextColor(tab_selected_item_text_color)

                    frameLayout.addView(title)

                } else {
                    val title = TextView(context)
                    title.gravity = Gravity.CENTER
                    title.id = index
                    title.textSize = tab_item_text_size
                    title.text = item.name
                    linearLayout.addView(title)
                    title.setTextColor(tab_unselected_item_text_color)
                }

            }

            itemClicked(0, listItems.get(0))
            linearLayout.setOnClickListener {
                itemClick(index)
                bottomMenuListener.bottomNavigationViewItemClickListener(index)
            }
        }

    }

    fun updateFont(fontPath: String) {
        if (listItems.isEmpty())
            return
        listItems.forEachIndexed { index, item ->
            val child = (parentLayout as ViewGroup).getChildAt(index)
            val frame = (child as ViewGroup).getChildAt(0)
            if (item.name != null) {
                var text: View? = null
                if (item.drawableUnselected != null) {
                    text = (frame as ViewGroup).getChildAt(1)
                } else {
                    text = (frame as ViewGroup).getChildAt(0)
                }
                val childTextView = text as TextView
                val face = Typeface.createFromAsset(
                    context.assets,
                    fontPath
                )
                childTextView.typeface = face
            }
        }
    }

    fun setBadge(indexBadge: Int, value: Int) {
        if (listItems.isEmpty())
            return
        listItems.forEachIndexed { index, item ->
            if (index == indexBadge)
                item.badge = value
            updateBadge(index, item)
        }
    }

    private fun updateBadge(index: Int, item: Item) {
        val child = (parentLayout as ViewGroup).getChildAt(index)
        if (item.drawableUnselected != null) {
            val frame = (child as ViewGroup).getChildAt(0)
            val textBadge = (frame as ViewGroup).getChildAt(1)

            if (item.badge > 0) {
                (textBadge as TextView).visibility = View.VISIBLE
                textBadge.text = item.badge.toString()
            } else (textBadge as TextView).visibility = View.GONE
        }
        if (item.name != null) {

            if (item.drawableUnselected == null) {
                val frame = (child as ViewGroup).getChildAt(0)
                val textBadge = (frame as ViewGroup).getChildAt(0)
                if (item.badge > 0) {
                    (textBadge as TextView).visibility = View.VISIBLE
                    textBadge.text = item.badge.toString()
                } else (textBadge as TextView).visibility = View.GONE
            }
        }
    }

    private fun itemClick(indexClicked: Int) {

        listItems.forEachIndexed { index, item ->
            if (indexClicked == index) {
                itemClicked(index, item)
                bottomMenuListener.bottomNavigationViewItemClickListener(index)
            } else {
                itemNotClicked(index, item)
            }
        }
    }

    private fun itemClicked(index: Int, item: Item) {
        val child = (parentLayout as ViewGroup).getChildAt(index)
        child.setBackgroundColor(tab_selected_item_background_color)
        if (item.drawableUnselected != null) {
            val frame = (child as ViewGroup).getChildAt(0)
            val icon = (frame as ViewGroup).getChildAt(0)
            val params = LinearLayout.LayoutParams(tab_icon_selected_width, tab_icon_selected_height)
            params.gravity = Gravity.CENTER
            params.setMargins(10, 10, 10, tab_margin_in_icon_text_in_selected)
            frame.layoutParams = params
            if (item.drawableSelected != null) {
                (icon as ImageView).setImageResource(item.drawableSelected!!)
            }
        }
        if (item.name != null) {
            val childTextView: TextView
            if (item.drawableUnselected == null) {
                val frame = (child as ViewGroup).getChildAt(0)
                val text = (frame as ViewGroup).getChildAt(1)
                childTextView = text as TextView
            } else {
                val text = (child as ViewGroup).getChildAt(1)
                childTextView = text as TextView
            }
            childTextView.setTextColor(tab_selected_item_text_color)
        }
    }

    private fun itemNotClicked(index: Int, item: Item) {
        val child = (parentLayout as ViewGroup).getChildAt(index)
        if (item.drawableUnselected != null) {
            val frame = (child as ViewGroup).getChildAt(0)
            val icon = (frame as ViewGroup).getChildAt(0)
            val params = LinearLayout.LayoutParams(tab_icon_unselected_width, tab_icon_unselected_height)
            params.gravity = Gravity.CENTER
            params.setMargins(10, 10, 10, tab_margin_in_icon_text_in_unselected)
            frame.layoutParams = params
            if (item.drawableSelected != null) {
                (icon as ImageView).setImageResource(item.drawableUnselected!!)
            }
        }
        if (item.name != null) {
            val childTextView: TextView
            if (item.drawableUnselected == null) {
                val frame = (child as ViewGroup).getChildAt(0)
                val text = (frame as ViewGroup).getChildAt(1)
                childTextView = text as TextView
            } else {
                val text = (child as ViewGroup).getChildAt(1)
                childTextView = text as TextView
            }
            childTextView.setTextColor(tab_unselected_item_text_color)
        }
        child.setBackgroundColor(tab_background_color)
        /*  if (item.name != null) {
              var text: View? = null
              if (item.drawable != null) {
                  text = (child as ViewGroup).getChildAt(1)
              } else {
                  text = (child as ViewGroup).getChildAt(0)
              }
              val childTextView = text as TextView
              childTextView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
          }
          child.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))*/
    }

    fun getBadgeBackground(): GradientDrawable {
        val strokeWidth = tab_badge_background_border_size
        val strokeColor = tab_badge_background_color
        val fillColor = tab_badge_background_color
        val gD = GradientDrawable()
        gD.setColor(fillColor)
        gD.shape = GradientDrawable.OVAL
        gD.setStroke(strokeWidth, strokeColor)
        return gD
    }


    interface BottomMenuListener {

        fun bottomNavigationViewItemClickListener(position: Int)
    }

}