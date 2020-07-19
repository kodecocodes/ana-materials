/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.wewatch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie(
  @SerializedName("vote_count")
  @Expose
  var voteCount: Int? = null,
  @PrimaryKey
  @SerializedName("id")
  @Expose
  var id: Int? = null,
  @SerializedName("video")
  @Expose
  var video: Boolean? = null,
  @SerializedName("vote_average")
  @Expose
  var voteAverage: Float? = null,
  @SerializedName("title")
  @Expose
  var title: String? = null,
  @SerializedName("popularity")
  @Expose
  var popularity: Float? = null,
  @SerializedName("poster_path")
  @Expose
  var posterPath: String? = null,
  @SerializedName("original_language")
  @Expose
  var originalLanguage: String? = null,
  @SerializedName("original_title")
  @Expose
  var originalTitle: String? = null,
  @SerializedName("genre_ids")
  @Expose
  var genreIds: List<Int>? = null,
  @SerializedName("backdrop_path")
  @Expose
  var backdropPath: String? = null,
  @SerializedName("adult")
  @Expose
  var adult: Boolean? = null,
  @SerializedName("overview")
  @Expose
  var overview: String? = null,
  @SerializedName("release_date")
  @Expose
  var releaseDate: String? = null,
  var watched: Boolean = false) {

  fun getReleaseYearFromDate(): String {
    return releaseDate?.split("-")?.get(0) ?: ""
  }
}
