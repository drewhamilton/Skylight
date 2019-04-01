package drewhamilton.skylight.sso.network

import okhttp3.ResponseBody

class ResponseException(error: ResponseBody?) :
    RuntimeException("Error retrieving Skylight info: ${error?.string()}")
