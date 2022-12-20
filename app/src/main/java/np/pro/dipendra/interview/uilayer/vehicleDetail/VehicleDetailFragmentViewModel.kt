package np.pro.dipendra.interview.uilayer.vehicleDetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VehicleDetailFragmentViewModel @Inject constructor(private val controller: VehicleDetailController) :
    ViewModel(), VehicleDetailController by controller