package np.pro.dipendra.interview.uilayer.vehiclelist

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface VehicleListController {
    val pagingData: Flow<PagingData<VehicleItem>>
    val uiState: StateFlow<UiState>
    val uiEvent: SharedFlow<UiEvent>
    suspend fun handleAction(uiAction: UiAction)

    sealed class UiState {
        data class Loading(val fullscreenLoading: Boolean) : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class UiEvent {
        data class OpenDetail(val vehicleItem: VehicleItem) : UiEvent()
        object RefreshList : UiEvent()
    }

    sealed class UiAction {
        object InitialView : UiAction()
        object Retry : UiAction()
        data class LoadStateChanged(val combinedLoadStates: CombinedLoadStates) : UiAction()
        data class ItemClick(val vehicleItem: VehicleItem) : UiAction()
        data class SearchText(val text: String?) : UiAction()
    }

}
