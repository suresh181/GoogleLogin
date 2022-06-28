package com.my.googlelogin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_logged.*

class LoggedActivity : AppCompatActivity() {
    private lateinit var signOut:Button
    private var mGoogleSignInClient : GoogleSignInClient? = null

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, LoggedActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
        signOut = findViewById(R.id.sign_out_button)
        initializeUI()
        initView()

    }

    private fun initView(){
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        if(acct != null) {
            val personName = acct.displayName
            tv_name.text = personName
            val personEmail = acct.email
            tv_email.text = personEmail
            val personId = acct.id
            tv_id.text = personId
            var personImage = acct.photoUrl
            Glide.with(this).load(personImage).into(iv_profile)
        }
    }

    private fun initializeUI() {
        signOut.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this, object:OnCompleteListener<Void>{
            override fun onComplete(p0: Task<Void>) {
                Toast.makeText(this@LoggedActivity, "Signed Out", Toast.LENGTH_LONG).show()
            }
        })
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}