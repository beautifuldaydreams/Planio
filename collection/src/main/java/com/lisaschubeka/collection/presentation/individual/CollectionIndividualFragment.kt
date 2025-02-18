package com.lisaschubeka.collection.presentation.individual

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lisaschubeka.collection.R
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lisaschubeka.collection.databinding.FragmentCollectionIndividualBinding
import com.lisaschubeka.collection.presentation.overview.CollectionOverviewViewModel
import com.lisaschubeka.collection.presentation.popup.EditPopupFragment
import com.lisaschubeka.collection.presentation.popup.PopupFragment
import com.lisaschubeka.storage.SharedPreferences.Companion.editSpPlantName
import com.lisaschubeka.storage.SharedPreferences.Companion.getNewSpIdNumber
import com.lisaschubeka.storage.data.PlantPhoto
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.File


class CollectionIndividualFragment: Fragment(), EditPopupFragment.OnInputSelected {

    private lateinit var binding: FragmentCollectionIndividualBinding

    val context = this
    private lateinit var dialog: EditPopupFragment

    private var currentBitmap: Bitmap? =null
    private val viewModel: CollectionOverviewViewModel by activityViewModels()
    var imgUrl : File? = null
    private lateinit var safeContext: Context
    private var plantName: String? = null
    private var plantId: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var imgPhoto: PlantPhoto? = null

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_collection_individual,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.toCollectionOverview.setOnClickListener {
            this.findNavController().navigate(
                CollectionIndividualFragmentDirections.actionCollectionIndividualFragmentToCollectionOverviewFragment()
            )
            viewModel.displayPlantDetailsComplete()
        }

        plantName = viewModel.navigateToSelectedPlant.value?.plantName
        plantId = viewModel.navigateToSelectedPlant.value?.plantId.toString()

        binding.button.text = plantId?.let { getNewSpIdNumber(it + "a", safeContext, plantName!!) }
        Log.i("check", "onCreateView:  New:${plantId?.let { getNewSpIdNumber(it + "a", safeContext, plantName!!) }}")

        binding.button.setOnClickListener{
            dialog = EditPopupFragment()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager, "editPopupFragment")
        }

        binding.collectionIndividualRecyclerview.adapter =
            CollectionIndividualAdapter((CollectionIndividualAdapter.OnClickListener {
                viewModel.displayPlantPhoto(it)
                imgPhoto = it
            }))

        binding.plantPhoto = viewModel.plantPhotoDisplay.value

        binding.collectionIndividualRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val myLayoutManager: LinearLayoutManager =
                    binding.collectionIndividualRecyclerview.layoutManager as LinearLayoutManager
                val scrollPosition = myLayoutManager.findFirstVisibleItemPosition()
                imgPhoto = viewModel.newPhotoList[scrollPosition]
                imgUrl = viewModel.newPhotoList[scrollPosition].plantFilePath
                imgUrl.let {
                    Glide.with(binding.collectionIndividualImageview)
                        .load(imgUrl)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_launcher_foreground)
                        )
                        .into(binding.collectionIndividualImageview)
                }
            }
        })
        binding.saveImage.setOnClickListener {
            currentBitmap = BitmapFactory.decodeFile(viewModel.plantPhotoDisplay.value?.plantFilePath.toString())
            if (allPermissionsGranted()) {
                viewModel.saveMediaToStorage(currentBitmap)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
        val deleteList = resources.getStringArray(R.array.delete_array)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, deleteList
        )

        val plantIndividual =
            CollectionIndividualFragmentArgs.fromBundle(requireArguments()).selectedPlant
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if (position == 1) {
                    viewModel.deleteSelectedPlantIndividual(plantIndividual)
                    binding.spinner.setSelection(0)
                    context.findNavController().navigate(
                        CollectionIndividualFragmentDirections.actionCollectionIndividualFragmentToCollectionOverviewFragment()
                    )
                }
                if (position == 2) {
                    imgPhoto?.let { viewModel.deleteSelectedPlantPhoto(it) }
                    viewModel.retrievePlantList(plantIndividual)
                    viewModel.changeToPlantPhotos(viewModel.mediaPlantList)
                    binding.spinner.setSelection(0)
                    binding.collectionIndividualRecyclerview.adapter?.notifyDataSetChanged()
                    val num = viewModel.newPhotoList.size - 1
                    imgUrl = viewModel.newPhotoList[num].plantFilePath
                    imgUrl.let {
                        Glide.with(binding.collectionIndividualImageview)
                            .load(imgUrl)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.loading_animation)
                                    .error(R.drawable.ic_launcher_foreground)
                            )
                            .into(binding.collectionIndividualImageview)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.viewModel = viewModel

        return binding.root
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(safeContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewModel.saveMediaToStorage(currentBitmap)
            } else {
                Toast.makeText(safeContext,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        internal const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun sendInput(input: String) {
        val notEditedPlantName = plantId?.let { getNewSpIdNumber(it + "a", safeContext, input) }
        if (notEditedPlantName != null) {
            editSpPlantName(plantId+ "a", safeContext, input)
        }
    }

    fun onChangeText() {
        binding.button.text = plantId?.let { getNewSpIdNumber(it + "a", safeContext, plantName!!) }
    }
}