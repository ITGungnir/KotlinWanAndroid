package app.itgungnir.kwa.common.http

import app.itgungnir.kwa.common.http.dto.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 知识体系
     */
    @GET("/tree/json")
    fun tree(): Single<Result<List<TreeResponse>>>

    /**
     * 知识体系某栏目下的文章
     */
    @GET("/article/list/{page}/json")
    fun hierarchy(@Path("page") page: Int, @Query("cid") cid: Int): Single<Result<HierarchyResponse>>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    fun projectTab(): Single<Result<List<ProjectTabResponse>>>

    /**
     * 项目
     */
    @GET("/project/list/{page}/json")
    fun project(@Path("page") page: Int, @Query("cid") cid: Int): Single<Result<ProjectResponse>>
}