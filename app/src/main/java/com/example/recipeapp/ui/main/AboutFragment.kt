package com.example.recipeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Developer
import com.example.recipeapp.ui.adapter.DeveloperAdapter

class AboutFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        recyclerView = view.findViewById(R.id.recyclerViewDevelopers)

        toolbar.title = "About"
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val developers = listOf(
            Developer("Mohamed Hisham Sayed", "https://www.linkedin.com/in/mohamed-hisham-99832b1b5?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app", "https://github.com/M1-Hisham"),
            Developer("Islam Ahmed Hussein", "https://www.linkedin.com/in/islam-hussein-44675a26a?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app", "https://github.com/isl001"),
            Developer("Ahmed Mohamed El-Telbani", "https://www.linkedin.com/in/ahmed-eltelbani?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app", "https://github.com/ahmedtelbani"),
            Developer("Abdelrahman Ahmed Hamdy", "https://www.linkedin.com/in/abdelrahman-ahmed-a978ba205/", "https://github.com/Boodyahmedhamdy"),
            Developer("Mostafa Abdelraheim Mahmoud", "https://eg.linkedin.com/in/mostafa-abdelraheim-746797278", "https://github.com/Mostafa-araheim")
        )

        val developerAdapter = DeveloperAdapter(developers)
        recyclerView.adapter = developerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

}