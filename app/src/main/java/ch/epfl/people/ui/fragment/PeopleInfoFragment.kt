package ch.epfl.people.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import ch.epfl.people.R
import ch.epfl.people.databinding.FragmentPeopleInfoBinding
import ch.epfl.people.extension.makeVisible
import ch.epfl.people.extension.setUnderLineText
import ch.epfl.people.navigation.AppNavigation
import ch.epfl.people.network.base.model.Response
import ch.epfl.people.network.people.model.People
import ch.epfl.people.ui.viewmodel.PeopleInfoViewModel
import ch.epfl.people.utils.functional.AnimationUtils
import ch.epfl.people.utils.functional.ToastUtils
import co.allcommerce.myservice.base.fragment.BaseFragment
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author Nitin Khanna
 * @date 31/03/2022 11:50PM
 * @song https://open.spotify.com/track/3Kh9bkKyT35NIfiK24uPdo?si=fe894285c19a4c0d
 */
@AndroidEntryPoint
class PeopleInfoFragment : BaseFragment() {
    private lateinit var binding: FragmentPeopleInfoBinding
    private val viewModel by viewModels<PeopleInfoViewModel>()
    private val args by navArgs<PeopleInfoFragmentArgs>()

    @Inject
    lateinit var appNavigation: AppNavigation

    override fun showProgress(msg: String?) {
        AnimationUtils.hideView(binding.rootContent)
        AnimationUtils.showView(binding.layoutProgressBar.progressBar)
    }

    override fun hideProgress() {
        AnimationUtils.showView(binding.rootContent)
        AnimationUtils.hideView(binding.layoutProgressBar.progressBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPeopleInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        viewModel.getPeopleInfo(args.id)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.responseStateForPeopleInfo.collect { responseState ->
                    when (responseState) {
                        is Response.Initial -> {
                            hideProgress()
                        }
                        is Response.Loading -> {
                            showProgress("")
                        }
                        is Response.Success -> {
                            hideProgress()
                            initUi(responseState.data)
                        }
                        is Response.IsEmpty -> {
                            hideProgress()
                            activity?.onBackPressed()
                        }
                        is Response.Error -> {
                            ToastUtils.showMessage(context, responseState.error.message)
                        }
                    }
                }
            }
        }
    }

    private fun initUi(data: People) {
        binding.apply {
            ivImage.load(data.imageUrl)
            tvName.text = data.name
            tvBiography.text = data.biography
            viewBiography.makeVisible(!data.biography.isNullOrEmpty())
            tvCitizenship.text = data.citizenship
            viewCitizenship.makeVisible(!data.citizenship.isNullOrEmpty())
            tvContactNumber.setUnderLineText(data.contactNumber)
            viewContactNumber.makeVisible(!data.contactNumber.isNullOrEmpty())
            tvEmail.setUnderLineText(data.email)
            viewEmail.makeVisible(!data.email.isNullOrEmpty())
            tvFieldOfExpertise.text = data.fieldOfExpertise
            viewFieldOfExpertise.makeVisible(!data.fieldOfExpertise.isNullOrEmpty())
            tvMission.text = data.mission
            viewMission.makeVisible(!data.mission.isNullOrEmpty())
            binding.ivLinkedin.makeVisible(!data.linkedinProfile.isNullOrEmpty())
            binding.ivTwitter.makeVisible(!data.twitterProfile.isNullOrEmpty())
            binding.ivWebsite.makeVisible(!data.website.isNullOrEmpty())
            binding.tvPosition.text = "(${data.position})"
            binding.tvPosition.makeVisible(!data.position.isNullOrEmpty())
        }

    }

    private fun initListeners() {
        binding.ivLinkedin.setOnClickListener {
            if(viewModel.responseStateForPeopleInfo.value !is Response.Success)return@setOnClickListener
            appNavigation.openChromeTab(
                activity = activity as AppCompatActivity?,
                CustomTabsIntent.Builder().setToolbarColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                ).build(),
                Uri.parse((viewModel.responseStateForPeopleInfo.value as Response.Success).data.linkedinProfile)
            )
        }

        binding.ivTwitter.setOnClickListener {
            if(viewModel.responseStateForPeopleInfo.value !is Response.Success)return@setOnClickListener
            appNavigation.openChromeTab(
                activity = activity as AppCompatActivity?,
                CustomTabsIntent.Builder().setToolbarColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                ).build(),
                Uri.parse((viewModel.responseStateForPeopleInfo.value as Response.Success).data.twitterProfile)
            )
        }

        binding.ivWebsite.setOnClickListener {
            if(viewModel.responseStateForPeopleInfo.value !is Response.Success)return@setOnClickListener
            appNavigation.openChromeTab(
                activity = activity as AppCompatActivity?,
                CustomTabsIntent.Builder().setToolbarColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                ).build(),
                Uri.parse((viewModel.responseStateForPeopleInfo.value as Response.Success).data.website)
            )
        }

    }
}