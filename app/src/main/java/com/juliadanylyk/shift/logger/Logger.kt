package com.juliadanylyk.shift.logger

interface Logger {
    /**
     * Developers stuff.
     */
    fun d(message: String)

    /**
     * The process might be continued, but take extra caution.
     */
    fun w(message: String)

    /**
     * The process might be continued, but take extra caution.
     */
    fun w(message: String, cause: Throwable)

    /**
     * Something terribly wrong had happened, that must be investigated immediately.
     */
    fun e(message: String)

    /**
     * Something terribly wrong had happened, that must be investigated immediately.
     */
    fun e(message: String, cause: Throwable)
}