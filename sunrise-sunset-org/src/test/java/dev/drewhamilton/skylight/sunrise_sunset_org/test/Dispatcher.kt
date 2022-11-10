package dev.drewhamilton.skylight.sunrise_sunset_org.test

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

@Suppress("TestFunctionName")
internal fun Dispatcher(dispatch: (RecordedRequest) -> MockResponse) = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse = dispatch.invoke(request)
}