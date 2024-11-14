// SplashActivity.kt
package ipn.mx.loginmovil

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ipn.mx.loginmovil.ui.theme.auth.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Encuentra el logotipo y aplica la animación
        val logo = findViewById<ImageView>(R.id.logo)
        val fadeInScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale)
        logo.startAnimation(fadeInScaleAnimation)

        // Espera un tiempo antes de iniciar la siguiente actividad
        Handler().postDelayed({
            // Inicia la actividad de login después de la animación
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finaliza la SplashActivity para que no se pueda regresar a ella
        }, 2000) // 2000 ms = 2 segundos
    }
}
