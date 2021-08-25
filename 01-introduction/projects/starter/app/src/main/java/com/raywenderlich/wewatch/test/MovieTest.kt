package com.raywenderlich.wewatch.test

import com.raywenderlich.wewatch.model.Movie
import org.junit.Assert.*
import org.junit.Test

class MovieTest {
    @Test
    fun testGetReleaseYearFromStringFormmattedDate() {
        val movie = Movie(title = "Finding Nemo", releaseDate = "2003-05-30")
        assertEquals("2003", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseYearFromYear() {
        val movie = Movie(title = "FindingNemo", releaseDate = "2003")
        assertEquals("2003", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseEmpty() {
        val movie = Movie(title = "FindingNemo", releaseDate = "")
        assertEquals("", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseNull() {
        val movie = Movie(title = "FindingNemo")
        assertEquals("", movie.getReleaseYearFromDate())
    }
}