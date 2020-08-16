package com.harsha.accenture.assign.utils

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Developer Utils that are turned ON in debug builds.
 * This class helps removing debugging libraries in release builds and
 * separating debugging related code in rest of the code base.
 */
object DevUtils {

    private val networkFlipperPlugin = NetworkFlipperPlugin()

    @JvmStatic
    fun initFlipper(context: Context) {
        SoLoader.init(context, false)

        if (FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(context))
            client.addPlugin(SharedPreferencesFlipperPlugin(context, context.packageName + "_preferences"))
            client.addPlugin(networkFlipperPlugin)
            client.start()
        }
    }

    @JvmStatic
    fun addHttpLoggerInterceptor(builder: OkHttpClient.Builder){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
    }

    @JvmStatic
    fun addFlipperInterceptor(builder: OkHttpClient.Builder){
        networkFlipperPlugin?.let {
            builder.addNetworkInterceptor(FlipperOkhttpInterceptor(it))
        }
    }

    @JvmStatic
    fun addChuckerInterceptor(builder: OkHttpClient.Builder, context: Context){
        builder.addInterceptor(ChuckerInterceptor(context))
    }

}