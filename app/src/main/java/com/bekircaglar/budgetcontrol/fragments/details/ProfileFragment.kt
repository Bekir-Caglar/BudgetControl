package com.bekircaglar.budgetcontrol.fragments.details

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentProfileBinding
import com.bekircaglar.budgetcontrol.model.UserData
import com.bekircaglar.budgetcontrol.viewmodel.ProfileFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var activityResultLanuncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: ProfileFragmentViewModel
    private var userDataList = ArrayList<UserData>()
    private lateinit var encodedImage: String
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()

        return binding.root

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProfileFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCalendar = Calendar.getInstance()

        //get arguments from signup fragment
        val username = arguments?.getString("username")
        val usermail = arguments?.getString("userEmail")

        viewModel.userDataList.observe(viewLifecycleOwner) {

            userDataList = it as ArrayList<UserData>

            if (userDataList.isEmpty()) {
                binding.enterUsername.setText(username)
                binding.enterEmail.setText(usermail)
                binding.enterPhone.setHint("Enter Phone")
                binding.enterDob.setHint("Enter Date of Birth")
                binding.userMail.text = usermail
                binding.userName.text = username
                binding.userImage.setImageResource(R.drawable.user)
            }
            else {
                binding.userName.text = userDataList[0].user_name
                binding.userMail.text = userDataList[0].user_email
                encodedImage = userDataList[0].user_image

                val decodedString: ByteArray = Base64.getDecoder().decode(encodedImage)
                val decodedByte: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                binding.userImage.setImageBitmap(decodedByte)
                binding.enterUsername.setText(userDataList[0].user_name)
                binding.enterPhone.setText(userDataList[0].user_phone)
                binding.enterEmail.setText(userDataList[0].user_email)
                binding.enterDob.setText(userDataList[0].user_dob)
            }
        }


        binding.applyChange.setOnClickListener {
            val newUsername = binding.enterUsername.text.toString()
            val newUserEmail = binding.enterEmail.text.toString()
            val newUserPhone = binding.enterPhone.text.toString()
            val newUserDob = binding.enterDob.text.toString()


            try {
                val bitmap: Bitmap = binding.userImage.drawToBitmap() // Var olan resmi al
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                encodedImage = Base64.getEncoder().encodeToString(byteArray) // Resmi encode et

            }
            catch (e:Exception){
                e.printStackTrace()
                binding.userImage.setImageResource(R.drawable.user)
            }
            val bitmap: Bitmap = binding.userImage.drawToBitmap() // Var olan resmi al
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val encodedImage: String = Base64.getEncoder().encodeToString(byteArray) // Resmi encode et


            if (newUsername.isEmpty() || newUserEmail.isEmpty() || newUserPhone.isEmpty() || newUserDob.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.updateUser(newUsername,newUserPhone,encodedImage,newUserDob)
                Toast.makeText(requireContext(), "User Data Updated", Toast.LENGTH_LONG).show()
                val action = ProfileFragmentDirections.actionProfileFragmentToMorePageFragment()
                Navigation.findNavController(view).navigate(action)

            }


        }

        registerLauncher()

        binding.userImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.READ_MEDIA_IMAGES
                        )
                    ) {
                        Snackbar.make(
                            binding.root, "Need Permission", Snackbar.LENGTH_INDEFINITE
                        ).setAction("Give Permission") {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                        }.show()


                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                    }
                } else {
                    val intenttogallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLanuncher.launch(intenttogallery)


                }


            } else {

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {
                        Snackbar.make(
                            binding.root,
                            "Need Permission",
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("Give Permission", {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                        }).show()


                    } else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                    }
                } else {
                    val intenttogallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLanuncher.launch(intenttogallery)


                }
            }
        }

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updatelabel(myCalendar)
        }


        binding.linearLayout7.setOnClickListener {

            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()


        }


    }
    private fun updatelabel(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.enterDob.setText(sdf.format(myCalendar.time))
    }

    private fun registerLauncher() {
        activityResultLanuncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        val imageData = intentFromResult.data
                        imageData?.let {
                            binding.userImage.setImageURI(it)

                            val inputStream: InputStream? =
                                requireActivity().contentResolver.openInputStream(it)
                            val bitmap = BitmapFactory.decodeStream(inputStream)

                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(
                                Bitmap.CompressFormat.PNG,
                                100,
                                byteArrayOutputStream
                            )
                            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                            encodedImage = Base64.getEncoder().encodeToString(byteArray)


                        }
                    }
                }


            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intenttogallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLanuncher.launch(intenttogallery)


                } else {
                    Toast.makeText(requireContext(), "Need Permission", Toast.LENGTH_LONG).show()
                }

            }
    }
}







