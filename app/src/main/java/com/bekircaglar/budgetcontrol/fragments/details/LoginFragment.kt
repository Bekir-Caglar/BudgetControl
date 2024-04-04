package com.bekircaglar.budgetcontrol.fragments.details

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
private lateinit var binding: FragmentLoginBinding
private lateinit var auth: FirebaseAuth
private lateinit var googleSignInClient: GoogleSignInClient



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        requireActivity() as AppCompatActivity
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE

        auth = FirebaseAuth.getInstance()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignin.setOnClickListener{
            val mail = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextNumberPassword.text.toString()

            if (mail.equals("") || pass.equals("")){
                val context = requireContext()
                val message = "Enter Email And Password"
                val duration = Toast.LENGTH_LONG

                Toast.makeText(context, message, duration).show()
            }
            else {
                auth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener {

                    val currentuser = auth.currentUser

                    if (currentuser != null){
                        val act1 = LoginFragmentDirections.actionLoginFragmentToBudgetFragment()
                        Navigation.findNavController(requireView()).navigate(act1)
                    }


                }.addOnFailureListener {

                    Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }




        }
        binding.signupTextButton.setOnClickListener {


            val actionlogintosingup = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            Navigation.findNavController(view).navigate(actionlogintosingup)

        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.clientId))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)



        binding.singinWithGoogle.setOnClickListener{
            signIn()
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){

            println("Current User: " + currentUser.email)
            val act1 = LoginFragmentDirections.actionLoginFragmentToBudgetFragment()
            Navigation.findNavController(requireView()).navigate(act1)
        }
        updateUI(currentUser)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                        val action = LoginFragmentDirections.actionLoginFragmentToNewUserProfile(null,auth.currentUser?.email.toString())
                        Navigation.findNavController(requireView()).navigate(action)




                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    private fun updateUI(user: FirebaseUser?) {

    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }






}