package np.pro.dipendra.interview.uilayer.vehiclelist

import androidx.paging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.*
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.UiAction.*
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.UiEvent.OpenDetail
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController.UiEvent.RefreshList
import javax.inject.Inject

class VehicleListControllerImpl @Inject constructor(
    private val vehicleListPagedSource: VehicleListPagedSource
) : VehicleListController {
    override lateinit var pagingData: Flow<PagingData<VehicleItem>>
    override var uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading(true))
    override var uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()


    override suspend fun handleAction(uiAction: UiAction) {
        when (uiAction) {
            is InitialView -> {
                pagingData = Pager(config = PagingConfig(100),
                    initialKey = 1,
                    pagingSourceFactory = { vehicleListPagedSource }).flow.cachedIn(
                    CoroutineScope(
                        currentCoroutineContext()
                    )
                )
            }
            is Retry -> retry()
            is ItemClick -> {
                openDetail(uiAction)
            }
            is LoadStateChanged -> {
                val loadState = uiAction.combinedLoadStates
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        uiState.value = UiState.Loading(true)
                    }
                    is LoadState.NotLoading -> {
                        uiState.value = UiState.Success
                    }
                    else -> {
                        // getting the error
                        val error = when {
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }
                        error?.let {
                            uiState.value = UiState.Error(error.error.message ?: "")
                        }
                    }
                }
            }
        }
    }

    private suspend fun openDetail(uiAction: ItemClick) {
        uiEvent.emit(OpenDetail(uiAction.vehicleItem))
    }

    private suspend fun retry() {
        uiState.value = UiState.Loading(true)
        uiEvent.emit(RefreshList)
    }

}