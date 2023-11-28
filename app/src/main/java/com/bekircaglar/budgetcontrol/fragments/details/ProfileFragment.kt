package com.bekircaglar.budgetcontrol.fragments.details

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.fragment.app.viewModels
import com.bekircaglar.budgetcontrol.R
import com.bekircaglar.budgetcontrol.databinding.FragmentProfileBinding
import com.bekircaglar.budgetcontrol.model.AccountsMoney
import com.bekircaglar.budgetcontrol.model.UserData
import com.bekircaglar.budgetcontrol.viewmodel.BudgetFragmentViewModel
import com.bekircaglar.budgetcontrol.viewmodel.ProfileFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale

@AndroidEntryPoint

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var activityResultLanuncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: ProfileFragmentViewModel
    private var userDataList = ArrayList<UserData>()
    private lateinit var encodedImage: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)


        return binding.root

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ProfileFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.userDataList.observe(viewLifecycleOwner){
            userDataList = it as ArrayList<UserData>
            binding.userName.text = userDataList[0].user_name
            binding.userMail.text = userDataList[0].user_email
            encodedImage = userDataList[0].user_image

            val decodedString: ByteArray = Base64.getDecoder().decode(encodedImage)
            if (decodedString != null) {
                val decodedByte: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.userImage.setImageBitmap(decodedByte)
            }
            else{
                binding.userImage.setImageResource(R.drawable.user)
            }

            binding.enterUsername.setText(userDataList[0].user_name)
            binding.enterPhone.setText(userDataList[0].user_phone)
            binding.enterEmail.setText(userDataList[0].user_email)
            binding.enterDob.setText(userDataList[0].user_dob)
        }

        binding.applyChange.setOnClickListener {
            // Yeni kullanıcı bilgilerini al
            val newUsername = binding.enterUsername.text.toString()
            val newUserEmail = binding.enterEmail.text.toString()
            val newUserPhone = binding.enterPhone.text.toString()
            val newUserDob = binding.enterDob.text.toString()

            // Kullanıcı bilgilerini güncelle
            viewModel.updateUser(newUsername, newUserEmail,newUserPhone,encodedImage,newUserDob)
            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_LONG).show()
        }

        registerLauncher()

        binding.userImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)) {
                        Snackbar.make(binding.root, "Need Permission", Snackbar.LENGTH_INDEFINITE
                        ).setAction("Give Permission",View.OnClickListener {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                        }).show()


                    }
                    else {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)

                    }
                }
                else {
                    val intenttogallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLanuncher.launch(intenttogallery)


                }


            } else {

                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Snackbar.make(
                            binding.root,
                            "Need Permission",
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("Give Permission", View.OnClickListener {
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
    }


    private fun registerLauncher() {
        activityResultLanuncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageData = intentFromResult.data
                    imageData?.let {
                        binding.userImage.setImageURI(it)

                        val inputStream: InputStream? = requireActivity().contentResolver.openInputStream(it)
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                        encodedImage = Base64.getEncoder().encodeToString(byteArray)



                    }
                }
            }


        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if (result) {
                val intenttogallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLanuncher.launch(intenttogallery)


            } else {
                Toast.makeText(requireContext(), "Need Permission", Toast.LENGTH_LONG)
            }

        }



    }



}


