package lt.setkus.superhero.app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.getSize
import android.view.View.MeasureSpec.makeMeasureSpec

class FourThreeLinearLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ForegroundLinearLayout(context, attributeSet, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fourThreeHeight = makeMeasureSpec(getSize(widthMeasureSpec) * 3 / 4, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, fourThreeHeight)
    }

    /**
     * Returns whether this View has content which overlaps.
     *
     *
     * This function, intended to be overridden by specific View types, is an optimization when
     * alpha is set on a view. If rendering overlaps in a view with alpha < 1, that view is drawn to
     * an offscreen buffer and then composited into place, which can be expensive. If the view has
     * no overlapping rendering, the view can draw each primitive with the appropriate alpha value
     * directly. An example of overlapping rendering is a TextView with a background image, such as
     * a Button. An example of non-overlapping rendering is a TextView with no background, or an
     * ImageView with only the foreground image. The default implementation returns true; subclasses
     * should override if they have cases which can be optimized.
     *
     *
     * **Note:** The return value of this method is ignored if [ ][.forceHasOverlappingRendering] has been called on this view.
     *
     * @return true if the content in this view might overlap, false otherwise.
     */
    override fun hasOverlappingRendering(): Boolean = false
}