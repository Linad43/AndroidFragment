package com.example.androidfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.time.LocalDate


class UpdateTaskFragment : Fragment(), OnFragmentDataListner {

    private lateinit var onFragmentDataListner: OnFragmentDataListner
    private lateinit var textET: EditText
    private lateinit var saveBTN: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_task, container, false)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentDataListner = requireActivity() as OnFragmentDataListner
        saveBTN = view.findViewById(R.id.save)
        textET = view.findViewById(R.id.taskET)
        val task = arguments?.getSerializable(Task::class.java.simpleName) as Task
        textET.setText(task.text)
        saveBTN.setOnClickListener {
            if (textET.text.isNotEmpty()) {
                val newTask = Task(task.id, textET.text.toString(), false, LocalDate.now().toString())
                onData(task)
            }
        }
    }

    override fun onData(task: Task) {
        val bundle = Bundle()
        bundle.putSerializable(Task::class.java.simpleName, task)
        val transaction = this.fragmentManager?.beginTransaction()
        val firstFragment = FirstFragment()
        firstFragment.arguments = bundle
        transaction?.replace(R.id.fragment_container, firstFragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

}
