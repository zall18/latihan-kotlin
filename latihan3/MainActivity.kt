package com.example.latihan3

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fistInput: EditText = findViewById(R.id.first_input)
        val password: EditText = findViewById(R.id.password_input)
        val login: AppCompatButton = findViewById(R.id.login_button)
        val signup: TextView = findViewById(R.id.signUp_text)
        val connection: Connection = Connection()
        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = session.edit()

        signup.setOnClickListener {
            startActivity(Intent(applicationContext, SignUp::class.java))
        }

        login.setOnClickListener {
            if(fistInput.text.length == 0){
                fistInput.setError("This field is important!")
            }else if(password.text.length == 0){
                password.setError("this field is important!")
            }else{
//                startActivity(Intent(applicationContext, BottomNav::class.java))


                var jsonObject = JSONObject().apply {
                    put("username", fistInput.text.toString())
                    put("password", password.text.toString())
                }

                lifecycleScope.launch {

                    val result = postrequest(connection.Connection + "api/login", jsonObject, null)

                    result.fold(
                        onSuccess = {
                            response ->

                            val jsonObject2 = JSONObject(response)

                            if(jsonObject2.getString("token").isNotEmpty()){
                                var jsonObject3 = JSONObject(jsonObject2.getString("user"))
                                editor.putString("token", jsonObject2.getString("token"))
                                editor.putString("id", jsonObject3.getString("id"))
                                editor.commit()


                                Toast.makeText(applicationContext, "Login Success!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(applicationContext, BottomNav::class.java))
                            }else{
                                Toast.makeText(applicationContext, "The fields not match", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onFailure = {
                            error ->
                            println(error)
                        }


                    )

                }
/**/
            }
        }
    }
}