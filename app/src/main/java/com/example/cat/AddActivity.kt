package com.example.cat
import android.Manifest
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity(), View.OnClickListener {

    private val CAMERA_REQUEST_CODE = 1
    private lateinit var tvAddImage: TextView
    private lateinit var ivPlaceImage: AppCompatImageView
    private lateinit var etDate: TextInputEditText
    private var imageUri: Uri? = null
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        tvAddImage = findViewById(R.id.tv_add_image)
        ivPlaceImage = findViewById(R.id.iv_place_image)
        etDate = findViewById(R.id.et_date)

        tvAddImage.setOnClickListener(this)
        etDate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_add_image -> {
                openCamera()
            }
            R.id.et_date -> {
                showDatePickerDialog()
            }
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
            imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
    }

    private fun showDatePickerDialog() {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        etDate.setText(sdf.format(cal.time).toString())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            ivPlaceImage.setImageURI(imageUri)
        }
    }
}





























//            R.id.tv_add_image -> {
//                val pictureDialog = AlertDialog.Builder(this)
//                pictureDialog.setTitle("Select Action")
//                val pictureDialogItems =
//                    arrayOf("Select photo from gallery", "Capture photo from camera")
//                pictureDialog.setItems(
//                    pictureDialogItems
//                ) { dialog, which ->
//                    when (which) {
//                        // Here we have create the methods for image selection from GALLERY
//                        //0 -> choosePhotoFromGallery()
//                        1 -> Toast.makeText(this@AddActivity,"Camera selection coming soon...", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                pictureDialog.show()
//            }


//private fun choosePhotoFromGallery() {
//
//        Dexter.withContext(this)
//            .withPermissions(
//
//            )
//            .withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//
//                    if (report!!.areAllPermissionsGranted()) {
//
//                        Toast.makeText(this@AddActivity,"Storage READ/WRITE permission are granted. Now you can select an image from GALLERY or lets says phone storage.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
//                    p1: PermissionToken?
//                ) {
//                    showRationalDialogForPermissions()
//                }
//            }).onSameThread()
//            .check()
//
//        // END
//    }
//    // END

//    private fun showRationalDialogForPermissions() {
//        AlertDialog.Builder(this)
//            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
//            .setPositiveButton("GO TO SETTINGS"
//            ) { _, _ ->
//                try {
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    val uri = Uri.fromParts("package", packageName, null)
//                    intent.data = uri
//                    startActivity(intent)
//                } catch (e: ActivityNotFoundException) {
//                    e.printStackTrace()
//                }
//            }
//            .setNegativeButton("Cancel") { dialog,
//                                           _ ->
//                dialog.dismiss()
//            }.show()
//    }