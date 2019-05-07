package app.itgungnir.kwa.common.http

import app.itgungnir.kwa.common.http.dto.ArticleResponse
import app.itgungnir.kwa.common.http.dto.BannerResponse
import app.itgungnir.kwa.common.http.dto.HistoryResponse
import app.itgungnir.kwa.common.http.dto.TabResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HttpApi {

    /**
     * 首页：banner
     */
    @GET("/banner/json")
    fun banners(): Single<Result<List<BannerResponse>>>

    /**
     * 首页：文章列表
     */
    @GET("/article/list/{page}/json")
    fun homeArticles(@Path("page") page: Int): Single<Result<ArticleResponse>>

    /**
     * 搜索：热词
     */
    @GET("/hotkey/json")
    fun hotKeys(): Single<Result<List<HistoryResponse>>>

    /**
     * 知识体系：分类
     */
    @GET("/tree/json")
    fun hierarchyTabs(): Single<Result<List<TabResponse>>>

    /**
     * 知识体系：文章列表
     */
    @GET("/article/list/{page}/json")
    fun hierarchyArticles(@Path("page") page: Int, @Query("cid") cid: Int): Single<Result<ArticleResponse>>

    /**
     * 公众号：分类
     */
    @GET("/wxarticle/chapters/json")
    fun weixinTabs(): Single<Result<List<TabResponse>>>

    /**
     * 公众号：文章列表
     */
    @GET("/wxarticle/list/{cid}/{page}/json")
    fun weixinArticles(@Path("page") page: Int, @Path("cid") cid: Int, @Query("k") k: String): Single<Result<ArticleResponse>>

    /**
     * 项目：分类
     */
    @GET("/project/tree/json")
    fun projectTabs(): Single<Result<List<TabResponse>>>

    /**
     * 项目：文章列表
     */
    @GET("/project/list/{page}/json")
    fun projectArticles(@Path("page") page: Int, @Query("cid") cid: Int): Single<Result<ArticleResponse>>
}