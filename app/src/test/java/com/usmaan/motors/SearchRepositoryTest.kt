package com.usmaan.motors

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.fail

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest {

    @Mock
    lateinit var service: SearchService

    @Mock
    lateinit var mapper: SearchMapper

    @Test
    fun `getAll to return all motors`() = runTest {
        // Given
        val repository = SearchRepository(service, mapper)
        val motor1 = Motor("id", "name1", "title1", "make1", "model1", "year1", "price1")
        val motor2 = Motor("id", "name2", "title2", "make2", "model2", "year2", "price2")
        val motors = listOf(motor1, motor2)
        val response = SearchResults(motors)

        whenever(service.getAll()).thenReturn(response)
        whenever(mapper.invoke(response)).thenReturn(motors)

        // When
        repository.getAll(
            onSuccess = {
                assertEquals(response.searchResults, it)
            },
            onError = {
                fail("OnError was Called")
            })
    }

    @Test
    fun `getAll to return no motors when API returns none`() = runTest {
        // Given
        val repository = SearchRepository(service, mapper)
        val motors: List<Motor> = listOf()
        val response = SearchResults(motors)

        whenever(service.getAll()).thenReturn(response)
        whenever(mapper.invoke(response)).thenReturn(motors)

        // When
        repository.getAll(
            onSuccess = {
                assertEquals(0, response.searchResults.size)
            },
            onError = {
                fail("OnError was Called")
            })
    }
}