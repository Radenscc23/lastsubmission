package com.example.githubuser.application.sections.fragmentsection
import com.example.githubuser.adapters.FragmentAdapter
import com.example.githubuser.databinding.UserFragmentsBinding
import com.example.githubuser.api.ClickUser
import com.example.githubuser.application.appdetails.DetailUserActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager


class FollowsFragment : Fragment() {
    private var _isBinding: UserFragmentsBinding? = null
    private val userBinding get() = _isBinding!!
    private val chooseAdapter: FragmentAdapter by lazy {
        FragmentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _isBinding = UserFragmentsBinding.inflate(inflater, container, false)
        return userBinding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val user = arguments?.getParcelable<ClickUser>(ARG_PARCEL)

        if (index == 1) {
            if (user != null) {
                user.login?.let {
                    val mIndex = 1
                    setViewModel(it, mIndex)
                }
            }
        } else {
            if (user != null) {
                user.login?.let {
                    val mIndex = 2
                    setViewModel(it, mIndex)
                }
            }
        }
    }
    private fun showLoading(state: Boolean){
        if (state){
            userBinding.progressBar.visibility = View.VISIBLE
        } else {
            userBinding.progressBar.visibility = View.GONE
        } }
    private fun setViewModel(username: String, index: Int) {

        val followsViewModel: FragmentModel by viewModels {
            FragmentFactory(username) }
        followsViewModel.userLoading.observe(viewLifecycleOwner, { showLoading(it) })
        followsViewModel.userDataFailed.observe(viewLifecycleOwner, { showLoading(it) })
        if (index == 1) {
            followsViewModel.followerList.observe(viewLifecycleOwner, { follResponse ->
                follResponse?.let {
                    if (follResponse.isEmpty()) {
                        userBinding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(
                            context,
                            "NOT FOLLOWERS",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        userBinding.progressBar.visibility = View.GONE
                        chooseAdapter.addDataToList(follResponse)
                        setUserData(follResponse)
                    }
                }
            })
        } else {
            followsViewModel.followingList.observe(viewLifecycleOwner, { follResponse ->
                follResponse?.let{
                    if (follResponse.isEmpty()) {
                        userBinding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(
                            context,
                            "NOT FOLLOWERS",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        userBinding.progressBar.visibility = View.GONE
                        chooseAdapter.addDataToList(follResponse)
                        setUserData(follResponse) } } }) } }

    private fun setUserData(userResponse: ArrayList<ClickUser>) {
        if (userResponse.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(view?.context)
            userBinding.rvFollows.layoutManager = layoutManager
            userBinding.rvFollows.adapter = chooseAdapter
            chooseAdapter.setOnItemClickCallback(object : FragmentAdapter.OnItemClickCallback {
                override fun onItemClicked(user: ClickUser) {
                    val intent = Intent(context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.KEY_USER, user)
                    intent.putExtra(DetailUserActivity.KEY_USERNAME, user.login)
                    intent.putExtra(DetailUserActivity.KEY_ID, user.id)
                    startActivity(intent)
                } }) } }


    override fun onDestroy() {
        super.onDestroy()
        _isBinding = null
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_PARCEL = "user_model"

        @JvmStatic
        fun newInstance(index: Int, userResponse: ClickUser?) =
            FollowsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putParcelable(ARG_PARCEL, userResponse)
                }
            } }


}