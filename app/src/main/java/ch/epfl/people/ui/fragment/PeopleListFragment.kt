package ch.epfl.people.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import ch.epfl.people.databinding.FragmentPeopleListBinding
import ch.epfl.people.network.base.model.Response
import ch.epfl.people.network.people.model.People
import ch.epfl.people.ui.adapter.PeoplesListAdapter
import ch.epfl.people.ui.viewmodel.PeopleListViewModel
import ch.epfl.people.utils.functional.AnimationUtils
import ch.epfl.people.utils.functional.ToastUtils
import co.allcommerce.myservice.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author Nitin Khanna
 * @date 31/03/2022 11:50PM
 * @song https://open.spotify.com/track/3Kh9bkKyT35NIfiK24uPdo?si=fe894285c19a4c0d
 */
@AndroidEntryPoint
class PeopleListFragment : BaseFragment() {
    private lateinit var binding: FragmentPeopleListBinding
    private val viewModel by viewModels<PeopleListViewModel>()
    private val mAdapter: PeoplesListAdapter by lazy {
        PeoplesListAdapter(arrayListOf(), mAdapterCallbacks)
    }

    private val mAdapterCallbacks: ((position: Int) -> Unit) = {
        //ignore
    }


    override fun showProgress(msg: String?) {
        AnimationUtils.hideView(binding.swipeRefresh)
        AnimationUtils.showView(binding.layoutProgressBar.progressBar)
    }

    override fun hideProgress() {
        AnimationUtils.showView(binding.swipeRefresh)
        AnimationUtils.hideView(binding.layoutProgressBar.progressBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPeopleListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.responseStateForPeoplesList.collect { responseState ->
                    when (responseState) {
                        is Response.Initial -> {
                            hideProgress()
                        }
                        is Response.Loading -> {
                            showProgress("")
                        }
                        is Response.Success<*> -> {
                            hideProgress()
                            mAdapter.addAll(responseState.data as ArrayList<People>)
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

    private fun initUi() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

}