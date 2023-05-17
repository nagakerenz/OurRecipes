package ourrecipe.uib.ourrecipes.ui.home.Categories

import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.car.ui.toolbar.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ourrecipe.uib.ourrecipes.R
import ourrecipe.uib.ourrecipes.databinding.ActivityCategoriesNavigationBinding
import java.lang.reflect.Type
import kotlin.math.roundToInt

class CategoriesNavigation : Fragment() {

    private lateinit var binding: ActivityCategoriesNavigationBinding
    private var tabTitles = mutableMapOf(
            "Home" to R.drawable.food_icon_breakfast_burito,
            "Call" to R.drawable.lunch_chickenkatsu,
            "Chat" to R.drawable.food_icon_dinner_bimbimbap
    )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCategoriesNavigationBinding.inflate(layoutInflater)
        setUpTabLayoutWithViewPager()
        return binding.root
    }

    private fun setUpTabLayoutWithViewPager() {
        val titles = ArrayList(tabTitles.keys)
        binding.viewPagerCategoriesFood.adapter = ViewPagerCategoriesNavigationAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPagerCategoriesFood) {tab, position ->
            tab.text = titles[position]
        }.attach()

        tabTitles.values.forEachIndexed { index, imageResId ->
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.activity_categories_navigation_tab_title, null)
                    as TextView
            textView.setCompoundDrawablesWithIntrinsicBounds(0, imageResId, 0, 0)
            textView.compoundDrawablePadding = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics
            ).roundToInt()
            binding.tabLayout.getTabAt(index)?.customView = textView
        }
    }
}