package com.domenplus.hashgenerator

import android.os.Bundle
import android.os.Message
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.domenplus.hashgenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() =_binding!!
    private val homeViewModel:HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val adapter=ArrayAdapter(requireContext(),R.layout.drop_down,hashAlgorithms)
        binding.autoCompleteTextView.setAdapter(adapter)
        binding.button.setOnClickListener {
            onGeneratedClacked()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val adapter=ArrayAdapter(requireContext(),R.layout.drop_down,hashAlgorithms)
        binding.autoCompleteTextView.setAdapter(adapter)
    }
    private fun onGeneratedClacked(){
        if (binding.plainText.text.isEmpty()){
            showSnackBar("Field Empty")
        }else{
            lifecycleScope.launch {
                applyAnimation()
                navigateToSuccess( getHashData())
            }
        }

    }
    private fun showSnackBar(message:String){
        Snackbar.make(binding.rootLayout
                ,message

                ,Snackbar.LENGTH_SHORT)
                .setAction("Ok"){}
                .setActionTextColor(ContextCompat.getColor(requireContext(),R.color.blue))
                .show()
    }
    private suspend fun applyAnimation(){
        with(binding){
            button.isClickable=false
            titleTextView.animate().alpha(0f).duration=400L
            button.animate().alpha(0f).duration=400L
            textInputLayout.animate()
                    .alpha(0f)
                    .translationXBy(1200f)
                    .duration=400L
            plainText.animate()
                    .alpha(0f)
                    .translationXBy(-1200f)
                    .duration=400L
            delay(300)
            successBackground.animate()
                    .alpha(1f)
                    .duration=600L
            successBackground.animate()
                    .rotationBy(720f)
                    .duration=600L
            successBackground.animate()
                    .scaleXBy(900f)
                    .duration=900L
            successBackground.animate()
                    .scaleYBy(900f)
                    .duration=900L
            delay(500)
            successImageView.animate().alpha(1f).duration=1000L
            delay(1500L)

        }
    }
    private fun getHashData():String{
        val algorithum= binding.autoCompleteTextView.text.toString()
        val plainText = binding.autoCompleteTextView.text.toString()
        return homeViewModel.getHash(algorithum,plainText)
    }
    private fun navigateToSuccess(hash:String){
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSuccesFragment(hash))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.clear_menu){
            binding.plainText.text.clear()
            showSnackBar("Clear")
            return true
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}