package app.itgungnir.kwa.home

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import app.itgungnir.kwa.common.ext.handleResult
import app.itgungnir.kwa.common.ext.io2Main
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.widget.banner.BannerItem
import app.itgungnir.kwa.common.widget.recycler_footer.FooterStatus
import app.itgungnir.kwa.common.widget.recycler_list.ItemData
import app.itgungnir.kwa.common.widget.recycler_list.RecyclerListStatus
import app.itgungnir.kwa.home.delegate.BannerVO
import app.itgungnir.kwa.home.delegate.HomeArticleVO
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import my.itgungnir.rxmvvm.core.mvvm.BaseVM

@SuppressLint("CheckResult")
class HomeVM : BaseVM() {

    val listDataState = MutableLiveData<RecyclerListStatus<List<ItemData>>>()

    val footerState = MutableLiveData<FooterStatus>()

    private val dataList = mutableListOf<ItemData>()

    private var currPage = 0

    private var hasMore = false

    fun getHomeData() {

        currPage = 0

        val s1 = HttpClient.api.banner()
            .handleResult()
            .io2Main()
            .map { BannerVO(it.map { item -> BannerItem(item.imagePath, item.title, item.url) }) }

        val s2 = HttpClient.api.homeArticle(currPage)
            .handleResult()
            .io2Main()
            .map {
                this.hasMore = it.over == false
                it.datas.map { item -> HomeArticleVO(item) }
            }

        Single.zip(s1, s2, BiFunction { t1: BannerVO, t2: List<HomeArticleVO> ->
            val responseList = mutableListOf<ItemData>(t1)
            responseList.addAll(t2)
            return@BiFunction responseList
        }).doOnSubscribe { this.listDataState.value = RecyclerListStatus.processing() }
            .doFinally { this.listDataState.value = RecyclerListStatus.complete() }
            .subscribe({
                dataList.clear()
                dataList.addAll(it)
                this.listDataState.value = RecyclerListStatus.success(dataList)
                this.footerState.value = FooterStatus.succeed(hasMore)
            }, {
                this.listDataState.value = RecyclerListStatus.error(it)
            })
    }

    fun loadMoreHomeData() {

        HttpClient.api.homeArticle(++currPage)
            .handleResult()
            .io2Main()
            .map {
                this.hasMore = it.over == false
                it.datas.map { item -> HomeArticleVO(item) }
            }.doOnSubscribe { this.footerState.value = FooterStatus.loading() }
            .subscribe({
                dataList.addAll(it)
                this.listDataState.value = RecyclerListStatus.success(dataList)
                this.footerState.value = FooterStatus.succeed(hasMore)
            }, {
                this.footerState.value = FooterStatus.failed()
            })
    }
}