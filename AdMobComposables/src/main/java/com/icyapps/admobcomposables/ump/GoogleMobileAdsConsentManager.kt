package com.icyapps.admobcomposables.ump

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform

class GoogleMobileAdsConsentManager private constructor(context: Context) {
    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(context)


    fun interface OnConsentCheckCompleteListener {
        fun consentCheckComplete(canRequestAds: Boolean, error: FormError?)
    }

    fun interface OnConsentGatheringCompleteListener {
        fun consentGatheringComplete(canRequestAds: Boolean, error: FormError?)
    }

    val canRequestAds: Boolean
        get() = consentInformation.canRequestAds()

    val isPrivacyOptionsRequired: Boolean
        get() =
            consentInformation.privacyOptionsRequirementStatus ==
                    ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

    fun checkConsent(
        context: Context,
        onConsentCheckCompleteListener: OnConsentCheckCompleteListener
    ) {
        val params = ConsentRequestParameters.Builder().build()

        consentInformation.requestConsentInfoUpdate(
            context as Activity,
            params,
            {
                onConsentCheckCompleteListener.consentCheckComplete(canRequestAds, null)
            },
            { requestConsentError ->
                onConsentCheckCompleteListener.consentCheckComplete(false, requestConsentError)
            }
        )
    }


    fun gatherConsent(
        activity: Activity,
        onConsentGatheringCompleteListener: OnConsentGatheringCompleteListener
    ) {
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .build()

        val params =
            ConsentRequestParameters.Builder().setConsentDebugSettings(debugSettings).build()

        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    if (formError != null) {
                        onConsentGatheringCompleteListener.consentGatheringComplete(
                            false,
                            formError
                        )
                    } else {
                        onConsentGatheringCompleteListener.consentGatheringComplete(
                            canRequestAds,
                            null
                        )
                    }
                }
            },
            { requestConsentError ->
                onConsentGatheringCompleteListener.consentGatheringComplete(
                    false,
                    requestConsentError
                )
            }
        )
    }

    companion object {
        @Volatile
        private var instance: GoogleMobileAdsConsentManager? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: GoogleMobileAdsConsentManager(context).also { instance = it }
            }
    }
}