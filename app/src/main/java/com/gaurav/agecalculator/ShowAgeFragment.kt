package com.gaurav.agecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_show_age.*

/**
 * Created by gauravgoel
 */
class ShowAgeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_age, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        model.firstName.observe(viewLifecycleOwner, Observer { firstName ->
            tvFirstName.text = resources.getString(R.string.first_name).plus(" ").plus(firstName)
        })

        model.lastName.observe(viewLifecycleOwner, Observer { lastName ->
            tvLastName.text = resources.getString(R.string.last_name).plus(" ").plus(lastName)
        })

        model.age.observe(viewLifecycleOwner, Observer { age ->
            tvAge.text = resources.getString(R.string.age).plus(" ").plus(age)
        })
    }

    companion object {
        fun newInstance() = ShowAgeFragment()
    }
}