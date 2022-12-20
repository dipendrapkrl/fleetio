package np.pro.dipendra.interview.uilayer.vehicleDetail

import kotlinx.coroutines.flow.MutableStateFlow
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailController.UiAction
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailController.UiState
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleItem
import javax.inject.Inject

class VehicleDetailControllerImpl @Inject constructor() : VehicleDetailController {
    override var uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)

    override suspend fun handleAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.InitialView -> setup(uiAction.vehicleItem)
        }
    }

    private fun setup(vehicleItem: VehicleItem) {
        uiState.value = UiState.Body(vehicleItem)
    }

}