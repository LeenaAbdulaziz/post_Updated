package com.example.post_updated

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Update_delete : AppCompatActivity() {
    lateinit var user_id : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)
        val name = findViewById<View>(R.id.editTextTextPersonName) as EditText
        val location = findViewById<View>(R.id.editTextTextPersonName2) as EditText
        user_id = findViewById<View>(R.id.editTextTextPersonName3) as EditText
        val updatebtn = findViewById<View>(R.id.update_button) as Button
        val deletebtn = findViewById<View>(R.id.delete_button) as Button
        val backbtn = findViewById<View>(R.id.back_button) as Button


        updatebtn.setOnClickListener {

            var f = Users.UserDetails(name.text.toString(), location.text.toString(),(user_id.text.toString().toInt()))

            addSingleuser(f, onResult = {
                name.setText("")
                location.setText("")
                //user_id.setText(0)
                Toast.makeText(applicationContext, "update Success!", Toast.LENGTH_SHORT).show();

            })
        }
        deletebtn.setOnClickListener {


            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val progressDialog = ProgressDialog(this@Update_delete)
            progressDialog.setMessage("Please wait")
            progressDialog.show()

            if (apiInterface != null) {
                apiInterface.deleteUser((user_id.text.toString().toInt()))?.enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        Toast.makeText(applicationContext, "Delete Sucess!", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss()

                    }
                })
            }

        }
    }


    private fun addSingleuser(f: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@Update_delete)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.updateUser((user_id.text.toString().toInt()),f)?.enqueue(object : Callback<Users.UserDetails> {
                override fun onResponse(
                    call: Call<Users.UserDetails>,
                    response: Response<Users.UserDetails>
                ) {
                    onResult()
                    progressDialog.dismiss()

                }

                override fun onFailure(call: Call<Users.UserDetails>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
        else{
            Log.e("asdfjlkhf", "addSingleuser: ")
            Toast.makeText(applicationContext, "API error", Toast.LENGTH_SHORT).show();
        }
    }


    fun delete(view: android.view.View) {
        intent = Intent(applicationContext, Update_delete::class.java)
        startActivity(intent)

    }

    fun update(view: android.view.View) {
        intent = Intent(applicationContext, Update_delete::class.java)
        startActivity(intent)


    }

    fun back(view: View) {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}

