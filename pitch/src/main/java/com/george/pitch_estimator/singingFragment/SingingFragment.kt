package com.george.pitch_estimator.singingFragment


/*Copyright 2016 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.george.pitch_estimator.ObjectBox
import com.george.pitch_estimator.PitchModelExecutor
import com.george.pitch_estimator.R
import com.george.pitch_estimator.SingRecorder
import com.george.pitch_estimator.databinding.FragmentFirstBinding
import com.george.pitch_estimator.permission.RxPermissions
import com.george.pitch_estimator.scores.Score
import io.objectbox.kotlin.boxFor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SingingFragment : Fragment(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var binding: FragmentFirstBinding

    // Koin ViewModel DI
    private val viewModel: SingingFragmentViewModel by viewModel()
    private lateinit var singRecorder: SingRecorder
    private lateinit var pitchModelExecutor: PitchModelExecutor
    val compositeDisposable = CompositeDisposable()

    // Permissions
    var PERMISSION_ALL = 123

    // App saves .wav audio file inside external storage of phone so anyone can compare
    // results with the colab notebook output. For that purpose this permission is mandatory
    var PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModelxml = viewModel

        //Check for permissions
        initialize()

        // Generate folder for saving .wav later
        generateFolder()

        // Observe notes as they come out of model and update webview respectively
        viewModel.noteValuesToDisplay.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { list ->

                if (list.size > 0) {
                    var i = 0
                    val handler = Handler()
                    handler.post(object : Runnable {
                        override fun run() {
                            all_pitches.text = all_pitches.text.toString() + list
                            when (list[i]) {
                                "C2" -> binding.webView.loadUrl("javascript:myMove('125')")
                                "C#2" -> binding.webView.loadUrl("javascript:myMoveSharp('125')")
                                "D2" -> binding.webView.loadUrl("javascript:myMove('130')")
                                "D#2" -> binding.webView.loadUrl("javascript:myMoveSharp('130')")
                                "E2" -> binding.webView.loadUrl("javascript:myMove('135')")
                                "F2" -> binding.webView.loadUrl("javascript:myMove('140')")
                                "F#2" -> binding.webView.loadUrl("javascript:myMoveSharp('140')")
                                "G2" -> binding.webView.loadUrl("javascript:myMove('145')")
                                "G#2" -> binding.webView.loadUrl("javascript:myMoveSharp('145')")
                                "A2" -> binding.webView.loadUrl("javascript:myMove('150')")
                                "A#2" -> binding.webView.loadUrl("javascript:myMoveSharp('150')")
                                "B2" -> binding.webView.loadUrl("javascript:myMove('155')")

                                "C3" -> binding.webView.loadUrl("javascript:myMove('160')")
                                "C#3" -> binding.webView.loadUrl("javascript:myMoveSharp('160')")
                                "D3" -> binding.webView.loadUrl("javascript:myMove('165')")
                                "D#3" -> binding.webView.loadUrl("javascript:myMoveSharp('165')")
                                "E3" -> binding.webView.loadUrl("javascript:myMove('170')")
                                "F3" -> binding.webView.loadUrl("javascript:myMove('175')")
                                "F#3" -> binding.webView.loadUrl("javascript:myMoveSharp('175')")
                                "G3" -> binding.webView.loadUrl("javascript:myMove('180')")
                                "G#3" -> binding.webView.loadUrl("javascript:myMoveSharp('180')")
                                "A3" -> binding.webView.loadUrl("javascript:myMove('185')")
                                "A#3" -> binding.webView.loadUrl("javascript:myMoveSharp('185')")
                                "B3" -> binding.webView.loadUrl("javascript:myMove('190')")

                                "C4" -> binding.webView.loadUrl("javascript:myMove('225')")
                                "C#4" -> binding.webView.loadUrl("javascript:myMoveSharp('225')")
                                "D4" -> binding.webView.loadUrl("javascript:myMove('230')")
                                "D#4" -> binding.webView.loadUrl("javascript:myMoveSharp('230')")
                                "E4" -> binding.webView.loadUrl("javascript:myMove('235')")
                                "F4" -> binding.webView.loadUrl("javascript:myMove('240')")
                                "F#4" -> binding.webView.loadUrl("javascript:myMoveSharp('240')")
                                "G4" -> binding.webView.loadUrl("javascript:myMove('245')")
                                "G#4" -> binding.webView.loadUrl("javascript:myMoveSharp('245')")
                                "A4" -> binding.webView.loadUrl("javascript:myMove('250')")
                                "A#4" -> binding.webView.loadUrl("javascript:myMoveSharp('250')")
                                "B4" -> binding.webView.loadUrl("javascript:myMove('255')")

                                "C5" -> binding.webView.loadUrl("javascript:myMove('260')")
                                "C#5" -> binding.webView.loadUrl("javascript:myMoveSharp('260')")
                                "D5" -> binding.webView.loadUrl("javascript:myMove('265')")
                                "D#5" -> binding.webView.loadUrl("javascript:myMoveSharp('265')")
                                "E5" -> binding.webView.loadUrl("javascript:myMove('270')")
                                "F5" -> binding.webView.loadUrl("javascript:myMove('275')")
                                "F#5" -> binding.webView.loadUrl("javascript:myMoveSharp('275')")
                                "G5" -> binding.webView.loadUrl("javascript:myMove('280')")
                                "G#5" -> binding.webView.loadUrl("javascript:myMoveSharp('280')")
                                "A5" -> binding.webView.loadUrl("javascript:myMove('285')")
                                "A#5" -> binding.webView.loadUrl("javascript:myMoveSharp('285')")
                                "B5" -> binding.webView.loadUrl("javascript:myMove('290')")

                            }
                            i++
                            if (i < list.size) {
                                handler.postDelayed(this, TIME_DELAY_FOR_NOTES)
                            }
                        }
                    })
                }
            })

        viewModel.singingEnd.observe(
            requireActivity(),
            Observer { end ->
                if (end) {
                    // Clear animation
                    binding.buttonAnimated.clearAnimation()
                } else {
                    // Start animation
                    animateSharkButton()
                }
            }
        )

        return binding.root
    }

    fun singingStopped() {
        // Execute method to stop callbacks
        viewModel.stopAllSinging()

        // Clear animation
        binding.buttonAnimated.clearAnimation()

        //Toast.makeText(activity, "Singing has stopped", Toast.LENGTH_LONG).show()
    }


    private fun animateSharkButton() {
        val animation = AnimationUtils.loadAnimation(activity, R.anim.scale_anim)
        binding.buttonAnimated.startAnimation(animation)
    }

    private fun generateFolder() {
        val root =
            File(Environment.getExternalStorageDirectory(), "Pitch Estimator")
        if (!root.exists()) {
            root.mkdirs()
        }
    }

    private fun initialize() {

        compositeDisposable.add(RxPermissions(this)
            .request(*PERMISSIONS)
            .subscribe(Consumer<Int> { integer ->
                when {
                    RxPermissions.GRANTED == integer -> {
                        viewModel.setUpdateLoopSingingHandler()
                        // Start karaoke
                        viewModel.setUpdateKaraokeHandler()
                    }
                    RxPermissions.DENIED == integer -> {
                        Toast.makeText(
                            activity,
                            getString(R.string.permissionsNotGranted),
                            Toast.LENGTH_LONG
                        ).show()
                        activity?.finish()
                    }
                    RxPermissions.NEVERASK == integer -> {
                        Toast.makeText(
                            activity,
                            getString(R.string.permissionsNotGranted),
                            Toast.LENGTH_LONG
                        ).show()

                        activity?.finish()
                    }
                }
            })
        )
    }

    override fun onDestroy() {
        if (all_pitches.text != null) {
            val text = all_pitches.text.toString()
            val score = Score()
            score.text = text
            score.time = System.currentTimeMillis()
            ObjectBox.boxStore.boxFor<Score>().put(score)
        }
        super.onDestroy()
        compositeDisposable.clear()
    }


    override fun onResume() {
        super.onResume()
        //viewModel.setNotesOnStart()
    }

    companion object {
        private const val TIME_DELAY_FOR_NOTES = 555L

        // Update interval for widget
        const val UPDATE_INTERVAL_INFERENCE = 2048L
        const val UPDATE_INTERVAL_KARAOKE = 440L
    }
}