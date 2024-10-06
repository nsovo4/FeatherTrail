package com.example.birdviewapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class BirdsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var galleryButton: ImageButton
    private lateinit var cameraButton: ImageButton
    private lateinit var imageView: ImageView  // To preview the image

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_birds, container, false)

        // Initialize views
        galleryButton = view.findViewById(R.id.galleryBtn)
        cameraButton = view.findViewById(R.id.cameraBtn)
        imageView = view.findViewById(R.id.imageView)  // ImageView to show selected image

        // Set up Gallery and Camera Intents
        setupGalleryLauncher()
        setupCameraLauncher()

        // Set up button listeners
        galleryButton.setOnClickListener {
            openGallery()
        }

        cameraButton.setOnClickListener {
            openCamera()
        }

        return view
    }

    // Function to open the gallery
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    // Function to launch the camera
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    // Setup Gallery Launcher to handle result
    private fun setupGalleryLauncher() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        val imageUri: Uri? = data.data
                        if (imageUri != null) {
                            try {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().contentResolver,
                                    imageUri
                                )
                                imageView.setImageBitmap(bitmap)  // Display the image
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
    }

    // Setup Camera Launcher to handle result
    private fun setupCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)  // Display the captured image
                }
            }
    }

}