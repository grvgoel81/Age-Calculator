package com.gaurav.agecalculator

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_get_user_details.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by gauravgoel
 */
class GetUserDetailsFragment : Fragment() {
    lateinit var model: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_user_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        showDatePicker(etBirthday)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker(dateEditText: AppCompatEditText) {

        val myCalendar = Calendar.getInstance()

        val datePickerDataSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar, dateEditText)
        }

        dateEditText.setOnClickListener {
            DatePickerDialog(requireContext(), datePickerDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
                .show()
        }

        btnShowAge.setOnClickListener { navigateToDetails() }
    }

    private fun updateLabel(myCalendar: Calendar, dateEditText: AppCompatEditText) {
        val sdf = SimpleDateFormat(resources.getString(R.string.date_format), Locale.US)
        dateEditText.setText(sdf.format(myCalendar.time))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigateToDetails() {
        if(model.isDataCompleted(etFirstName.text.toString(), etLastName.text.toString().trim(), etBirthday.text.toString()) &&
            model.checkAgeValidity(etBirthday.text.toString(), resources.getString(R.string.date_format))) {
            model.setFirstValue(etFirstName.text.toString().trim())
            model.setLastValue(etLastName.text.toString().trim())
            model.setAge(etBirthday.text.toString().trim(), resources.getString(R.string.date_format))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container, ShowAgeFragment.newInstance()).addToBackStack(null)
                .commit()
        } else {
            Toast.makeText(requireContext(), resources.getString(R.string.age_validity_error_message), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = GetUserDetailsFragment()
    }
}