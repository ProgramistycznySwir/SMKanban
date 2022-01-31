package com.example.sm_kanban

//import androidx.navigation.fragment
//import com.example.sm_kanban.databinding.ActivityMainBinding
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment() : Fragment() {
    lateinit var settingsButton: ImageButton

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    lateinit var addTaskFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        // Add rickroll.
        settingsButton.setOnLongClickListener {
            // Walnij dropa bazy.
            (context as MainActivity).dropnijBazę()
            // Rzuć pastą.
            (context as MainActivity).dajPasteOStażyścieProgramiście()
            true
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

        addTaskFAB = view.findViewById(R.id.floatingActionButton5)
        addTaskFAB.setOnClickListener { 
            (context as MainActivity).addTask(Task(status = TaskStatus.get(viewPager.currentItem)))
        }
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}