package com.example.camera.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.camera.BitmapHelper
import com.example.camera.R
import com.example.camera.databinding.FragmentCameraBinding
import com.example.camera.databinding.ToastBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import com.example.storage.data.PlantIndividual
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

class CameraFragment : Fragment(){

    val TAG = "BUG"

    private lateinit var binding: FragmentCameraBinding
    private lateinit var binding2: ToastBinding
    private val viewModel: CameraViewModel by viewModels()

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null

    private lateinit var safeContext: Context
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    lateinit var imageMat: Mat

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        binding2 = DataBindingUtil.inflate(inflater, R.layout.toast, container, false)
        binding.lifecycleOwner = this
        binding.mainScreen.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.HomeFlow)
        }

        binding.cameraRecyclerview.adapter = CameraAdapter(CameraAdapter.OnClickListener {
            viewModel.onSelectForPreview(it)
        })
        binding.viewModel = viewModel
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
        viewModel.selectForPreviewComplete()

        camera_capture_button.setOnClickListener {
            try {
                if (viewModel.selectForPreview.value == null) {
                    throw java.lang.NullPointerException()
                }
                takePhoto()
            } catch (e: NullPointerException) {
                val msg = "Please select plant below before taking a picture"
                Toast.makeText(safeContext, msg, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        viewModel.selectForPreview.observe(viewLifecycleOwner, {
            val id: Int? = it?.plantId
            val toBeEdgeDetected = context?.getExternalFilesDir("planio/dataclasses")
            val specificFile: MutableList<File?>? = (File(toBeEdgeDetected, "$id")
                .listFiles()?.toMutableList() ?: mutableListOf())
            binding.edgeDetectionView.translationZ = 0F
            if (null != it && !specificFile?.isEmpty()!!) {
                binding.edgeDetectionView.translationZ = 5.0F
                mImageToImageWithEdge(it)
            } else {
                viewModel.onEdgeDetectionNull()
                Log.i(TAG, "edgeDetectionImage.value if blank plant = ${viewModel.edgeDetectionImage.value}")
            }
        })
        viewModel.edgeDetectionImage.observe(viewLifecycleOwner, {
            if (viewModel.edgeDetectionImage.value != null) {
                BitmapHelper.showBitmap(safeContext, it, binding.edgeDetectionView)
            } else{
                binding.edgeDetectionView.setImageResource(android.R.color.transparent)
            }
        })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            preview = Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider())
            } catch (exc: Exception) {
            }
        }, ContextCompat.getMainExecutor(safeContext))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(
                System.currentTimeMillis()
            ) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(safeContext),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {}
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo Saved"
                    Toast.makeText(safeContext, msg, Toast.LENGTH_SHORT).show()
                    viewModel.saveImage(photoFile)
                    binding.cameraRecyclerview.adapter?.notifyDataSetChanged()
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

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.getExternalFilesDirs(null)?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else activity?.filesDir!!
    }


    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        internal const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        var isOffline = false // prevent app crash when goes offline
    }

    private fun mImageToImageWithEdge(plantIndividual: PlantIndividual) {
        val toBeEdgeDetected: File?

        val id = plantIndividual.plantId
        val specificFile: File?

        try {
                toBeEdgeDetected = context?.getExternalFilesDir("planio/dataclasses")
                specificFile = (File(toBeEdgeDetected, "$id")
                    .listFiles()?.toMutableList() ?: mutableListOf()).last()
            }catch (e: Exception) {
                //todo: create a "Directory not found" message in the UI to notify user
                Log.i("OnCreate", "planio/dataclasses/0 directory not found.")
                return
            }

        val file = FileInputStream(specificFile)
        val inStream = ObjectInputStream(file)
        val item = inStream.readObject() as PlantPhoto
        val bit = BitmapFactory.decodeFile(item.plantFilePath.toString())
        detectEdges(bit)

    }

    private fun detectEdges(image: Bitmap) {
        val src = Mat(image.height, image.width, CvType.CV_8UC4)
        Utils.bitmapToMat(image, src)
        val edges = Mat(src.size(), CvType.CV_8UC1)
        Imgproc.cvtColor(src, edges, Imgproc.COLOR_BGRA2GRAY)
        Imgproc.Canny(edges, edges, 80.0, 100.0)

        val src1 = edges.clone()
        val src2 = edges.clone()
        val alpha = Mat(image.height, image.width, CvType.CV_8UC1)

        Imgproc.threshold(edges, alpha, 155.0, 0.0, Imgproc.THRESH_BINARY_INV)
        val dst = Mat(image.height, image.width, CvType.CV_8UC4)
        val rgba = mutableListOf<Mat>()
        rgba.add(edges)
        rgba.add(src1)
        rgba.add(src2)
        rgba.add(alpha)
        Core.merge(rgba, dst)
        val invertcolormatrix = Mat(
            image.height, image.width, CvType.CV_8UC4, Scalar(
                255.0,
                255.0,
                255.0,
                255.0
            )
        )
        Core.subtract(invertcolormatrix, dst, dst)
        val output =
            Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(dst, output)
        viewModel.onEdgeDetection(output)
//        BitmapHelper.showBitmap(safeContext, output, binding.edgeDetectionView)
    }

    override fun onResume() {
        super.onResume()
        val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(safeContext) {
            override fun onManagerConnected(status: Int) {
                when (status) {
                    LoaderCallbackInterface.SUCCESS -> {
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
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, safeContext, mLoaderCallback)
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }
}