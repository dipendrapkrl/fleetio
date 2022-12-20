package np.pro.dipendra.interview.uilayer.vehiclelist

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import np.pro.dipendra.interview.datalayer.repository.vehicles.VehiclesInfo

@Keep
@Parcelize
data class VehicleItem(
    val name: String,
    val make: String,
    val model: String,
    val imageUrl: String,
    val vehiclesInfo: VehiclesInfo
) : Parcelable