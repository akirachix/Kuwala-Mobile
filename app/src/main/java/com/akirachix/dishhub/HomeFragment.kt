
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akirachix.dishhub.AddItemManually
import com.akirachix.dishhub.DairyCategory
import com.akirachix.dishhub.FruitsCategory
import com.akirachix.dishhub.VegetablesCategory
import com.akirachix.dishhub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileImageView = binding.imageViewProfile
        profileImageView.setOnClickListener {
            if (activity != null) {
                val intent = Intent(activity, Profile::class.java) // Correct way
                startActivity(intent)
            }
        }
        val addButton = binding.buttonAddItem
        addButton?.setOnClickListener {
            startActivity(Intent(activity, AddItemManually::class.java))
        }

        val fruit = binding.fruits
        fruit?.setOnClickListener {
            startActivity(Intent(activity, FruitsCategory::class.java))
        }

        val dairy = binding.imageView5


        dairy?.setOnClickListener {
            startActivity(Intent(activity, DairyCategory::class.java))
        }

        val vegetables = binding.imageView4
        vegetables?.setOnClickListener {
            startActivity(Intent(activity, VegetablesCategory::class.java))
        }

        val grain = binding.imageView6
        grain?.setOnClickListener {
            startActivity(Intent(activity, GrainsAdapter::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}