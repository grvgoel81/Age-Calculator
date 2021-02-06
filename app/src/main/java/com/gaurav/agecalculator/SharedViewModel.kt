package com.gaurav.agecalculator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

/**
 * Created by gauravgoel
 */
class SharedViewModel : ViewModel() {

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val age = MutableLiveData<String>()

    fun setFirstValue(firstNameText: String?) {
        firstName.value = firstNameText
    }

    fun setLastValue(lastNameText: String?) {
        lastName.value = lastNameText
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAge(birthdayDate: String?, dateFormat: String?) {
        val period = getBirthdayPeriod(birthdayDate, dateFormat)
        val stringBuffer = StringBuffer()

        if( period.years > 0) {
            stringBuffer.append(period.years).append(" ").append(YEARS).append(", ")
        }

        if(period.months > 0) {
            stringBuffer.append(period.months).append(" ").append(MONTHS).append(", ")
        }

        if(period.days > 0) {
            stringBuffer.append(period.days).append(" ").append(DAYS)
        }

        age.value = stringBuffer.toString()
    }

    fun isDataCompleted(firstName: String, lastName: String, birthdayDate: String) : Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() && birthdayDate.isNotEmpty()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAgeValidity(birthdayDate: String?, dateFormat: String?) :Boolean {
        val period = getBirthdayPeriod(birthdayDate, dateFormat)
        return period.years > 0 || period.months > 0 || period.days > 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBirthdayPeriod(dateString: String?, dateFormat: String?) : Period {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        val today = LocalDate.now()
        val birthday: LocalDate = LocalDate.parse(dateString, formatter)
        return Period.between(birthday, today)
    }

    companion object {
        private const val YEARS = "years"
        private const val MONTHS = "months"
        private const val DAYS = "days"
    }
}