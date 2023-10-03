package common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.macaosoftware.component.viewmodel.StateComponent

object DemoComponentDefaults {

    val DemoComponentView: @Composable StateComponent<DemoViewModel>.(
        modifier: Modifier,
        componentViewModel: DemoViewModel
    ) -> Unit = { modifier, demoViewModel ->
        Text("My bound Component ID = ${instanceId()}")
    }

    fun test() {
        val component1 = StateComponent<DemoViewModel>(
            viewModelFactory = DemoViewModelFactory(ViewModelDependency()),
            content = DemoComponentView
        )
        component1.dispatchStart()
    }

    fun test2() {
        val component2 = StateComponent<DemoViewModel>(
            viewModelFactory = DemoViewModelFactory(ViewModelDependency())
        ) { modifier: Modifier, componentViewModel: DemoViewModel ->
            Text("My bound Component ID = ${instanceId()}")
        }

        component2.dispatchStart()
    }

}
