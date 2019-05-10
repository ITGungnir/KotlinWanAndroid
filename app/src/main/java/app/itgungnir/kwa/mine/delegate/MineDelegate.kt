package app.itgungnir.kwa.mine.delegate

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.*
import app.itgungnir.kwa.mine.MineState
import kotlinx.android.synthetic.main.listitem_mine.view.*

class MineDelegate(
    private val deleteLambda: (Int, Int) -> Unit
) : BaseDelegate<MineState.MineDataVO>() {

    private var listAdapter: EasyAdapter? = null

    override fun layoutId(): Int = R.layout.listitem_mine

    override fun onCreateVH(container: View) {

        container.apply {

            list.apply {

                object : LinearSnapHelper() {
                    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
                        if (layoutManager is LinearLayoutManager) {
                            val lastPos = layoutManager.findLastVisibleItemPosition()
                            return layoutManager.findViewByPosition(lastPos)
                        }
                        return null
                    }
                }.attachToRecyclerView(this)

                listAdapter = bind(
                    manager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false),
                    diffAnalyzer = object : Differ {
                        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                            if (oldItem is MineState.MineArticleVO && newItem is MineState.MineArticleVO) {
                                oldItem.id == newItem.id && oldItem.originId == newItem.originId
                            } else {
                                false
                            }

                        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                            if (oldItem is MineState.MineArticleVO && newItem is MineState.MineArticleVO) {
                                oldItem.author == newItem.author &&
                                        oldItem.title == newItem.title &&
                                        oldItem.category == newItem.category &&
                                        oldItem.categoryId == newItem.categoryId &&
                                        oldItem.date == newItem.date &&
                                        oldItem.link == newItem.link &&
                                        oldItem.id == newItem.id &&
                                        oldItem.originId == newItem.originId
                            } else {
                                false
                            }

                        override fun getChangePayload(oldItem: ListItem, newItem: ListItem): Bundle? {
                            if (oldItem is MineState.MineArticleVO && newItem is MineState.MineArticleVO) {
                                val bundle = Bundle()
                                bundle.putInt("PL_ID", newItem.id)
                                bundle.putInt("PL_OID", newItem.originId)
                                if (oldItem.author != newItem.author) {
                                    bundle.putString("PL_AUTHOR", newItem.author)
                                }
                                if (oldItem.title != newItem.title) {
                                    bundle.putString("PL_TITLE", newItem.title)
                                }
                                if (oldItem.category != newItem.category) {
                                    bundle.putString("PL_C", newItem.category)
                                }
                                if (oldItem.categoryId != newItem.categoryId) {
                                    bundle.putInt("PL_CID", newItem.categoryId)
                                }
                                if (oldItem.date != newItem.date) {
                                    bundle.putString("PL_DATE", newItem.date)
                                }
                                if (oldItem.link != newItem.link) {
                                    bundle.putString("PL_LINK", newItem.link)
                                }
                                return if (bundle.isEmpty) null else bundle
                            } else if (oldItem is MineState.MineDeleteVO && newItem is MineState.MineDeleteVO) {
                                val bundle = Bundle()
                                bundle.putInt("PL_ID", newItem.id)
                                bundle.putInt("PL_OID", newItem.originId)
                                return if (bundle.isEmpty) null else bundle
                            } else {
                                return null
                            }
                        }
                    }
                ).map({ data -> data is MineState.MineArticleVO }, MineArticleDelegate())
                    .map({ data -> data is MineState.MineDeleteVO }, MineDeleteDelegate(deleteLambda))
            }
        }
    }

    override fun onBindVH(
        item: MineState.MineDataVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        holder.render(item) {

            var articleVO = item.articleVO
            var deleteVO = item.deleteVO

            if (payloads.isNotEmpty()) {
                val payload = payloads[0]
                articleVO = articleVO.copy(
                    id = if (payload.containsKey("PL_ID")) payload.getInt("PL_ID") else articleVO.id,
                    originId = if (payload.containsKey("PL_OID")) payload.getInt("PL_OID") else articleVO.originId,
                    author = if (payload.containsKey("PL_AUTHOR")) payload.getString("PL_AUTHOR") else articleVO.author,
                    category = if (payload.containsKey("PL_C")) payload.getString("PL_C") else articleVO.category,
                    categoryId = if (payload.containsKey("PL_CID")) payload.getInt("PL_CID") else articleVO.categoryId,
                    title = if (payload.containsKey("PL_TITLE")) payload.getString("PL_TITLE") else articleVO.title,
                    date = if (payload.containsKey("PL_DATE")) payload.getString("PL_DATE") else articleVO.date,
                    link = if (payload.containsKey("PL_LINK")) payload.getString("PL_LINK") else articleVO.link
                )
                deleteVO = deleteVO.copy(
                    id = if (payload.containsKey("PL_ID")) payload.getInt("PL_ID") else deleteVO.id,
                    originId = if (payload.containsKey("PL_OID")) payload.getInt("PL_OID") else deleteVO.originId
                )
            }

            listAdapter?.update(listOf(articleVO, deleteVO))
        }
    }
}