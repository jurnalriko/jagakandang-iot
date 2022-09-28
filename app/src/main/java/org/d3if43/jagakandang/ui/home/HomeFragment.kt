package org.d3if43.jagakandang.ui.home


import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.d3if43.jagakandang.*
import org.d3if43.jagakandang.data.HomeDataClass
import org.d3if43.jagakandang.data.HomeDb
import org.d3if43.jagakandang.data.IotDb
import org.d3if43.jagakandang.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment(), HomeDialog.DialogListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var myAdapter: HomeAdapter

    private val viewModel: HomeViewModel by lazy {
        val dataSource = HomeDb.getInstance(requireActivity()).dao
        val factory = HomeViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }
    private lateinit var data: HomeDataClass
    private val handler = object : HomeAdapter.ClickHandler {
        override fun onClick(position: Int, homeDataClass: HomeDataClass) {
            data = homeDataClass
            if (myAdapter.getSelection().isNotEmpty()) {
                myAdapter.toggleSelection(position)
                if (myAdapter.getSelection().isEmpty()) {
                    myAdapter.resetSelection()
                    binding.deleteButton.visibility = View.GONE
                    binding.buttonTambah.visibility = View.VISIBLE
                    return
                } else
                    binding.deleteButton.visibility = View.VISIBLE
                binding.buttonTambah.visibility = View.GONE
                return
            } else
                myAdapter.toggleSelection(position)
            myAdapter.getSelection().first()
            val intent = Intent(requireContext(), DetailHomeActivity::class.java)
            intent.putExtra("HOME_DATA", data)
            startActivity(intent)
            myAdapter.resetSelection()
            binding.deleteButton.visibility = View.GONE
            binding.buttonTambah.visibility = View.VISIBLE
            return
        }

        override fun onLongClick(position: Int, homeDataClass: HomeDataClass): Boolean {
            data = homeDataClass
            if (myAdapter.getSelection().isNotEmpty()) {
                myAdapter.resetSelection()
                binding.deleteButton.visibility = View.GONE
                binding.buttonTambah.visibility = View.VISIBLE
                return false
            } else
                myAdapter.toggleSelection(position)
            binding.deleteButton.visibility = View.VISIBLE
            binding.buttonTambah.visibility = View.GONE
            return true
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTime()
        binding.buttonTambah.setOnClickListener {
            HomeDialog().show(childFragmentManager, "HomeDialog")
        }
        myAdapter = HomeAdapter(handler)
        with(binding.homeRecyclerView) {
            setHasFixedSize(true)
            adapter = myAdapter
        }
        binding.deleteButton.setOnClickListener {
            Log.d("HomeFragment", "Delete Clicked!" + myAdapter.getSelection())
            deleteData()
        }

        viewModel.data.observe(this, {
            myAdapter.submitList(it)
            binding.emptyTextView.visibility = if (it.isEmpty()) View.VISIBLE
            else View.GONE
        })
    }

    override fun onStart() {
        super.onStart()
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            binding.textUser.text = user.displayName?.uppercase()
        } else {
            // No user is signed in
        }
        // [END check_current_user]
    }


    private fun getTime() {
        val calendar = Calendar.getInstance()
        val time = calendar.get(Calendar.HOUR_OF_DAY)

        if (time >= 0 && time < 12) {
            binding.textTime.setText(R.string.selamat_pagi)
        } else if (time >= 12 && time < 15) {
            binding.textTime.setText(R.string.selamat_siang)
        } else if (time >= 15 && time < 18) {
            binding.textTime.setText(R.string.selamat_sore)
        } else {
            binding.textTime.setText(R.string.selamat_malam)
        }

    }

    override fun processDialog(homeDataClass: HomeDataClass) {
        viewModel.insertData(homeDataClass)
    }


    private fun deleteData() = AlertDialog.Builder(activity).apply {
        setMessage(R.string.delete_message)
        setPositiveButton(R.string.hapus) { _, _ ->
            viewModel.deleteData(myAdapter.getSelection())
            binding.deleteButton.visibility = View.GONE
            binding.buttonTambah.visibility = View.VISIBLE
            myAdapter.resetSelection()
        }
        setNegativeButton(R.string.batal) { dialog, _ ->
            dialog.cancel()
            binding.deleteButton.visibility = View.GONE
            binding.buttonTambah.visibility = View.VISIBLE
            myAdapter.resetSelection()
        }
        show()


    }


}