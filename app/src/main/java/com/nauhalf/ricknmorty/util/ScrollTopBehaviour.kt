package com.nauhalf.ricknmorty.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener


class ScrollToTopBehavior(context: Context?, attrs: AttributeSet?) :
	CoordinatorLayout.Behavior<FloatingActionButton?>(context, attrs) {
	override fun layoutDependsOn(
		parent: CoordinatorLayout,
		child: FloatingActionButton,
		dependency: View
	): Boolean {
		return dependency is RecyclerView
	}

	override fun onDependentViewChanged(
		parent: CoordinatorLayout,
		child: FloatingActionButton,
		dependency: View
	): Boolean {
		val recyclerView = dependency as RecyclerView
		val layoutManager = recyclerView.layoutManager
		if (layoutManager != null && layoutManager is LinearLayoutManager) {
			val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
			val hasVisibleItems = firstVisibleItemPosition != -1
			val firstItemVisible = firstVisibleItemPosition == 0
			if (child.visibility == View.VISIBLE) {
				if (!hasVisibleItems || firstItemVisible) {
					child.hide(object : OnVisibilityChangedListener() {
						override fun onHidden(fab: FloatingActionButton) {
							child.visibility = View.INVISIBLE
						}
					})
					return true
				}
			} else {
				if (hasVisibleItems && !firstItemVisible) {
					child.show()
					return true
				}
			}
		}
		return false
	}
}
