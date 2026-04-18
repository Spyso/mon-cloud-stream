package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class VoirAnimeProvider : MainAPI() {
    override var mainUrl = "https://voir-anime.to"
    override var name = "VoirAnime"
    override val hasMainPage = true
    override var lang = "fr"
    override val hasSearch = true
    override val supportedTypes = setOf(TvType.Anime)

    override val mainPage = mainPageOf(
        "$mainUrl/animes-vf/" to "Animes VF",
        "$mainUrl/animes-vostfr/" to "Animes VOSTFR",
        "$mainUrl/films-anime/" to "Films Animés",
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val url = request.data + "page/$page/"
        val doc = app.get(url).document
        val items = doc.select("div.film-detail, article.card, div.anime-card").mapNotNull {
            it.toSearchResult()
        }
        return newHomePageResponse(request.name, items)
    }

    private fun Element.toSearchResult(): AnimeSearchResponse? {
        val title = selectFirst("h3, h2, .title, .name")?.text() ?: return null
        val href = fixUrl(selectFirst("a")?.attr("href") ?: return null)
        val poster = fixUrlNull(
            selectFirst("img")?.attr("data-src")
                ?: selectFirst("img")?.attr("src")
        )
        return newAnimeSearchResponse(title, href, TvType.Anime) {
            this.posterUrl = poster
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val doc = app.get("$mainUrl/?s=$query").document
        return doc.select("div.film-detail, article.card, div.anime-card")
            .mapNotNull { it.toSearchResult() }
    }

    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.selectFirst("h1, .film-name, .entry-title")?.text() ?: ""
        val poster = fixUrlNull(
            doc.selectFirst("div.film-poster img, .cover img")?.attr("src")
        )
        val description = doc.selectFirst("div.description, .synopsis, div.overview p")?.text()
        val episodes = doc.select("div.ep-item a, ul.episodes a, .episode-list a").mapNotNull { ep ->
            val epHref = fixUrl(ep.attr("href"))
            val epName = ep.text().trim()
            Episode(data = epHref, name = epName)
        }
        return newAnimeLoadResponse(title, url, TvType.Anime, episodes) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        doc.select("iframe, div[data-src]").forEach { element ->
            val src = element.attr("src").ifEmpty { element.attr("data-src") }
            if (src.isNotEmpty()) {
                loadExtractor(fixUrl(src), data, subtitleCallback, callback)
            }
        }
        return true
    }
}
