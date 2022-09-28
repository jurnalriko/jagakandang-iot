package org.d3if43.jagakandang

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.d3if43.jagakandang.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        updateUI()
        binding.gmailLayout.setOnClickListener { openGmail() }
        binding.whatsappLayout.setOnClickListener { openWA() }
        binding.gmapsLayout.setOnClickListener { openMaps() }
        binding.buttonSignOut.setBackgroundColor(Color.parseColor("#a3524e"))
        binding.buttonSignOut.setOnClickListener { signOut() }
        binding.copyButton.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied to Clipboard", binding.infoUidTextView.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied to Clipboard", Toast.LENGTH_LONG).show()
        }



    }
    private fun updateUI() {
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            binding.namaTextView.text = user.displayName?.uppercase()
            binding.infoNamaTextView.text = user.displayName
            binding.infoEmailTextView.text = user.email
            binding.infoUidTextView.text = user.uid
            Glide.with(this).load(user.photoUrl).into(binding.imageView)
        } else {

        }
        // [END check_current_user]
    }



    private fun openWA(){
        val num = "6281905633034"
        val msg = "Hello, Can I Ask Something?"
        val whatsapp = Intent(Intent.ACTION_VIEW)
        whatsapp.data = Uri.parse("https://api.whatsapp.com/send?phone="+num+"&text="+msg)
        startActivity(whatsapp)
    }

    private fun openGmail(){
        val emailIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", "jagakandang29@gmail.com", null))
        startActivity(emailIntent)
    }

    private fun openMaps(){
        val mapsIntent = Intent(Intent.ACTION_VIEW)
        mapsIntent.data = Uri.parse("https://g.page/telkomuniversity-bandung?share")
        startActivity(mapsIntent)
    }

    private fun signOut(){
        AlertDialog.Builder(this).apply{
            setMessage(R.string.signout_message)
            setPositiveButton(R.string.ya) { _, _ ->
                Firebase.auth.signOut()
                finishAffinity()
                startActivity(Intent(this@InfoActivity, LoginActivity::class.java))
            }
            setNegativeButton(R.string.tidak) { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }

}