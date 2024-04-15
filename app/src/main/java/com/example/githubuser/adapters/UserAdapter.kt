package com.example.githubuser.adapters
import com.example.githubuser.R
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.api.ClickUser
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class UserAdapter :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private val githubUsers = ArrayList<ClickUser>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(githubUsers[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(githubUsers[position]) }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(items: ArrayList<ClickUser>) {
        githubUsers.clear()
        githubUsers.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = githubUsers.size

    class MyViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userResponse: ClickUser) {
            binding.name.text = userResponse.login
            Glide.with(binding.root)
                .load(userResponse.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.loading_icon)
                        .error(R.drawable.bg_error_theme)
                )
                .into(binding.circleImageView)

        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
