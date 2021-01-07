package com.domenplus.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.domenplus.hashgenerator.databinding.FragmentSuccesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment() {
    private val  args : SuccessFragmentArgs by navArgs()
    private var _binding:FragmentSuccesBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentSuccesBinding.inflate(inflater, container, false)
        binding.hashTextView.text = args.hash
        binding.copyButton.setOnClickListener{
            onCopyClicked(args.hash)
        }

        return binding.root
    }

    private fun onCopyClicked(hash: String) {
        lifecycleScope.launch {
            applyAnimation()
        }
        val clipBordManager=requireActivity().getSystemService(Context.CLIPBOARD_SERVICE)  as ClipboardManager
        val clipData=ClipData.newPlainText("HASH",hash)
        clipBordManager.setPrimaryClip(clipData)
    }
    private suspend fun applyAnimation(){
        binding.include.messageBackground.animate()
                .translationY(80f)
                .duration=200

        binding.include.messageTextView.animate()
                .translationY(80F)
                .duration=200
        delay(2000L)
        binding.include.messageBackground.animate()
                .translationY(-80f)
                .duration=500

        binding.include.messageTextView.animate()
                .translationY(-80F)
                .duration=500

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}