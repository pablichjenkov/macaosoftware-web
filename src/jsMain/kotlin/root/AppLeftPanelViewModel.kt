package root

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentViewModel
import com.macaosoftware.component.panel.PanelComponentViewModelFactory
import com.macaosoftware.component.panel.PanelStatePresenter
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import contactus.ContactUsTopBarComponent
import demo.DemoComponent
import home.HomeComponent
import topbar3page.TutorialCleanIJViewModel
import topbar3page.TutorialCleanIJViewModelFactory

class AppLeftPanelViewModel(
    panelComponent: PanelComponent<AppLeftPanelViewModel>,
    override val panelStatePresenter: PanelStatePresenter
) : PanelComponentViewModel(panelComponent) {

    override fun onAttach() {
        panelComponent.uriFragment = "navigation_panel_component"
        val panelNavItems = createPanelNavItems()
        panelComponent.setNavItems(panelNavItems, 0)
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDetach() {

    }

    private fun createPanelNavItems(): MutableList<NavItem> {
        return mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = HomeComponent().apply {
                    uriFragment = "demo"
                }
            ),
            NavItem(
                label = "Demo",
                icon = Icons.Filled.Apps,
                component = DemoComponent(
                    screenName = "Playground"
                ).apply {
                    uriFragment = "demo"
                }
            ),
            NavItem(
                label = "My Account",
                icon = Icons.Filled.Refresh,
                component = TopBarComponent<TutorialCleanIJViewModel>(
                    viewModelFactory = TutorialCleanIJViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "My Account",
                        onMessage = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                ).apply {
                    uriFragment = "my_account"
                }
            ),
            NavItem(
                label = "Contact Us",
                icon = Icons.Filled.Email,
                component = ContactUsTopBarComponent(
                    screenName = "Contact Us"
                ).apply {
                    uriFragment = "contact_us"
                }
            )
        )

    }

}

class AppLeftPanelViewModelFactory(
    private val panelStatePresenter: PanelStatePresenter
) : PanelComponentViewModelFactory<AppLeftPanelViewModel> {
    override fun create(panelComponent: PanelComponent<AppLeftPanelViewModel>): AppLeftPanelViewModel {
        return AppLeftPanelViewModel(panelComponent, panelStatePresenter)
    }
}
