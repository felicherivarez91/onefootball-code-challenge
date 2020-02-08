package com.onefootball

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.onefootball.model.News
import com.onefootball.util.TestSchedulerProvider
import com.onefootball.view.util.loadingstate.LoadingState
import com.onefootball.view.util.schedulers.ISchedulersProvider
import com.onefootball.viewmodel.NewsViewModel
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
@RunWith(JUnit4::class)
class NewsViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val mockDataSource: IDataSource by lazy {
        mock(IDataSource::class.java)
    }

    private val viewModel: NewsViewModel by lazy {
        NewsViewModel(mockDataSource)
    }

    private val stateObserver: Observer<LoadingState> by lazy {
        mock(Observer::class.java) as Observer<LoadingState>
    }

    private val observer: Observer<List<News>> by lazy {
        mock(Observer::class.java) as Observer<List<News>>
    }

    private val mockSchedulerProvider =
        TestSchedulerProvider()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        viewModel.errorStateLiveData.observeForever(stateObserver)
        viewModel.itemsLiveData.observeForever(observer)
    }

    @Test
    fun testNullValue() {
        Mockito.`when`(mockDataSource.getNews()).thenReturn(null)

        assertNotNull(viewModel.errorStateLiveData)
        assertTrue(viewModel.errorStateLiveData.hasObservers())
        assertNotNull(viewModel.itemsLiveData)
        assertTrue(viewModel.itemsLiveData.hasObservers())
    }

    @Test
    fun testHasValues() {
        Mockito.`when`(mockDataSource.getNews()).thenReturn(Single.just(getAllItems()))
        viewModel.loadNews()

        Mockito.verify(observer, times(1)).onChanged(LoadingState.SUCCESS_STATE.data)
        Mockito.verify(stateObserver, times(0)).onChanged(LoadingState.ERROR_STATE)
        assertTrue(LoadingState.SUCCESS_STATE.data!!.isNotEmpty())
        assertTrue(LoadingState.SUCCESS_STATE.data!!.size == 3)
        assertTrue(LoadingState.SUCCESS_STATE.data!!.get(0).title == "The 5 players who could be the next Messi or Ronaldo")
        assertTrue(LoadingState.SUCCESS_STATE.data!!.get(0).resourceName == "Onefootball")
        assertEquals(getAllItems(), LoadingState.SUCCESS_STATE.data)
    }

    @Test
    fun testDataError() {
        Mockito.`when`(mockDataSource.getNews()).thenReturn(Single.error(Throwable()))
        viewModel.loadNews()

        Mockito.verify(stateObserver, times(1)).onChanged(LoadingState.ERROR_STATE)
        Mockito.verify(observer, times(0)).onChanged(LoadingState.SUCCESS_STATE.data)
    }

    private fun getAllItems(): MutableList<News> = mutableListOf(
        News(
            "The 5 players who could be the next Messi or Ronaldo",
            "https://image-service.onefootball.com/crop/face?h=810&amp;image=https%3A%2F%2Fwp-images.onefootball.com%2Fwp-content%2Fuploads%2Fsites%2F10%2F2019%2F08%2FFIFA-Ballon-dOr-Gala-2014-1566312341-1024x683.jpg&amp;q=25&amp;w=1080",
            "Onefootball",
            "https://images.onefootball.com/blogs_logos/circle_onefootball.png",
            "https://onefootball.com/en/news/the-5-players-who-could-be-the-next-messi-or-ronaldo-en-26880141?variable=20190822"
        ),
        News(
            "The 5 players who could be the next Messi or Ronaldo",
            "https://image-service.onefootball.com/crop/face?h=810&amp;image=https%3A%2F%2Fwp-images.onefootball.com%2Fwp-content%2Fuploads%2Fsites%2F10%2F2019%2F08%2FFIFA-Ballon-dOr-Gala-2014-1566312341-1024x683.jpg&amp;q=25&amp;w=1080",
            "Onefootball",
            "https://images.onefootball.com/blogs_logos/circle_onefootball.png",
            "https://onefootball.com/en/news/the-5-players-who-could-be-the-next-messi-or-ronaldo-en-26880141?variable=20190822"
        ),
        News(
            "The 5 players who could be the next Messi or Ronaldo",
            "https://image-service.onefootball.com/crop/face?h=810&amp;image=https%3A%2F%2Fwp-images.onefootball.com%2Fwp-content%2Fuploads%2Fsites%2F10%2F2019%2F08%2FFIFA-Ballon-dOr-Gala-2014-1566312341-1024x683.jpg&amp;q=25&amp;w=1080",
            "Onefootball",
            "https://images.onefootball.com/blogs_logos/circle_onefootball.png",
            "https://onefootball.com/en/news/the-5-players-who-could-be-the-next-messi-or-ronaldo-en-26880141?variable=20190822"
        )
    )
}