package app.itgungnir.kwa.common.dto

data class VersionResponse(
    val downloadUrl: String,
    val version: String,
    val versionDesc: String
)