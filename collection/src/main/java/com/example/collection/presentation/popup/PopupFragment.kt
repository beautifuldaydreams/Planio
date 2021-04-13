package com.example.collection.presentation.popup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import com.example.collection.R


class PopupFragment: DialogFragment() {

    override fun getTheme() = R.style.DialogCustomTheme

    interface OnInputSelected{
        fun sendInput(input: String)
    }

    private var mOnInputSelected: OnInputSelected? = null

    private lateinit var closeButton: AppCompatImageButton
    private lateinit var createButton: AppCompatImageButton
    private lateinit var textInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_plant_popup, container, false)
        closeButton = view.findViewById(R.id.closed_button)
        createButton = view.findViewById(R.id.create_button)
        textInput = view.findViewById(R.id.enter_name)

        closeButton.setOnClickListener {
            dialog?.dismiss()
        }

        createButton.setOnClickListener{
            val input = textInput.text.toString()
            mOnInputSelected?.sendInput(input)
            dialog?.dismiss()
        }

        this.view?.setBackgroundColor(Color.TRANSPARENT)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnInputSelected = targetFragment as OnInputSelected?
        } catch (e: ClassCastException) {
        }
    }
}