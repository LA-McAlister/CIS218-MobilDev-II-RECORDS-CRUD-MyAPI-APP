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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddRecord : BaseActivity() {


    lateinit var edtEditName: EditText
    lateinit var edtEditDescription: EditText
    lateinit var edtEditPrice: EditText
    lateinit var edtEditRating: EditText
    lateinit var edtEditDateModified: TextView
    lateinit var edtEditDateCreated: TextView

    private val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    private val createDate : String = LocalDateTime.now().format(formatter)!!
    private val modifiedDate : String = LocalDateTime.now().format(formatter)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record)

        edtEditName = findViewById(R.id.edtEditName)
        edtEditDescription = findViewById(R.id.edtEditDescription)
        edtEditPrice = findViewById(R.id.edtEditPrice)
        edtEditRating = findViewById(R.id.edtEditRating)
        edtEditDateModified = findViewById(R.id.edtEditDateModified)
        edtEditDateCreated = findViewById(R.id.edtEditDateCreated)
    }

    fun addNewRecordOnClick( v : View){
//            val newRecordID = recordsList.size + 1
//            val addRecordItem = RecordsItem(
//                newRecordID,
//                edtEditName.text.toString(),
//                edtEditDescription.text.toString(),
//                edtEditPrice.text.toString().toDouble(),
//                edtEditRating.text.toString().toInt(),
//                edtEditDateModified.toString(),
//                edtEditDateCreated.toString())
//
//            recordsList.add(addRecordItem)
        //Write the edited record to database:
        //Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        //Request a string response from the provided URL.
        val request : StringRequest = object : StringRequest(
            Method.POST,
            baseUrl,
            Response.Listener { response ->
                //display the first 500 characters of the response string.
                Log.i("CRUDapi", "Response is: $response")
            },
            Response.ErrorListener { error ->
                Log.i("CRUDapi", "It no worky - $error")
            }){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String, String> = HashMap()
                params["name"] = edtEditName.text.toString()
                params["description"] = edtEditDescription.text.toString()
                params["price"] = edtEditPrice.text.toString().toDouble().toString()
                params["rating"] = edtEditRating.text.toString().toInt().toString()
                params["createdDate"] = createDate
                params["modifiedDate"] = modifiedDate
                return params
            }
        }

        //Add the request to the RequestQueue.
        request.setShouldCache(false) //this forces information retrieval from the network again and not the volley cache data file in the project.
        queue.add(request)
        //End save to database

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            toastIt("Successfully Added")

    }

    fun showAllRecordsOnClick(v : View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}