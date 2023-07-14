import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.topbar.TopBarComponent

class AboutTopBarComponent(
    val screenName: String,
    config: Config
) : TopBarComponent(config) {

    val Step1 = SimpleComponent(
        screenName,
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                // backStack.push(Step2)
            }
        }
    }.also {
        it.setParent(this@AboutTopBarComponent)
    }

    override fun onStart() {
        println("CustomTopBarComponent::onStart()")
        if (activeComponent.value == null) {
            backStack.push(Step1)
        } else {
            activeComponent.value?.dispatchStart()
        }
    }

    override fun getStackBarItemForComponent(topComponent: Component): StackBarItem {
        return when (topComponent) {
            Step1 -> {
                StackBarItem(
                    Step1.text,
                    Icons.Filled.Star,
                )
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    // region: DeepLink

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return DeepLinkMatchData(
            screenName,
            DeepLinkMatchType.MatchOne
        )
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return when (nextUriFragment) {
            "contact_us" -> Step1
            else -> null
        }
    }

    // endregion

}
