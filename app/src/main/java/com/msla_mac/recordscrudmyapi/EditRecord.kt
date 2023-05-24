package com.msla_mac.recordscrudmyapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditRecord : BaseActivity() {

    lateinit var edtEditRecordID: EditText
    lateinit var edtEditName: EditText
    lateinit var edtEditDescription: EditText
    lateinit var edtEditPrice: EditText
    lateinit var edtEditRating: EditText
    lateinit var edtEditDateModified: TextView
    lateinit var edtEditURL : EditText

    private val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    private val modifiedDate : String = LocalDateTime.now().format(formatter)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_record)
        val record = recordsList[currentRecord]

        edtEditRecordID = findViewById(R.id.edtEditRecordID)
        edtEditName = findViewById(R.id.edtEditName)
        edtEditDescription = findViewById(R.id.edtEditDescription)
        edtEditPrice = findViewById(R.id.edtEditPrice)
        edtEditRating = findViewById(R.id.edtEditRating)
        edtEditDateModified = findViewById(R.id.edtEditDateModified)
        edtEditURL = findViewById(R.id.edtEditURL)


        //setting textEdit to default values
        edtEditRecordID.setText(record.recordID.toString())
        edtEditName.setText(record.name)
        edtEditDescription.setText(record.description)
        edtEditPrice.setText(record.price.toString())
        edtEditRating.setText(record.rating.toString())
        edtEditURL.setText(record.image)
        edtEditDateModified.setText(record.dateModified)
    }

    fun editRecordsOnClick(v: View) {
        val record = recordsList[currentRecord]
        record.name = edtEditName.text.toString()
        record.description = edtEditDescription.text.toString()
        record.price = edtEditPrice.text.toString().toDouble()
        record.rating = edtEditRating.text.toString().toInt()
        record.image = edtEditURL.text.toString()
        recordsList[currentRecord] = record

        //Write the edited record to database:
        //Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        //Request a string response from the provided URL.
        val jsonObjectRequest : StringRequest = object : StringRequest (
            Request.Method.PUT,
            "$baseUrl/${record.recordID}",
            Response.Listener { response ->
                //display the first 500 characters of the response string.
                Log.i("CRUDapi", "Response is: $response")
            },
            Response.ErrorListener {
                Log.i("CRUDapi", "It no worky - ${it.message}")
            }){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String, String> = HashMap()
                params["name"] = record.name
                params["description"] = record.description
                params["price"] = record.price.toString()
                params["rating"] = record.rating.toString()
                params["image"] = record.image
                params["modifiedDate"] = modifiedDate
                return params
            }
        }

        //Add the request to the RequestQueue.
        jsonObjectRequest.setShouldCache(false) //this forces information retrieval from the network again and not the volley cache data file in the project.
        queue.add(jsonObjectRequest)
            //End save to database

        toastIt("Item Updated")
        val intent = Intent(this, ShowRecord::class.java)
        startActivity(intent)
    }

    fun showAllRecordsOnClick(v : View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}