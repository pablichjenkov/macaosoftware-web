import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.panel.*
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.core.topbar.TopBarComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object PanelBuilder {

    fun build(): PanelComponent<PanelStatePresenterDefault> {

        val panelNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = HomeComponent()
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = CustomTopBarComponent(
                    "Orders",
                    TopBarComponent.DefaultConfig,
                    {}
                )
            ),
            NavItem(
                label = "Contact Us",
                icon = Icons.Filled.Email,
                component = AboutUsTopBarComponent("Contact Us")
            )
        )

        return PanelComponent(
            panelStatePresenter = createPanelStatePresenter(),
            config = PanelComponent.DefaultConfig,
            content = PanelComponent.DefaultPanelComponentView
        ).also {
            it.setNavItems(panelNavItems, 0)
        }

    }

    private fun createPanelStatePresenter(): PanelStatePresenterDefault {
        return PanelStatePresenterDefault(
            dispatcher = Dispatchers.Main,
            panelHeaderState = NoPanelHeaderState,
        )
    }

}
