package com.seback.moviedbcompose.core.data.api

enum class ApiSortBy(val value: String) {
    POPULARITY_DESC("popularity.desc"),
    VOTE_COUNT_DESC("vote_count.desc"),
    RELEASE_DATE_DESC("primary_release_date.des"),
    ORIGINAL_TITLE_ASC("original_title.asc")
}