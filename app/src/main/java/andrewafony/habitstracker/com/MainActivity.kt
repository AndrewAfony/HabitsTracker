package andrewafony.habitstracker.com

import andrewafony.habitstracker.com.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = (application as ProvideViewModel).provideViewModel()

        viewModel.observe(this) { state ->
            state.apply(binding.daysNumber, binding.buttonReset)
        }

        binding.buttonReset.setOnClickListener {
            viewModel.reset()
        }

        viewModel.init(savedInstanceState == null)
    }
}