package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var taskslist = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                taskslist.removeAt(position)
                // notify the adapter of the change
                adapter.notifyDataSetChanged()
                saveItems()
            }

        }
        loadItems()
        //look at the recyview
        val RecyView = findViewById<RecyclerView>(R.id.recyview)
        adapter = TaskItemAdapter(taskslist, onLongClickListener)
        RecyView.adapter = adapter
        RecyView.layoutManager = LinearLayoutManager(this)
        val inputText = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener {
            // graph the text
            val userinputtask = inputText.text.toString()
            // add the string to the list
            taskslist.add(userinputtask)
            //notify the adapter
            adapter.notifyItemInserted(taskslist.size - 1)
            //reset the text field
            inputText.setText("")
            saveItems()
        }
    }
    //save the input data
    // save the data by writing and reading
    // create a method to get the file we need
    fun getDataFile() : File {
        // Every line is a task in the file
        return File(filesDir,"data.txt")
    }
    // Load the items by reading every file in the line
    fun loadItems(){
        try {
            taskslist = org.apache.commons.io.FileUtils.readLines(getDataFile())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
    // Save by writing
    fun saveItems(){
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), taskslist)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}