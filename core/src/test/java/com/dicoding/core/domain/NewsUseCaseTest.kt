package com.dicoding.core.domain

import com.dicoding.newsapp.core.data.Resource
import com.dicoding.newsapp.core.domain.model.News
import com.dicoding.newsapp.core.domain.repository.INewsRepository
import com.dicoding.newsapp.core.domain.usecase.NewsInteractor
import com.dicoding.newsapp.core.domain.usecase.NewsUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsUseCaseTest {
    private lateinit var newsUseCase: NewsUseCase
    @Mock private lateinit var newsRepository: INewsRepository

    @Before
    fun setUp() {
        newsUseCase = NewsInteractor(newsRepository)

        val dummyNewsList = listOf(
            News("Sample Title", "title 1", "sample author 1", "17-20-2024", "Lorem Content", "description", false),
            News("Sample Title 1", "title 2", "sample author 2", "17-20-2024", "Lorem Content", "description", false),
            News("Sample Title 2", "title 3", "sample author 3", "17-20-2024", "Lorem Content", "description", false),
            News("Sample Title 3", "title 4", "sample author 4", "17-20-2024", "Lorem Content", "description", true),
            News("Ini Sample Title 1" , "title 5", "sample author 5", "17-20-2024", "Lorem Content", "description", true),
            News("Ini Sample Title 2", "title 6", "sample author 7", "17-20-2024", "Lorem Content", "description", false),
            News("Ini Sample Title 3", "title 7", "sample author 8", "17-20-2024", "Lorem Content", "description", false)
        )
        val resource = Resource.Success(dummyNewsList)

        `when`(newsRepository.getGeneralNews()).thenReturn(flow { emit(resource) })
    }

    @Test
    fun `should get news from repository`() = runBlocking {
        val result = newsUseCase.getNews()

        result.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    assertEquals(7, resource.data?.size)
                    assertEquals("title 1", resource.data?.get(0)?.title)
                }
                is Resource.Error -> {
                    assertTrue(false)
                }

                is Resource.Loading -> assertTrue(false)
            }
        }

        verify(newsRepository).getGeneralNews()
        verifyNoMoreInteractions(newsRepository)
    }
}
