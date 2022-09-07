package com.malicankaya.dobcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvSelectedDateInMinutes: TextView? = null
    private var tvSelectedDateInHours: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatepicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedDateInMinutes = findViewById(R.id.tvSelectedDateInMinutes)
        tvSelectedDateInHours = findViewById(R.id.tvSelectedDateInHours)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        var dpd = DatePickerDialog(
            this,
            { view, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = simpleDateFormat.parse(selectedDate)
                var difference: Long = 0
                theDate?.let {
                    //1 JAN 1970 and selected date in minutes
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentTime =
                        simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                    currentTime?.let {
                        //1 JAN 1970 and current date in minutes(millis to min)
                        val currentTimeInMinutes = currentTime.time / 60000
                        difference = currentTimeInMinutes - selectedDateInMinutes
                    }
                }
                myCalendar.set(Calendar.YEAR,selectedYear);
                myCalendar.set(Calendar.MONTH,selectedMonth);
                myCalendar.set(Calendar.DAY_OF_MONTH,selectedDay);
                val sDateWZero = simpleDateFormat.format(myCalendar.time)
                tvSelectedDate?.text = sDateWZero
                tvSelectedDateInMinutes?.text = difference.toString()
                tvSelectedDateInHours?.text = (difference / 60).toString()
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86_400_000
        dpd.show()

    }
}