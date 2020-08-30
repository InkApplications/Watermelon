package com.inkapplications.standard

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CompositeExceptionTest {

    @JsName("no_empty_exceptions")
    @Test
    fun `no empty exceptions`() {
        assertFailsWith<IllegalArgumentException>(message = "exceptions is empty") {
            CompositeException(emptyList())
        }
    }

    @JsName("message_should_print_each_exception_message")
    @Test
    fun `message should print each exception message`() {
        val e = CompositeException(listOf(Throwable("t1"), Throwable("t2")))

        // testing this way due to throwables on jvm being java.lang.Throwable and
        // every other platform being kotlin.Throwable
        assertTrue {
            e.message!!.startsWith("Multiple exceptions occurred:")
            e.message!!.contains("Throwable: t1, ")
            e.message!!.contains("Throwable: t2")
        }
    }
}
