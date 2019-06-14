package lt.setkus.superhero.app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import lt.setkus.superhero.R
import lt.setkus.superhero.app.ui.widget.StateLayout.State.CONTENT
import lt.setkus.superhero.app.ui.widget.StateLayout.State.LOADING

class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var state: State = LOADING

    private var contentLayout: View? = null
    private var loadingLayout: View? = null

    @LayoutRes
    private var loadingLayoutRes: Int

    init {
        if (isInEditMode) {
            state = CONTENT
        }

        context.obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0).apply {
            try {
                loadingLayoutRes = getResourceId(R.styleable.StateLayout_loadingLayout, R.layout.state_loading)
            } finally {
                recycle()
            }
        }
    }

    fun loading(): StateLayout {
        state = LOADING
        updateLoadingVisibility(VISIBLE)
        contentLayout?.visibility = GONE
        return this
    }

    fun content(): StateLayout {
        state = CONTENT
        updateLoadingVisibility(GONE)
        contentLayout?.visibility = VISIBLE
        return this
    }

    private fun updateLoadingVisibility(visibility: Int) =
        when (visibility) {
            VISIBLE -> loadingLayout?.visibility = VISIBLE
            else -> loadingLayout?.visibility = GONE
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        prepareContentLayout()
        prepareLoadingLayout()

        updateWithState()
    }

    private fun updateWithState() {
        when (state) {
            LOADING -> loading()
            CONTENT -> content()
            else -> content()
        }
    }

    private fun prepareLoadingLayout() {
        loadingLayout = LayoutInflater.from(context).inflate(loadingLayoutRes, null)
        loadingLayout?.visibility = GONE
        addView(loadingLayout)
    }

    private fun prepareContentLayout() {
        contentLayout = getChildAt(0)
        contentLayout?.visibility = GONE
    }

    enum class State {
        LOADING, CONTENT, ERROR, EMPTY
    }
}