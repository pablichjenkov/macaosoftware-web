package topbar3page

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.push
import com.macaosoftware.component.topbar.*
import common.SimpleComponent

class TutorialCleanIJViewModel(
    topBarComponent: TopBarComponent<TutorialCleanIJViewModel>,
    override val topBarStatePresenter: TopBarStatePresenter,
    private val screenName: String,
    private val onMessage: (Msg) -> Unit
) : TopBarComponentViewModel(topBarComponent) {

    private val tutorialCleanIJComponent = topBarComponent

    val Step1 = SimpleComponent(
        "$screenName/Page 1",
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                tutorialCleanIJComponent.navigator.push(Step2)
            }
        }
    }.also {
        it.setParent(tutorialCleanIJComponent)
    }

    val Step2 = SimpleComponent(
        "$screenName/Page 2",
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                tutorialCleanIJComponent.navigator.push(Step3)
            }
        }
    }.also {
        it.setParent(tutorialCleanIJComponent)
    }

    val Step3 =
        SimpleComponent(
            "$screenName/Page 3",
        ) { msg ->
            when (msg) {
                SimpleComponent.Msg.Next -> {
                    onMessage(Msg.OnboardDone)
                    //router?.handleDeepLink("Settings")
                }
            }
        }.also {
            it.setParent(tutorialCleanIJComponent)
        }

    private var activeComponent: Component? = null

    override fun mapComponentToStackBarItem(topComponent: Component): TopBarItem {
        return when (topComponent) {
            Step1 -> {
                TopBarItem(
                    Step1.text,
                    Icons.Filled.Star,
                )
            }

            Step2 -> {
                TopBarItem(
                    Step2.text,
                    Icons.Filled.Star,
                )
            }

            Step3 -> {
                TopBarItem(
                    Step3.text,
                    Icons.Filled.Star,
                )
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onCheckChildForNextUriFragment(nextUriFragment: String): Component? {
        return when (nextUriFragment) {
            "Page1" -> Step1
            "Page2" -> Step2
            "Page3" -> Step3
            else -> null
        }
    }

    override fun onAttach() {
        tutorialCleanIJComponent.uriFragment = screenName
    }

    override fun onStart() {
        if (activeComponent == null) {
            activeComponent = Step1
            tutorialCleanIJComponent.navigator.push(Step1)
        }

    }

    override fun onStop() {

    }

    override fun onDetach() {

    }

    sealed interface Msg {
        object OnboardDone : Msg
    }
}

class TutorialCleanIJViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenter,
    private val screenName: String,
    private val onMessage: (TutorialCleanIJViewModel.Msg) -> Unit
) : TopBarComponentViewModelFactory<TutorialCleanIJViewModel> {
    override fun create(
        topBarComponent: TopBarComponent<TutorialCleanIJViewModel>
    ): TutorialCleanIJViewModel {
        return TutorialCleanIJViewModel(
            topBarComponent = topBarComponent,
            topBarStatePresenter = topBarStatePresenter,
            screenName = screenName,
            onMessage = onMessage
        )
    }

}
