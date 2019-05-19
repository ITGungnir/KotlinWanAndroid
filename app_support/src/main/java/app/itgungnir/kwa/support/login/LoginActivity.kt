package app.itgungnir.kwa.support.login

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.*
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.LocalizeUserInfo
import app.itgungnir.kwa.support.R
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.grouter.api.Router
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel

@Route(LoginActivity)
class LoginActivity : BaseActivity() {

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = this,
            viewModelClass = LoginViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.activity_login

    @SuppressLint("CheckResult")
    override fun initComponent() {

        headBar.title("用户登录")
            .back(getString(R.string.icon_back)) { finish() }

        Observable.combineLatest(
            arrayOf(
                RxTextView.textChanges(userNameInput.getInput()),
                RxTextView.textChanges(passwordInput.getInput())
            )
        ) { items: Array<Any> -> items.all { item -> item.toString().trim().isNotEmpty() } }
            .subscribe { valid: Boolean ->
                when (valid) {
                    true ->
                        login.ready("登录")
                    else ->
                        login.disabled("登录")
                }
            }

        login.apply {
            disabled("登录")
            onAntiShakeClick {
                hideSoftInput()
                loading()
                val userName = userNameInput.getInput().editableText.toString().trim()
                val password = passwordInput.getInput().editableText.toString().trim()
                viewModel.login(userName, password)
            }
        }

        toRegister.onAntiShakeClick {
            it.hideSoftInput()
            Router.instance.with(this)
                .target(RegisterActivity)
                .go()
        }
    }

    override fun observeVM() {

        viewModel.pick(LoginState::userInfo)
            .observe(this, Observer { userInfo ->
                userInfo?.a?.let {
                    AppRedux.instance.dispatch(
                        LocalizeUserInfo(it.collectIds - -1, it.userName)
                    )
                    login.ready("登录")
                    finish()
                }
            })

        viewModel.pick(LoginState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                    login.ready("登录")
                }
            })
    }
}