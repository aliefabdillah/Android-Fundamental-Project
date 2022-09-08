package com.dicoding.myquote

import cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier
import java.io.IOException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLException
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocket

/*
* CLASS UNTUK MENGTASI ERROR SSL CERTIFICATE*/
class CustomHostnameVerifier : X509HostnameVerifier {
    @Throws(IOException::class)
    override fun verify(host: String, ssl: SSLSocket) {
    }

    @Throws(SSLException::class)
    override fun verify(host: String, cert: X509Certificate) {
    }

    @Throws(SSLException::class)
    override fun verify(host: String, cns: Array<String>, subjectAlts: Array<String>) {
    }

    override fun verify(s: String, sslSession: SSLSession): Boolean {
        return false
    }
}