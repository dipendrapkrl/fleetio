package np.pro.dipendra.interview.uilayer.vehiclelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import np.pro.dipendra.interview.collect
import np.pro.dipendra.interview.databinding.FragmentVehicleListBinding
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.UiAction
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.UiEvent
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.UiState.*

@AndroidEntryPoint
class VehicleListFragment : Fragment() {

    private val viewmodel: VehicleListFragmentViewModel by viewModels()

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VehicleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListeners()
        observeStates()
        observeActions()
        observePagingState()
        sendAction(UiAction.InitialView)
    }

    private fun setupClickListeners() {
        binding.errorLayout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewmodel.handleAction(UiAction.Retry)
            }
        }
    }

    private fun sendAction(uiAction: UiAction) {
        viewmodel.run {
            viewModelScope.launch {
                viewmodel.handleAction(uiAction)
            }
        }
    }

    private fun setupViews() {
        adapter = VehicleListAdapter {
            Log.v("tag", "item click")
            sendAction(UiAction.ItemClick(it))
        }
        binding.recyclerView.adapter =
            adapter.withLoadStateFooter(VehicleLoadStateAdapter {
                sendAction(UiAction.Retry)
            })
        adapter.addLoadStateListener { sendAction(UiAction.LoadStateChanged(it)) }
    }


    private fun observeActions() {
        viewmodel.uiEvent.collect(viewLifecycleOwner) {
            when (it) {
                is UiEvent.OpenDetail -> {
                    Log.v("tag", "open detail event")
                    launchDetailFragment(it)
                }
                is UiEvent.RefreshList -> adapter.retry()
            }
        }
    }

    private fun observePagingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.pagingData.collect {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun observeStates() {
        viewmodel.uiState.collect(viewLifecycleOwner) {
            when (it) {
                is Success -> displaySuccessState()
                is Error -> displayErrorState()
                is Loading -> displayLoadingState(it)
            }
        }
    }

    private fun launchDetailFragment(openDetail: UiEvent.OpenDetail) {
        findNavController().navigate(
            VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailFragment(
                openDetail.vehicleItem
            )
        )
    }

    private fun displayErrorState() {
        binding.recyclerView.isVisible = false
        binding.errorLayout.isVisible = true
        binding.loadingLayout.isVisible = false
    }

    private fun displayLoadingState(uiState: Loading) {
        if (uiState.fullscreenLoading) {
            binding.recyclerView.isVisible = false
            binding.errorLayout.isVisible = false
            binding.loadingLayout.isVisible = true
        }
    }

    private fun displaySuccessState() {
        binding.recyclerView.isVisible = true
        binding.errorLayout.isVisible = false
        binding.loadingLayout.isVisible = false
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}