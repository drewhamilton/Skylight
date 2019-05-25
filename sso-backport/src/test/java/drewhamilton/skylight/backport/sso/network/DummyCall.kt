package drewhamilton.skylight.backport.sso.network

import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DummyCall<T> private constructor(
    private val responseItem: T? = null,
    private val errorCode: Int = -1,
    private val responseBody: ResponseBody? = null
) : Call<T> {

    private var isExecuted: Boolean = false
    private var isCanceled: Boolean = false

    override fun execute(): Response<T> {
        isExecuted = true
        return if (responseItem == null)
            Response.error(errorCode, responseBody!!)
        else
            Response.success(responseItem)
    }

    override fun enqueue(callback: Callback<T>) {
        throw UnsupportedOperationException()
    }

    override fun isExecuted() = isExecuted

    override fun cancel() {
        isCanceled = true
    }

    override fun isCanceled() = isCanceled

    override fun clone(): Call<T> {
        val clone = DummyCall(responseItem, errorCode, responseBody)
        if (isExecuted) clone.execute()
        if (isCanceled) clone.cancel()
        return clone
    }

    override fun request(): Request {
        throw UnsupportedOperationException()
    }

    companion object {
        @JvmStatic
        fun <T> success(responseItem: T) = DummyCall(responseItem)

        @JvmStatic
        fun <T> error(errorCode: Int, responseBody: ResponseBody) =
            DummyCall<T>(errorCode = errorCode, responseBody = responseBody)
    }
}
