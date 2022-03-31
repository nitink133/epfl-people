package ch.epfl.people.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.epfl.people.databinding.FragmentPeopleInfoBinding
import co.allcommerce.myservice.base.fragment.BaseFragment


/**
 * @author Nitin Khanna
 * @date 31/03/2022 11:50PM
 * @song https://open.spotify.com/track/3Kh9bkKyT35NIfiK24uPdo?si=fe894285c19a4c0d
 */
class PeopleInfoFragment : BaseFragment() {
    private lateinit var binding: FragmentPeopleInfoBinding

    override fun showProgress(msg: String?) {
        //ignore
    }

    override fun hideProgress() {
        //ignore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPeopleInfoBinding.inflate(inflater)
        return binding.root
    }
}