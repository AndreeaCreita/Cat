package com.example.cat

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

//import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), View.OnClickListener {

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var etdate: TextInputEditText
    // END

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_add_place)
        etdate = findViewById(R.id.et_date)
        setSupportActionBar(toolbar) // Use the toolbar to set the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // This is to use the home back button.
        // Setting the click event to the back button
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


                updateDateInView()

            }



        etdate.setOnClickListener(this)

    }

    // TODO(Step 5 : This is a override method after extending the onclick listener interface.)

    override fun onClick(v: View?) {
        when (v!!.id) {
            // TODO(Step 7 : Launching the datepicker dialog on click of date edittext.)
            // START
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddActivity,
                    dateSetListener, // This is the variable which have created globally and initialized in setupUI method.
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR), // Here the cal instance is created globally and used everywhere in the class where it is required.
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault()) // A date format
        etdate.setText(sdf.format(cal.time).toString()) // A selected date using format which we have used is set to the UI.
    }
    // END
}
