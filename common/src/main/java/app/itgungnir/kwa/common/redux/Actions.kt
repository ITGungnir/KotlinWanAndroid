package app.itgungnir.kwa.common.redux

import my.itgungnir.rxmvvm.core.redux.Action

data class AddSearchHistory(
    val value: String
) : Action

object ClearSearchHistory : Action

data class LocalizeCookies(
    val cookies: Set<String>
) : Action

data class LocalizeUserInfo(
    val collectIds: Set<Int>,
    val userName: String
) : Action

object ClearUserInfo : Action

data class CollectArticle(
    val articleId: Int
) : Action

data class DisCollectArticle(
    val articleId: Int
) : Action

object NullAction : Action

class LoginActions {
    data class Login(
        val userName: String,
        val password: String
    ) : Action

    data class GetCollectIds(
        val userName: String
    ) : Action

    data class Error(
        val message: String
    ) : Action
}