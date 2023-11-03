package root

import contactus.ContactUsTopBarComponent
import home.HomeComponent
import androidx.compose.material.icons.Icons
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
                component = HomeComponent()
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = TopBarComponent<TutorialCleanIJViewModel>(
                    viewModelFactory = TutorialCleanIJViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders",
                        onMessage = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Contact Us",
                icon = Icons.Filled.Email,
                component = ContactUsTopBarComponent("Contact Us").apply {
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
