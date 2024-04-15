package com.example.githubuser.adapters
import com.example.githubuser.repo.Favorite
import com.example.githubuser.databinding.ItemUserFollowsBinding
import com.example.githubuser.application.appdetails.DetailUserActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SettingAdapter : RecyclerView.Adapter<SettingAdapter.MyViewHolder>() {
    private val followedUser = ArrayList<Favorite>()

    private lateinit var userclickBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) { this.userclickBack = onItemClickCallback }

    interface OnItemClickCallback { fun onItemClicked(favEntity: Favorite) }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            ItemUserFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView) }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(followedUser[position])
        holder.itemView.setOnClickListener { userclickBack.onItemClicked(followedUser[position]) } }

    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(items: List<Favorite>) {
        followedUser.clear()
        followedUser.addAll(items)
        notifyDataSetChanged() }

    override fun getItemCount() = followedUser.size
    class MyViewHolder(private val binding: ItemUserFollowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favEntity: Favorite) {
            with(binding) {
                name.text = favEntity.login
                Glide.with(root)
                    .load(favEntity.avatar_url)
                    .circleCrop()
                    .into(binding.circleImageView)
                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.KEY_USER, favEntity)
                    itemView.context.startActivity(intent) } } } }


}