package com.example.githubuser.adapters
import com.example.githubuser.databinding.ItemUserFollowsBinding
import com.example.githubuser.api.ClickUser
import com.example.githubuser.application.appdetails.DetailUserActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R

class FragmentAdapter :
    RecyclerView.Adapter<FragmentAdapter.MyViewHolder>() {
    private var githubUsers = ArrayList<ClickUser>()
    private lateinit var userClickBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) { this.userClickBack = onItemClickCallback }

    interface OnItemClickCallback { fun onItemClicked(user: ClickUser) }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(items: ArrayList<ClickUser>) {
        githubUsers.clear()
        githubUsers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ItemUserFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(githubUsers[position])
        holder.itemView.setOnClickListener { userClickBack.onItemClicked(githubUsers[position]) }

    }

    override fun getItemCount() = githubUsers.size

    class MyViewHolder(private var _isBinding: ItemUserFollowsBinding) :
        RecyclerView.ViewHolder(_isBinding.root) {
        fun bind(userResponse: ClickUser) {
            _isBinding.name.text = userResponse.login
            Glide.with(_isBinding.root)
                .load(userResponse.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.loading_icon)
                        .error(R.drawable.bg_error_theme))
                .circleCrop()
                .into(_isBinding.circleImageView)
            _isBinding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.KEY_USER, userResponse)
                itemView.context.startActivity(intent)
            } } }

}