package lt.setkus.superhero.app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import lt.setkus.superhero.R
import lt.setkus.superhero.app.ui.widget.StateLayout.State.CONTENT
import lt.setkus.superhero.app.ui.widget.StateLayout.State.ERROR
import lt.setkus.superhero.app.ui.widget.StateLayout.State.LOADING
import lt.setkus.superhero.app.ui.widget.StateLayout.State.NONE
import timber.log.Timber

class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var layoutState: State = NONE

    private var contentLayout: View? = null
    private var loadingLayout: View? = null
    private var errorLayout: View? = null

    @LayoutRes
    private var loadingLayoutRes: Int
    @LayoutRes
    private var errorLayoutRes: Int

    init {
        if (isInEditMode) {
            layoutState = CONTENT
        }

        context.obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0).apply {
            try {
                loadingLayoutRes = getResourceId(R.styleable.StateLayout_loadingLayout, R.layout.state_loading)
                errorLayoutRes = getResourceId(R.styleable.StateLayout_errorLayout, R.layout.state_error)
            } finally {
                recycle()
            }
        }
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
        prepareErrorLayout()
        updateWithState()
    }

    private fun updateWithState() {
        when (layoutState) {
            LOADING -> loading()
            CONTENT -> content()
            ERROR -> error()
            NONE -> content()
            else -> hideAll()
        }
    }

    fun loading(): StateLayout {
        Timber.d("loading state")
        layoutState = LOADING
        updateLoadingVisibility(VISIBLE)
        contentLayout?.visibility = GONE
        return this
    }

    private fun hideAll() {
        Timber.d("hide all state")
        contentLayout?.visibility = GONE
        loadingLayout?.visibility = GONE
    }

    fun content(): StateLayout {
        Timber.d("content state")
        layoutState = CONTENT
        updateLoadingVisibility(GONE)
        contentLayout?.visibility = VISIBLE
        return this
    }

    fun error(): StateLayout {
        Timber.d("error state")
        layoutState = ERROR
        errorLayout?.visibility = View.VISIBLE
        return this
    }

    private fun prepareLoadingLayout() {
        loadingLayout = LayoutInflater.from(context).inflate(loadingLayoutRes, null)
        loadingLayout?.visibility = GONE
        addView(loadingLayout, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER))
    }

    private fun prepareContentLayout() {
        contentLayout = getChildAt(0)
        contentLayout?.visibility = GONE
    }

    private fun prepareErrorLayout() {
        errorLayout = LayoutInflater.from(context).inflate(loadingLayoutRes, null)
        errorLayout?.visibility = GONE
        addView(errorLayout, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER))
    }

    enum class State {
        LOADING, CONTENT, ERROR, EMPTY, NONE
    }
}