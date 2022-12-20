package np.pro.dipendra.interview.uilayer.vehiclelist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VehicleListFragmentViewModel @Inject constructor(private val controller: VehicleListController) :
    ViewModel(), VehicleListController by controller