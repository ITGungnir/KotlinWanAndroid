package app.itgungnir.kwa.mine.delegate

import android.os.Bundle
import android.view.View
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.widget.easy_adapter.BaseDelegate
import app.itgungnir.kwa.common.widget.easy_adapter.EasyAdapter
import app.itgungnir.kwa.mine.MineState
import kotlinx.android.synthetic.main.listitem_mine_delete.view.*

class MineDeleteDelegate(
    private val deleteLambda: (Int, Int) -> Unit
) : BaseDelegate<MineState.MineDeleteVO>() {

    override fun layoutId(): Int = R.layout.listitem_mine_delete

    override fun onCreateVH(container: View) {
    }

    override fun onBindVH(
        item: MineState.MineDeleteVO,
        holder: EasyAdapter.VH,
        position: Int,
        payloads: MutableList<Bundle>
    ) {

        var id = item.id
        var originId = item.originId

        holder.render(item) {

            if (payloads.isNotEmpty()) {
                val payload = payloads[0]
                id = if (payload.containsKey("PL_ID")) payload.getInt("PL_ID") else id
                originId = if (payload.containsKey("PL_OID")) payload.getInt("PL_OID") else originId
            }

            delete.setOnClickListener {
                deleteLambda.invoke(id, originId)
            }
        }
    }
}