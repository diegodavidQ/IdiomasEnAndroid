package diego.diedavq.idiomasenandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    val SHAREDLANGUAGE = "LANGUAGE"
    val NAME_PREFERENCE ="PREFERENCE"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //---------CHANGE LANGUAGE TO SAVED LANGUAGE ON SharedPreferences---//
        prefs =  getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE)
        //load language
        if (getLanguagePreferences(prefs) != null) {
            var language: String? = getLanguagePreferences(prefs)
            //get language current
            var currentLocale: String? =
                ConfigurationCompat.getLocales(resources.configuration)[0].toString()
            //if language current is diferent then change to language saved
            if (currentLocale == "es_US") { //default language maybe "es_US" that is same "es"
                currentLocale = "es"
            }
            if (language != null && language != currentLocale) {
                setAppLocale(language)
            }
        }
        //-----------------------------------------------------------------//

        btnCambiarIdioma.setOnClickListener(this) //ChangeLanguage()
    }


    //button listener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCambiarIdioma -> {
                if (getLanguagePreferences(prefs) == "es") {
                    setLanguageSharedPreferences("en")
                    // Toast.makeText(this, "Locale in English!", Toast.LENGTH_LONG).show();
                    setAppLocale("en")
                } else {
                    setLanguageSharedPreferences("es")
                    // Toast.makeText(this, "Locale en Español!", Toast.LENGTH_LONG).show();
                    setAppLocale("es")
                }
            }
        }
    }


    //method to change language
    private fun setAppLocale(localeCode: String): Unit {
        val resources: Resources = resources
        val dm: DisplayMetrics = resources.getDisplayMetrics()
        val config: Configuration = resources.getConfiguration()
        config.setLocale(Locale(localeCode.toLowerCase(Locale.ROOT)))
        resources.updateConfiguration(config, dm)
        val refreshActivity = intent
        finish()
        startActivity(refreshActivity)
    }


    private fun setLanguageSharedPreferences( language : String){
        val editor : SharedPreferences.Editor? = prefs?.edit()
        editor?.putString(SHAREDLANGUAGE, language)
        editor?.apply()
    }

    private fun getLanguagePreferences(prefs: SharedPreferences?): String? {
        var language :String? = prefs?.getString(SHAREDLANGUAGE,"es")
        return language
    }


}


