package com.ilsamil.readingdiary

import android.util.Log
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var selectedDate = LocalDate.now()

        print(selectedDate.year.toString())
        print("\n")
        print(selectedDate.monthValue.toString())

        assertEquals(4, 2 + 2)
    }
}