package com.san.moviedbclientmvc.networking.model

data class Movie (var id: Integer,
                 var original_title: String,
                 var popularity: Number,
                 var poster_path: String?,
                 var vote_count: Int,
                 var release_date: String,
                 var vote_average: Number,
                 var overview: String,
                 var releases: Releases?
)