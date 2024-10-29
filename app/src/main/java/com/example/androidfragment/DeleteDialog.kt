package com.example.androidfragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DeleteDialog:DialogFragment() {
    private var removable: Removable? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        removable = context as Removable?
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val task = requireArguments().getSerializable(Task::class.java.simpleName)
        val builder = AlertDialog.Builder(
            requireActivity()
        )

        return builder
            .setTitle("Внимание")
            .setMessage("Удалить должность?")
            .setPositiveButton("Да") { dialog, which ->
                removable?.remove(task as Task)
            }.setNegativeButton("Отмена", null)
            .create()
    }
}