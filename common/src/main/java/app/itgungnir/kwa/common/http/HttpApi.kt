package app.itgungnir.kwa.common.http

import app.itgungnir.kwa.common.http.dto.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HttpApi {

    /**
     * 首页：banner
     */
    @GET("/banner/json")
    fun banners(): Single<Result<List<BannerResponse>>>

    /**
     * 首页：置顶列表
     */
    @GET("/article/top/json")
    fun topArticles(): Single<Result<List<ArticleResponse>>>

    /**
     * 首页：文章列表
     */
    @GET("/article/list/{page}/json")
    fun homeArticles(@Path("page") page: Int): Single<Result<ArticleListResponse>>

    /**
     * 搜索：热词列表
     */
    @GET("/hotkey/json")
    fun hotKeys(): Single<Result<List<TagResponse>>>

    /**
     * 搜索：结果列表
     */
    @POST("/article/query/{page}/json")
    fun searchResult(@Path("page") page: Int, @Query("k") k: String): Single<Result<ArticleListResponse>>

    /**
     * 知识体系：分类
     */
    @GET("/tree/json")
    fun hierarchyTabs(): Single<Result<List<TabResponse>>>

    /**
     * 知识体系：文章列表
     */
    @GET("/article/list/{page}/json")
    fun hierarchyArticles(@Path("page") page: Int, @Query("cid") cid: Int): Single<Result<ArticleListResponse>>

    /**
     * 常用网站
     */
    @GET("/friend/json")
    fun tools(): Single<Result<List<TagResponse>>>

    /**
     * 导航
     */
    @GET("/navi/json")
    fun navigation(): Single<Result<List<NavigationResponse>>>

    /**
     * 公众号：分类
     */
    @GET("/wxarticle/chapters/json")
    fun weixinTabs(): Single<Result<List<TabResponse>>>

    /**
     * 公众号：文章列表
     */
    @GET("/wxarticle/list/{cid}/{page}/json")
    fun weixinArticles(@Path("page") page: Int, @Path("cid") cid: Int, @Query("k") k: String): Single<Result<ArticleListResponse>>

    /**
     * 项目：分类
     */
    @GET("/project/tree/json")
    fun projectTabs(): Single<Result<List<TabResponse>>>

    /**
     * 项目：文章列表
     */
    @GET("/project/list/{page}/json")
    fun projectArticles(@Path("page") page: Int, @Query("cid") cid: Int): Single<Result<ArticleListResponse>>
}