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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
open class Movie {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  @SerializedName("id")
  @Expose
  var id: Int? = null

  @SerializedName("vote_count")
  @Expose
  var voteCount: Int? = null
  @SerializedName("video")
  @Expose
  var video: Boolean? = null
  @ColumnInfo(name = "vote_count")
  @SerializedName("vote_average")
  @Expose
  var voteAverage: Float? = null

  @ColumnInfo(name = "title")
  @SerializedName("title")
  @Expose
  var title: String? = null
  @SerializedName("popularity")
  @Expose
  var popularity: Float? = null
  @SerializedName("poster_path")
  @Expose
  var posterPath: String? = null
  @SerializedName("original_language")
  @Expose
  var originalLanguage: String? = null
  @SerializedName("original_title")
  @Expose
  var originalTitle: String? = null
  @SerializedName("genre_ids")
  @Expose
  @TypeConverters(IntegerListTypeConverter::class)
  var genreIds: List<Int>? = null
  @SerializedName("backdrop_path")
  @Expose
  var backdropPath: String? = null
  @SerializedName("adult")
  @Expose
  var adult: Boolean? = null
  @SerializedName("overview")
  @Expose
  var overview: String? = null

  @ColumnInfo(name = "release_date")
  @SerializedName("release_date")
  @Expose
  var releaseDate: String? = null

  /**
   * No args constructor for use in serialization
   *
   */
  constructor() {}


  /**
   * Constructor for manually added movies
   *
   */
  constructor(title: String, releaseDate: String, posterPath: String) {
    this.title = title
    this.releaseDate = releaseDate
    this.posterPath = posterPath
  }

  /**
   *
   * @param genreIds
   * @param id
   * @param title
   * @param releaseDate
   * @param overview
   * @param posterPath
   * @param originalTitle
   * @param voteAverage
   * @param originalLanguage
   * @param adult
   * @param backdropPath
   * @param voteCount
   * @param video
   * @param popularity
   */
  constructor(id: Int?, voteCount: Int?, video: Boolean?, voteAverage: Float?, title: String, popularity: Float?, posterPath: String, originalLanguage: String, originalTitle: String, genreIds: List<Int>, backdropPath: String, adult: Boolean?, overview: String, releaseDate: String) : super() {
    //this.id = id;
    this.voteCount = voteCount
    this.video = video
    this.voteAverage = voteAverage
    this.title = title
    this.popularity = popularity
    this.posterPath = posterPath
    this.originalLanguage = originalLanguage
    this.originalTitle = originalTitle
    this.genreIds = genreIds
    this.backdropPath = backdropPath
    this.adult = adult
    this.overview = overview
    this.releaseDate = releaseDate
  }

}
