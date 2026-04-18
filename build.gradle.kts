import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.gradle.kotlin.dsl.configure

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.lagradost.cloudstream3.gradle")
}

cloudstream {
    repositoryName = "Flux cloud Mon"
    repositoryDescription = "Mon depot d'extensions personnel"
}

dependencies {
    val cloudstreamVersion = "4.0.0"
    compileOnly("com.github.recloudstream:cloudstream:v$cloudstreamVersion")
}
