version = 1

cloudstream {
    description = "Mon extension personnelle"
    authors = listOf("Spyro")
    status = 1
    tvTypes = listOf("Movie")
    language = "fr"
    iconUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Korduene_Logo.png"
}

android {
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
