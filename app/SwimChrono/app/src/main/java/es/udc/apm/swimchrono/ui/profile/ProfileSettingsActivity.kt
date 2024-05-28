package es.udc.apm.swimchrono.ui.profile

import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.ConfigurationCompat
import es.udc.apm.swimchrono.BaseActivity
import es.udc.apm.swimchrono.R
import es.udc.apm.swimchrono.util.LocaleUtil
import java.util.Locale


class ProfileSettingsActivity : BaseActivity() {

    private lateinit var firstLocaleCode: String
    private lateinit var secondLocaleCode: String
    private lateinit var currentSystemLocaleCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        val buttonExit = findViewById<ImageView>(R.id.ivBackButton)
        val textViewLanguageTitle = findViewById<TextView>(R.id.textViewLanguageTitle)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupLanguage)

        currentSystemLocaleCode =
            (ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)?.language
                ?: if (storage.getPreferredLocale() == LocaleUtil.OPTION_PHONE_LANGUAGE) {
                    if (currentSystemLocaleCode in LocaleUtil.supportedLocales) {
                        textViewLanguageTitle.text = R.string.espanol.toString()

                    } else {
                        textViewLanguageTitle.text = R.string.english.toString()
                    }
                } else {
                    if (currentSystemLocaleCode == storage.getPreferredLocale()) {
                        textViewLanguageTitle.text = R.string.english.toString()
                    } else {
                        textViewLanguageTitle.text =
                            Locale(storage.getPreferredLocale()).displayLanguage
                    }
                }).toString()
        firstLocaleCode = if (currentSystemLocaleCode in LocaleUtil.supportedLocales) {
            currentSystemLocaleCode
        } else {
            if (storage.getPreferredLocale() == LocaleUtil.OPTION_PHONE_LANGUAGE) {
                "en"
            } else {
                storage.getPreferredLocale()
            }
        }
        secondLocaleCode = getSecondLanguageCode()
        initRadioButtonUI()
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonEnglish -> {
                    updateAppLocale(LocaleUtil.OPTION_PHONE_LANGUAGE)
                    recreate()
                }

                R.id.radioButtonSpanish -> {
                    updateAppLocale(secondLocaleCode)
                    recreate()
                }
            }
        }
        buttonExit.setOnClickListener {
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getSecondLanguageCode(): String {
        return if (firstLocaleCode == "en") "es" else "en"
    }

    private fun initRadioButtonUI() {


        val radioButtonEnglish = findViewById<RadioButton>(R.id.radioButtonEnglish)
        val radioButtonSpanish = findViewById<RadioButton>(R.id.radioButtonSpanish)

        if (currentSystemLocaleCode in LocaleUtil.supportedLocales) {
            radioButtonEnglish.text = getLanguageNameByCode(firstLocaleCode).uppercase()
        } else {
            radioButtonEnglish.text = getLanguageNameByCode(firstLocaleCode).uppercase()
        }
        radioButtonSpanish.text = getLanguageNameByCode(secondLocaleCode).uppercase()
        if (storage.getPreferredLocale() == secondLocaleCode) radioButtonSpanish.isChecked = true
        else radioButtonEnglish.isChecked = true
    }

    private fun getLanguageNameByCode(code: String): String {
        val tempLocale = Locale(code)
        return tempLocale.getDisplayLanguage(tempLocale)
    }

    private fun updateAppLocale(locale: String) {
        storage.setPreferredLocale(locale)
        LocaleUtil.applyLocalizedContext(applicationContext, locale)
    }
}