package dev.joshhalvorson.jobapptracker.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dev.joshhalvorson.jobapptracker.BuildConfig
import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.component
import dev.joshhalvorson.jobapptracker.factory.MainActivityViewModelFactory
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var factory: MainActivityViewModelFactory

    companion object {
        const val TAG = "signInActivity"
        const val RC_SIGN_IN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        component.inject(this)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.client_id)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        checkLogIn(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(
            signInIntent,
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                sign_in_card_view.visibility = View.GONE
                sign_in_progress_bar.visibility = View.VISIBLE
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    user?.uid?.let {
                        viewModel.addApplication(
                            it, "init", Application(
                                "init", "init",
                                response = false,
                                firstInterview = false,
                                secondInterview = false,
                                thirdInterview = false,
                                offer = false
                            )
                        )
                    }
                    checkLogIn(user)
                } else {
                    // If sign in fails, display a message to the user.
                    sign_in_card_view.visibility = View.VISIBLE
                    sign_in_progress_bar.visibility = View.GONE
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                    checkLogIn(null)
                }
            }
    }

    private fun checkLogIn(user: FirebaseUser?) {
        if (user != null) {
            Log.i(TAG, user.uid)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        } else {
            sign_in_card_view.visibility = View.VISIBLE
            sign_in_progress_bar.visibility = View.GONE
            Log.i(TAG, "Signed out")
        }
    }

}
