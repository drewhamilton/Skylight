package drewhamilton.skylight.sample.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import drewhamilton.skylight.sample.R

@Deprecated(
    "Skylight sample for Android has moved to https://github.com/drewhamilton/SkylightAndroid/tree/master/app"
)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_destination)
    }
}
