package np.pro.dipendra.interview.uilayer.vehicleDetail

import kotlinx.coroutines.flow.StateFlow
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleItem

interface VehicleDetailController {
    val uiState: StateFlow<UiState>
    suspend fun handleAction(uiAction: UiAction)

    sealed class UiState {
        object Loading : UiState()
        data class Body(val vehicleItem: VehicleItem) : UiState()
    }

    sealed class UiAction {
        data class InitialView(val vehicleItem: VehicleItem) : UiAction()
    }
}
