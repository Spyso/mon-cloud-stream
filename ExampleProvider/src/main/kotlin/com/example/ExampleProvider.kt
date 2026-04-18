package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.AppUtils.parseJson
import com.lagradost.cloudstream3.utils.ExtractorLink

class ExampleProvider : MainAPI() {
    override var mainUrl = "https://example.com"
    override var name = "ExampleProvider"
    override val hasMainPage = false
    override var lang = "fr"
    override val supportedTypes = setOf(TvType.Movie)
}
