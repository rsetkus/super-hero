/*
 *   Copyright 2018 Google LLC
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package lt.setkus.superhero.app.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import lt.setkus.superhero.R

/**
 * See https://gist.github.com/chrisbanes/9091754
 */
open class ForegroundLinearLayout : LinearLayout {

    private var currentForeground: Drawable? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ForegroundLinearLayout)

        val foregroundDrawable: Drawable? = typedArray.getDrawable(R.styleable.ForegroundLinearLayout_android_foreground)
        if (foregroundDrawable != null) {
            foreground = foregroundDrawable
        }

        typedArray.recycle()
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || (who == currentForeground)
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        currentForeground?.jumpToCurrentState()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        currentForeground?.apply {
            if (this.isStateful) {
                this.state = drawableState
            }
        }
    }

    /**
     * Returns the drawable used as the foreground of this layout. The
     * foreground drawable, if non-null, is always drawn on top of the children.
     *
     * @return A Drawable or null if no foreground was set.
     */
    override fun getForeground(): Drawable? {
        return currentForeground
    }

    /**
     * Supply a Drawable that is to be rendered on top of all of the child
     * views in this layout.  Any padding in the Drawable will be taken
     * into account by ensuring that the children are inset to be placed
     * inside of the padding area.
     *
     * @param drawable The Drawable to be drawn on top of the children.
     */
    override fun setForeground(foreground: Drawable?) {
        if (currentForeground != foreground) {
            currentForeground?.apply {
                this.callback = null
                unscheduleDrawable(this)
            }

            currentForeground = foreground

            if (foreground != null) {
                setWillNotDraw(false)
                foreground.callback = this
                foreground.apply {
                    if (this.isStateful) {
                        this.state = drawableState
                    }
                }
            } else {
                setWillNotDraw(true)
            }

            requestLayout()
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentForeground?.setBounds(0, 0, w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        currentForeground?.apply {
            this.draw(canvas)
        }
    }
}