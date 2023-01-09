package andrewafony.habitstracker.com

import andrewafony.habitstracker.com.databinding.ActivityMainBinding
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("main", Context.MODE_PRIVATE)
        val time = preferences.getLong("time", -1)
        if (time == -1L) {
            preferences.edit().putLong("time", System.currentTimeMillis()).apply()
            binding.daysNumber.text = "0"
        } else {
            val diff = (System.currentTimeMillis() - time) / 1000
            val days = diff / (24 * 3600)
            binding.daysNumber.text = days.toString()
        }

        binding.buttonReset.setOnClickListener {
            preferences.edit().putLong("time", System.currentTimeMillis()).apply()
            binding.daysNumber.text = "0"
        }
    }
}