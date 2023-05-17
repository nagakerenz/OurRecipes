package ourrecipe.uib.ourrecipes.ui.home.Categories

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ourrecipe.uib.ourrecipes.ui.home.Categories.breakfast.BreakfastFragment
import ourrecipe.uib.ourrecipes.ui.home.Categories.dinner.DinnerFragment
import ourrecipe.uib.ourrecipes.ui.home.Categories.lunch.LunchFragment

class ViewPagerCategoriesNavigationAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> BreakfastFragment()
            1 -> LunchFragment()
            else -> DinnerFragment()
        }
    }
}