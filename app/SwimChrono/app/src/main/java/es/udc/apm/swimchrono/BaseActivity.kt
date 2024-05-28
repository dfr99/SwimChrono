package es.udc.apm.swimchrono

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.udc.apm.swimchrono.util.LocaleUtil
import es.udc.apm.swimchrono.util.Storage

open class BaseActivity :
    AppCompatActivity() {
    private lateinit var oldPrefLocaleCode: String

    protected open val storage: Storage by lazy {
        Storage(this)
    }

    /**
     * updates the toolbar text locale if it set from the android:label property of Manifest
     */
    private fun resetTitle() {
        try {
            val label = packageManager.getActivityInfo(
                componentName,
                PackageManager.GET_META_DATA
            ).labelRes;
            if (label != 0) {
                setTitle(label);
            }
        } catch (e: PackageManager.NameNotFoundException) {
        }
    }

    override fun attachBaseContext(newBase: Context) {
        oldPrefLocaleCode = Storage(newBase).getPreferredLocale()
        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode))
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetTitle()
    }

    override fun onResume() {
        val currentLocaleCode = Storage(this).getPreferredLocale()
        if (oldPrefLocaleCode != currentLocaleCode) {
            recreate() //locale is changed, restart the activty to update
            oldPrefLocaleCode = currentLocaleCode
        }
        super.onResume()
    }
}