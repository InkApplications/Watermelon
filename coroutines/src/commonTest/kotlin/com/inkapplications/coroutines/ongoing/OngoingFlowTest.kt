package com.inkapplications.coroutines.ongoing

import com.inkapplications.coroutines.doubles.Animal
import kotlinx.coroutines.flow.*
import kotlin.test.Test
import kotlin.test.assertSame

class OngoingFlowTest
{
    @Test
    fun convertFlow()
    {
        val flow = channelFlow { send(Animal.Bird) }
        val ongoing = flow.asOngoing()

        assertSame(flow, ongoing.asFlow(), "Flow instance is preserved")
    }
}
