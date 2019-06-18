package com.oubouhou.githubrepos.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oubouhou.githubrepos.Model.RepoItem;
import com.oubouhou.githubrepos.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private List<RepoItem> repos;
    private Context mContext;

    public RepoAdapter(Context mContext, List<RepoItem> repos){
        this.mContext = mContext;
        this.repos = repos;
    }
    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View reposView = LayoutInflater.from(mContext).inflate(R.layout.repo_item, viewGroup, false);
        return new RepoViewHolder(reposView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder repoViewHolder, int i) {
        RepoItem currentRepoItem = repos.get(i);

        String repo_owner = currentRepoItem.getRepo_owner();
        String repo_name = currentRepoItem.getRepo_name();
        String repo_desc = currentRepoItem.getRepo_desc();
        String avatar_url = currentRepoItem.getRepo_avatarUrl();
        String repo_rating = currentRepoItem.getRepo_rating();

        repoViewHolder.repoOwner.setText(repo_owner);
        repoViewHolder.repoName.setText(repo_name);
        repoViewHolder.repodescr.setText(repo_desc);
        repoViewHolder.repoRating.setText(repo_rating);
        Picasso.with(mContext).load(avatar_url).fit().centerInside().into(repoViewHolder.repoOwnerAvatar);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder{
        public TextView repoName;
        public TextView repoOwner;
        public TextView repodescr;
        public ImageView repoOwnerAvatar;
        public TextView repoRating;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repoOwner = itemView.findViewById(R.id.owner);
            repoName = itemView.findViewById(R.id.repo_name);
            repodescr = itemView.findViewById(R.id.repo_desc);
            repoOwnerAvatar = itemView.findViewById(R.id.avatar);
            repoRating = itemView.findViewById(R.id.star_rating);
        }
    }

}
