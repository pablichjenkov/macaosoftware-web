package common

import com.macaosoftware.component.viewmodel.StateComponent
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.component.viewmodel.ComponentViewModelFactory

class ViewModelDependency

class DemoViewModel(
    private val component: StateComponent<DemoViewModel>,
    viewModelDependency: ViewModelDependency
) : ComponentViewModel() {

    override fun onStart() {
        println("My bound Component ID = ${component.instanceId()}")
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }
}

class DemoViewModelFactory(
    private val viewModelDependency: ViewModelDependency
) : ComponentViewModelFactory<DemoViewModel> {
    override fun create(component: StateComponent<DemoViewModel>): DemoViewModel {
        return DemoViewModel(component, viewModelDependency)
    }
}
