import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.stack.StackComponent

object PanelBuilder {

    fun build(): PanelComponent {

        val panelNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = HomeComponent()
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = CustomTopBarComponent("Orders", StackComponent.DefaultConfig) {},
            ),
            NavItem(
                label = "Contact Us",
                icon = Icons.Filled.Email,
                component = AboutTopBarComponent("Contact Us", StackComponent.DefaultConfig)
            )
        )

        return PanelComponent().also {
            it.setNavItems(panelNavItems, 0)
        }
    }

}
