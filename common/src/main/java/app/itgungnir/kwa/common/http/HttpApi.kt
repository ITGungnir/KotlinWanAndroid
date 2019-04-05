package app.itgungnir.kwa.common.http

import app.itgungnir.kwa.common.http.dto.BannerResponse
import app.itgungnir.kwa.common.http.dto.HomeArticleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface HttpApi {

    /**
     * banner
     */
    @GET("/banner/json")
    fun banner(): Single<Result<List<BannerResponse>>>

    /**
     * 首页 - 文章列表
     */
    @GET("/article/list/{page}/json")
    fun homeArticle(@Path("page") page: Int): Single<Result<HomeArticleResponse>>
}