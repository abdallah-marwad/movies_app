package com.example.api.ui.layout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import com.example.api.R
import com.example.api.common.helper.LangUtils
import com.example.api.databinding.ActivityLoginBinding
import com.example.api.ui.layout.MainActivity.USER_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var parentJob: Job
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var binding: ActivityLoginBinding
    private lateinit var arl: ActivityResultLauncher<Intent>
    private lateinit var sharedPreferences :SharedPreferences


    companion object{
        const val REGISTER_STATE : String = "register state"
        const val USER_ID_SHARED : String = "user id"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLang(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        auth = FirebaseAuth.getInstance()
        parentJob = Job()
        coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)
        arlInitial()
        btnRegisterOnClick()
        btnLoggInOnClick()
        btnGoogleSignInOnClick()
        skipOnclick()
    }

    fun checkLang(activity: Activity?) {
        if (Locale.getDefault().displayLanguage != "en") {
            val langUtils = LangUtils()
            langUtils.setLocale(activity, "en")
        }
    }
    private fun skipOnclick(){
        binding.loginSkip.setOnClickListener {
            binding.loginProgressBar.isVisible = true
            loginState(auth.currentUser?.uid)
            checkLoggedState()
        }
    }

    override fun onStart() {
        super.onStart()
       checkLoggedState()
    }
    // region register with user and pass
    private fun btnRegisterOnClick() {
        binding.btnSignup.setOnClickListener {
            binding.loginTxtErr.isVisible = false
            binding.loginProgressBar.isVisible = true

            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            coroutineScope.launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        showToastMsg("Successfully Registration")
                        loginState(auth.currentUser!!.uid)
                        checkLoggedState()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        binding.loginProgressBar.isVisible = false
                        binding.loginTxtErr.isVisible = true
                        binding.loginTxtErr.text = "Error :"+e.message
                    }
                }
            }
        }
    }

    //endregion


    //region Login
    private fun btnLoggInOnClick() {
        binding.btnLogin.setOnClickListener {
            binding.loginProgressBar.isVisible = true
            binding.loginTxtErr.isVisible = false
            loggInUser()
        }
    }

    private fun loggInUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            coroutineScope.launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        showToastMsg("Successfully Login")
                        loginState(auth.currentUser!!.uid)
                        checkLoggedState()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        binding.loginProgressBar.isVisible = false
                        binding.loginTxtErr.isVisible = true
                        binding.loginTxtErr.text = "Error :"+e.message
                    }
                }
            }
        }
    }
    private fun showToastMsg( message :String?){
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG)
            .show()
    }
    //endregion


    //region check Login
//    private fun checkLoggedState() : Boolean{
//        val user = auth.currentUser
//        if (user == null) {
//            if(MainActivity.outFromLogin){
//                finish()
//            }else{
//
//            }
//        } else {
//            if(MainActivity.outFromLogin){
//                finish()
//            }else{
//                auth.signOut()
//
//            }
//            USER_ID =user.uid
//            toMainActivity()
//        }
//        return false
//    }
    private fun checkLoggedState(){
        val authenticated =sharedPreferences.getBoolean(REGISTER_STATE,false)
        USER_ID = sharedPreferences.getString(USER_ID_SHARED,null) // ممكن هنا المشكل
        if(authenticated){
            if(MainActivity.logout){
                finish()
            }
            else{
                toMainActivity()
            }
        }
    }
    private fun loginState(userId: String?){
        with (sharedPreferences.edit()) {
            putBoolean(REGISTER_STATE, true)
            putString(USER_ID_SHARED, userId)
            apply()
        }

    }

    private fun toMainActivity(){
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
    }
    //endregion



    // region signing with google

    private fun arlInitial() {
        arl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data).result
            account?.let {googleAccount->
                googleAuthForFireBase(googleAccount)
            } ?: showToastMsg("Error")

        }

    }
    private fun googleAuthForFireBase(account : GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        coroutineScope.launch {
            try {
                auth.signInWithCredential(credential).await()
                withContext(Dispatchers.Main) {
                    showToastMsg("successfully Logged In")
                    loginState(auth.currentUser!!.uid)
                    checkLoggedState()
                }

            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    binding.loginProgressBar.isVisible = false
                    binding.loginTxtErr.isVisible = true
                    binding.loginTxtErr.text = "Error :"+e.message
                }
            }
        }
    }
    private fun btnGoogleSignInOnClick() {
        binding.btnGoogleLog.setOnClickListener {
            binding.loginTxtErr.isVisible = false
            binding.loginProgressBar.isVisible = true
            googleSignInRequest()
        }
    }

    private fun googleSignInRequest() {
        val option = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webClint_id))
            .requestEmail()
            .build()
        val signInClient = GoogleSignIn.getClient(this, option)
        signInClient.signInIntent.apply {
            arl.launch(this)
        }

    }
    //endregion
    override fun onStop() {
        super.onStop()
        parentJob.cancel()
    }
}


