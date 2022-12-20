package np.pro.dipendra.interview.uilayer.vehicleDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import np.pro.dipendra.interview.collect
import np.pro.dipendra.interview.databinding.FragmentVehicleDetailBinding
import np.pro.dipendra.interview.uilayer.displayImageFrom
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailController.UiAction
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailController.UiAction.InitialView
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailController.UiState
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleItem

@AndroidEntryPoint
class VehicleDetailFragment : DialogFragment() {
    private val viewmodel: VehicleDetailFragmentViewModel by viewModels()
    val args: VehicleDetailFragmentArgs by navArgs()


    private var _binding: FragmentVehicleDetailBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleDetailBinding.inflate(inflater)
        args.vehicleItem
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()

        sendAction(InitialView(args.vehicleItem))
    }

    private fun observeState() {
        viewmodel.uiState.collect(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    //no-op
                }
                is UiState.Body -> setupBody(it.vehicleItem)
            }
        }
    }

    private fun setupBody(vehicleItem: VehicleItem) {
        binding.name.text = vehicleItem.name
        binding.make.text = vehicleItem.make
        binding.model.text = vehicleItem.model
        binding.icon.displayImageFrom(vehicleItem.imageUrl)
    }

    private fun sendAction(uiAction: UiAction) {
        viewmodel.run {
            viewModelScope.launch {
                viewmodel.handleAction(uiAction)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}