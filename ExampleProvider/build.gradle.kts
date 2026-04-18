version = 1

cloudstream {
    description = "Animes VF et VOSTFR depuis voir-anime.to"
    authors = listOf("Spyro")
    status = 1
    tvTypes = listOf("Anime")
    language = "fr"
    iconUrl = "https://www.google.com/s2/favicons?domain=voir-anime.to&sz=256"
}

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation("org.jsoup:jsoup:1.18.3")
}
