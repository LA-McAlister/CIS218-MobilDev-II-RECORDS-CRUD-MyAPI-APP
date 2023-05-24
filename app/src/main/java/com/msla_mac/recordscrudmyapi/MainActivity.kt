package com.msla_mac.recordscrudmyapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import javax.xml.transform.ErrorListener
import kotlin.random.Random

class MainActivity : BaseActivity() {
    lateinit var recyclerView : RecyclerView
    private lateinit var recordsListAdapter: RecordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recordsRecycler)
        recordsListAdapter = RecordsAdapter(recordsList) { position ->
            toastIt("You selected position: $position")

            //perform whatever you want onclick
            val intent = Intent(this, ShowRecord::class.java)
            currentRecord = position
            startActivity(intent)
        }


        //toastIt("Created list")

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = recordsListAdapter


        recordsListAdapter.notifyDataSetChanged()

        ///Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


        ///Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            baseUrl,
            null, //jsonRequestObject
            { response ->

                for (i in 0 until response.length()) {
                    val record: JSONObject = response.getJSONObject(i)
                    val id = record.getInt("id")
                    val name = record.getString("name")
                    val description = record.getString("description")
                    val price = record.getDouble("price")
                    val rating = record.getInt("rating")
                    val dateCreated = record.getString("created_at")
                    val dateModified = record.getString("updated_at")
                    val recordsItem = RecordsItem(id, name, description, price, rating, dateModified, dateCreated)
                    recordsList.add(recordsItem)
                }
                recordsListAdapter.notifyDataSetChanged()

                //display the first 500 characters of the response string.
                Log.i("CRUDapi", "Response is: ${response.toString()}")
            },
            {
                Log.i("CRUDapi", "It no worky - ${it.message}")
            })

        //Add the request to the RequestQueue.
        stringRequest.setShouldCache(false) //this forces information retrieval from the network again and not the volley cache data file in the project.
        queue.add(stringRequest)
    }

    fun editRecordOnClick(v : View) {
        val intent = Intent(this, EditRecord::class.java)
        startActivity(intent)
    } // End of editEvent onClick

    fun addRecordOnClick( v : View ) {
        val intent = Intent(this, AddRecord::class.java)
        startActivity(intent)
    }

    fun addMockDataOnClick( v : View) {
//        for (i in 1..41) {
//            val rating = (1..5).random()
//            val price = Random.nextDouble(999.99)
//            val recordsItem = RecordsItem(i,"Meeting $i", "Very long meeting", price, rating, "19 April 2023", "19 March 2023")
//            recordsList.add(recordsItem)
//        }
//
//        recordsListAdapter.notifyDataSetChanged()
    }

    fun scrollToTopOnClick(v : View) {
        recyclerView.smoothScrollToPosition(0)
    }

    fun scrollToBottomOnClick(v : View) {
        recyclerView.smoothScrollToPosition(recordsList.size)
    }

    fun clearAllDataOnClick(view : View) {
//        deleteRecordsFile()
    }

}