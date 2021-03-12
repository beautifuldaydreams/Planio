package com.example.camera.presentation

//import com.example.camera.di.DaggerCameraComponent

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.camera.BitmapHelper
import com.example.camera.R
import com.example.camera.databinding.FragmentCameraBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import com.example.storage.data.PlantPhoto
import kotlinx.android.synthetic.main.fragment_camera.*
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment () : Fragment(){

    val TAG = "CameraFragment"

    private lateinit var binding: FragmentCameraBinding

    private val viewModel: CameraViewModel by viewModels()

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null

    private lateinit var safeContext: Context

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    lateinit var imageMat: Mat

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = safeContext.resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
        return if (resourceId > 0) {
            safeContext.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)

        binding.mainScreen.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.HomeFlow)
        }

        binding.viewModel = viewModel
        binding.cameraRecyclerview.adapter = CameraAdapter(CameraAdapter.OnClickListener {
            viewModel.onSelectForPreview(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        OpenCVLoader.initDebug()
        // Request camera permissions
        if (allPermissionsGranted()) {
            //Todo: If laggy add coroutine here and make startCamera a suspend function
            startCamera()
        } else {
            requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Setup the listener for take photo button
        camera_capture_button.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
//        cameraExecutor = Executors.newCachedThreadPool()

        viewModel.selectForPreview.observe(viewLifecycleOwner, {
            if (null != it) {
                Log.d(TAG, "in selectForPreview Observer")
                mImageToImageWithEdge()
                viewModel.selectForPreviewComplete()
                Log.d(TAG, " selectForPreviewComplete() is \"complete\"")
            }
        })
    }

    private fun startCamera() {
//        OpenCVLoader.initDebug()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            Log.d(TAG, "before preview1")
            preview = Preview.Builder().build()
            Log.d(TAG, "after preview1")

            imageCapture = ImageCapture.Builder().build()

//            imageAnalyzer = ImageAnalysis.Builder().build().apply {
//                setAnalyzer(Executors.newSingleThreadExecutor(), CornerAnalyzer {
//                    val bitmap = viewFinder.bitmap
//                    val img = Mat()
//                    Utils.bitmapToMat(bitmap, img)
//                    bitmap?.recycle()
//
//                    val corner = processPicture(img)
//                    // Image analysis here
//                })
//            }
            // Select back camera
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider())
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(safeContext))

    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create timestamped output file to hold the image
        val photoFile = File(
            outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(
                System.currentTimeMillis()
            ) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Setup image capture listener which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(safeContext),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Log.d(TAG, "File: $photoFile")
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(safeContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                    viewModel.getNewSpIdNumber()?.toInt()
                        ?.let { viewModel.saveImage(it, photoFile, 1) }
                    viewModel.editSpIdNumber()
                }
            })
    }

    override fun onPause() {
        super.onPause()
        isOffline = true
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    safeContext,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
//                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun getOutputDirectory(): File {
        val mediaDir = activity?.getExternalFilesDirs(null)?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }


    companion object {
        val TAG = "CameraXFragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        internal const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        var isOffline = false // prevent app crash when goes offline
    }

    fun deselectRemainingList(position: Int, recyclerView: RecyclerView) {
        val itemCount = recyclerView.adapter?.itemCount
        for (i in 0 until itemCount!!) {
            if (i != position){
                val holder = recyclerView.findViewHolderForAdapterPosition(i)
//                !holder?.itemView?.plantSelectedView?.isVisible!!
            }
        }
    }

    private fun mImageToImageWithEdge() {
        Log.d(TAG, "in mImageToImageWithEdge()")
        val toBeEdgeDetected: File?

        try {
                toBeEdgeDetected = (context?.getExternalFilesDir("planio/dataclasses/1")
                    ?.listFiles()?.toMutableList() ?: mutableListOf()).last()
            }catch (e: Exception) {
                //todo: create a "Directory not found" message in the UI to notify user
                Log.i("OnCreate", "planio/dataclasses/0 directory not found.")
                return
            }


        Log.d(TAG, "${toBeEdgeDetected.absoluteFile}")
        Log.d(TAG, toBeEdgeDetected.absolutePath)
        Glide.with(binding.edgeDetectionView.context).load(toBeEdgeDetected).into(binding.edgeDetectionView)

        val file = FileInputStream(toBeEdgeDetected)
        val inStream = ObjectInputStream(file)
        val item = inStream.readObject() as PlantPhoto

        val bit = BitmapFactory.decodeFile(item.plantFilePath.toString())
        detectEdges(bit)
    }

    private fun detectEdges(image: Bitmap) {

        val src = Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC4)
        Utils.bitmapToMat(image, src)
        val edges = Mat(src.size(), CvType.CV_8UC1)

        Imgproc.cvtColor(src, edges, Imgproc.COLOR_BGRA2GRAY)
        Imgproc.Canny(edges, edges, 80.0, 100.0)

        val src1 = edges.clone()
        val src2 = edges.clone()
        val alpha = Mat(image.height, image.width, CvType.CV_8UC1)

        Imgproc.threshold(edges, alpha, 155.0, 0.0, Imgproc.THRESH_BINARY_INV);

        val dst = Mat(image.height, image.width, CvType.CV_8UC4)
//        val listMat = listOf(edges, src1, src2, alpha)
//        Core.merge(listMat, dst)

        val rgba = mutableListOf<Mat>()
        rgba.add(edges)
        rgba.add(src1)
        rgba.add(src2)
        rgba.add(alpha)
        Core.merge(rgba, dst)

        val invertcolormatrix = Mat(image.height, image.width, CvType.CV_8UC4, Scalar(255.0, 255.0, 255.0, 255.0))

        Core.subtract(invertcolormatrix, dst, dst)


        val output =
            Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(dst, output)

        BitmapHelper.showBitmap(safeContext, output, binding.edgeDetectionView)

        Log.d(TAG, "finished binding")
    }

//    private class CornerAnalyzer(private val listener: CornersListener) : ImageAnalysis.Analyzer {
//
//        private fun ByteBuffer.toByteArray(): ByteArray {
//            rewind()    // Rewind the buffer to zero
//            val data = ByteArray(remaining())
//            get(data)   // Copy the buffer into a byte array
//            return data // Return the byte array
//        }
//
//        @SuppressLint("UnsafeExperimentalUsageError")
//        override fun analyze(imageProxy: ImageProxy) {
//            if (!isOffline) {
//                listener()
//            }
//            imageProxy.close() // important! if it is not closed it will only run once
//        }
//    }

    override fun onResume() {
        super.onResume()

        val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(safeContext) {
            override fun onManagerConnected(status: Int) {
                when (status) {
                    LoaderCallbackInterface.SUCCESS -> {
                        Log.i("OpenCV", "OpenCV loaded successfully")
                        imageMat = Mat()
                    }
                    else -> {
                        super.onManagerConnected(status)
                    }
                }
            }
        }

        isOffline = false
        if (!OpenCVLoader.initDebug()) {
            Log.d(
                "OpenCV",
                "Internal OpenCV library not found. Using OpenCV Manager for initialization"
            )
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, safeContext, mLoaderCallback)
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

}