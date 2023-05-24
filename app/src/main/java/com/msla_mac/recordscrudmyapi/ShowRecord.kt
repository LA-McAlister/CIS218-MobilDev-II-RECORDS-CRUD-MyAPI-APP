package com.msla_mac.recordscrudmyapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ShowRecord : BaseActivity() {
    lateinit var record : RecordsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_record)

        val txtRecordID: TextView = findViewById(R.id.txtRecordID)
        val txtName: TextView = findViewById(R.id.txtName)
        val txtDescription: TextView = findViewById(R.id.txtDescription)
        val txtPrice: TextView = findViewById(R.id.txtPrice)
        val txtRating: TextView = findViewById(R.id.txtRating)
        val txtDateModified: TextView = findViewById(R.id.txtDateModified)
        val txtDateCreated: TextView = findViewById(R.id.txtDateCreated)

        record = recordsList[ currentRecord ]

        txtRecordID.text = record.recordID.toString()
        txtName.text = record.name
        txtDescription.text = record.description
        txtPrice.text = record.price.toString()
        txtRating.text = record.rating.toString()
        txtDateModified.text = record.dateModified
        txtDateCreated.text = record.dateCreated
    }

    fun deleteRecordOnClick ( v: View){
        val builder = android.app.AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this record?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, which ->
                toastIt("Record: ${record.recordID} is deleted.")
                //delete the element from the list
                recordsList.removeAt(currentRecord)

               // TODO: save to database:
                //start data base           `
                //Instantiate the RequestQueue.
                val queue = Volley.newRequestQueue(this)
                //Request a string response from the provided URL.
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.DELETE,
                    baseUrl + "/${record.recordID}",
                    null, //jsonRequestObject
                    { response ->
                        //display the first 500 characters of the response string.
                        Log.i("CRUDapi", "Response is: ${response.toString()}")
                    },
                    {
                        Log.i("CRUDapi", "It no worky - ${it.message}")
                    })

                //Add the request to the RequestQueue.
                jsonObjectRequest.setShouldCache(false) //this forces information retrieval from the network again and not the volley cache data file in the project.
                queue.add(jsonObjectRequest)
                //End save to database

                //Go to another screen - Show all activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("No"){ dialog, which ->
                toastIt("Delete canceled.")
                dialog.cancel()
            }
            .create().show()
    }

    fun editRecordOnClick( v : View){
        val intent = Intent(this, EditRecord::class.java)
        startActivity(intent)
    }

    fun showAllRecordsOnClick(v : View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    } // End of showAll onClick
}