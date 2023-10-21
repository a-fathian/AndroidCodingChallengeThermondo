package ali.fathian.presentation.launch

import ali.fathian.domain.common.Resource
import ali.fathian.domain.model.DomainLaunchModel
import ali.fathian.domain.use_cases.GetAllLaunchesUseCase
import ali.fathian.presentation.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesViewModelTest : BaseTest() {

    private lateinit var viewModel: LaunchesViewModel

    private val getAllLaunchesUseCase = mock<GetAllLaunchesUseCase>()

    @Before
    fun setUp() {
        viewModel = LaunchesViewModel(getAllLaunchesUseCase, mockDispatcher.io())
    }

    @Test
    fun `fetchLaunches success updates uiState with launches`() = runTest {
        val expectedLaunches = getLaunchesList()
        getAllLaunchesUseCase.stub {
            onBlocking { invoke() } doReturn expectedLaunches
        }

        viewModel.fetchLaunches()

        val launches = viewModel.uiState.value

        assertTrue(launches.errorMessage.isEmpty())
        assertEquals(1, launches.allLaunches.size)
        assertEquals(expectedLaunches.data?.get(0)?.name, launches.allLaunches[0].name)
    }

    @Test
    fun `fetchLaunches error updates uiState with error message`() = runTest {

        getAllLaunchesUseCase.stub {
            onBlocking { invoke() } doReturn Resource.Error("Error")
        }

        viewModel.fetchLaunches()

        val launches = viewModel.uiState.value

        assertTrue(launches.errorMessage.isNotEmpty())
        assertEquals("Error", launches.errorMessage)
    }

    private fun getLaunchesList(): Resource<List<DomainLaunchModel>> {
        return Resource.Success(
            listOf(
                DomainLaunchModel(
                    name = "Falcon"
                )
            )
        )
    }
}