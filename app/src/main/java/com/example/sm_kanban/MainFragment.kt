package com.example.sm_kanban

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
//import androidx.navigation.fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
//import com.example.sm_kanban.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment() : Fragment() {
    lateinit var settingsButton: ImageButton

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }


        //Read tasklist data from shared prefs and populate viewmodel lists with it
//        readSharedPrefsToViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        //Get ref to viewpager and set up its adapter & attributes
        viewPager = view.findViewById(R.id.viewPager)
        val viewPagerAdapter = TasklistFragmentStateAdapter(this)
        viewPager.apply{
            adapter = viewPagerAdapter
            offscreenPageLimit = 2
        }

        //Get ref to tablayout, set up & attach its mediator
        tabLayout = view.findViewById(R.id.tabLayout2)
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = TaskStatus.getLocalizedString(view.context, position)
        }.attach()
        return view
    }

    private inner class TasklistFragmentStateAdapter(fa: Fragment) : FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment {
            //Create argument bundle with task list type
            val tasklistFragmentArguments = Bundle().apply{
                putInt(ARG_NAME_taskListType, position)
            }

            //Attach the argument bundle to new fragment instance and return the fragment
            return TabFragment().apply{
                arguments = tasklistFragmentArguments
            }
        }

        override fun getItemCount(): Int = 3
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

}