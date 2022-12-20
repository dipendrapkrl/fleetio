package np.pro.dipendra.interview.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import np.pro.dipendra.interview.datalayer.network.NetworkDataSource
import np.pro.dipendra.interview.datalayer.network.NetworkDataSourceImpl
import np.pro.dipendra.interview.datalayer.repository.vehicles.VehiclesRepository
import np.pro.dipendra.interview.datalayer.repository.vehicles.VehiclesRepositoryImpl
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailController
import np.pro.dipendra.interview.uilayer.vehicleDetail.VehicleDetailControllerImpl
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListController
import np.pro.dipendra.interview.uilayer.vehiclelist.VehicleListControllerImpl

@Module
@InstallIn(SingletonComponent::class)
interface HiltBindModule {
    @Binds
    fun bindCountriesRepositoryImpl(vehiclesRepositoryImpl: VehiclesRepositoryImpl): VehiclesRepository

    @Binds
    fun bindNetworkDataSource(networkDataSource: NetworkDataSourceImpl): NetworkDataSource

    @Binds
    fun bindVehicleListControllerImpl(vehicleListControllerImpl: VehicleListControllerImpl): VehicleListController


    @Binds
    fun bindVehicleDetailController(vehicleDetailControllerImpl: VehicleDetailControllerImpl): VehicleDetailController

}