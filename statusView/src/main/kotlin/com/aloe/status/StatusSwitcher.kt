package com.aloe.status

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewAnimator

class StatusSwitcher : ViewAnimator {
  private var show = 0
  private val viewIds = mutableListOf(0, 0, 0, 0)
  private val viewIndex = mutableListOf(-1, -1, -1, -1)
  private val views = mutableListOf<View?>(null, null, null, null)
  private var inAnim = true
  private var outAnim = true
  private val inAnimation = AlphaAnimation(0F,1F).apply {
    duration = 500
  }
  private val outAnimation = AlphaAnimation(1F, 0F).apply {
    duration = 500
  }

  constructor(context: Context) : this(context, null)

  constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
    init(attrs)
  }

  private fun init(attrs: AttributeSet?) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.StatusSwitcher)
    viewIds[0] = a.getResourceId(R.styleable.StatusSwitcher_contentId, 0)
    viewIds[1] = a.getResourceId(R.styleable.StatusSwitcher_loadingId, 0)
    viewIds[2] = a.getResourceId(R.styleable.StatusSwitcher_errorId, 0)
    viewIds[3] = a.getResourceId(R.styleable.StatusSwitcher_emptyId, 0)
    val contentLayout = a.getResourceId(R.styleable.StatusSwitcher_contentLayout, 0)
    val loadingLayout = a.getResourceId(R.styleable.StatusSwitcher_loadingLayout, 0)
    val errorLayout = a.getResourceId(R.styleable.StatusSwitcher_errorLayout, 0)
    val emptyLayout = a.getResourceId(R.styleable.StatusSwitcher_emptyLayout, 0)
    show = a.getInt(R.styleable.StatusSwitcher_show, 0)
    a.recycle()
    if (viewIds[0] == 0) {
      views[0] = if (contentLayout == 0) {
        LayoutInflater.from(context).inflate(R.layout.item_status, this, false)
      } else {
        LayoutInflater.from(context).inflate(contentLayout, this, false)
      }
      addView(views[0])
    }
    if (viewIds[1] == 0) {
      views[1] = if (loadingLayout == 0) {
        LayoutInflater.from(context).inflate(R.layout.item_status, this, false).apply {
          findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.loading)
          findViewById<TextView>(R.id.text).text = "加载中..."
        }
      } else {
        LayoutInflater.from(context).inflate(loadingLayout, this, false)
      }
      addView(views[1])
    }
    if (viewIds[2] == 0) {
      views[2] = if (errorLayout == 0) {
        LayoutInflater.from(context).inflate(R.layout.item_status, this, false).apply {
          findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.ic_error)
          findViewById<TextView>(R.id.text).text = "开小差了"
        }
      } else {
        LayoutInflater.from(context).inflate(errorLayout, this, false)
      }
      addView(views[2])
    }
    if (viewIds[3] == 0) {
      views[3] = if (emptyLayout == 0) {
        LayoutInflater.from(context).inflate(R.layout.item_status, this, false).apply {
          findViewById<ImageView>(R.id.icon).setImageResource(R.drawable.ic_empty)
          findViewById<TextView>(R.id.text).text = "空空如也"
        }
      } else {
        LayoutInflater.from(context).inflate(emptyLayout, this, false)
      }
      addView(views[3])
    }
    if (inAnim) {
      setInAnimation(inAnimation)
    }
    if (outAnim) {
      setOutAnimation(outAnimation)
    }
  }

  override fun setInAnimation(inAnimation: Animation?) {
    super.setInAnimation(inAnimation)
    inAnim = false
  }

  override fun setOutAnimation(outAnimation: Animation?) {
    super.setOutAnimation(outAnimation)
    outAnim = false
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    views.forEachIndexed { index, view ->
      if (view == null) views[index] = findViewById(viewIds[index])
      view?.let {
        viewIndex[index] = indexOfChild(it)
      }
    }
    for (index in 0 until childCount) {
      getChildAt(index).visibility = View.GONE
    }
    views[show]?.visibility = View.VISIBLE
  }

  fun showView(type: StatusType) {
    super.setDisplayedChild(viewIndex[type.value])
  }

  fun getView(type: StatusType): View? = views[type.value]

  override fun setDisplayedChild(statusType: Int) {
    throw RuntimeException("please use showView method.")
  }
}
