package com.inkapplications.standard

import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ResultsTest {
    @Test
    fun throwCancelsSuccess() {
        val result = runCatching { "test" }.throwCancels { TODO("Stub") }

        assertEquals("test", result.getOrNull(), "Successful result returns")
    }

    @Test
    fun throwCancelsUnrelated() {
        val error =  RuntimeException()
        val result = runCatching { throw error }.throwCancels { TODO("Stub") }

        assertEquals(error, result.exceptionOrNull(), "Unrelated exception is preserved")
    }

    @Test
    fun throwCancelsThrows() {
        val cancel = CancellationException()
        val result = runCatching {
            runCatching { throw cancel }.throwCancels()
        }

        assertEquals(cancel, result.exceptionOrNull(), "Cancellation is thrown")
    }

    @Test
    fun throwCancelsInvokesAction() {
        val cancel = CancellationException()
        var invoked: CancellationException? = null
        val result = runCatching {
            runCatching { throw cancel }.throwCancels { invoked = it }
        }

        assertEquals(cancel, result.exceptionOrNull(), "Cancellation is thrown")
        assertEquals(cancel, invoked, "Cancellation action is invoked with exception")
    }
}
