package com.bku.musicandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bku.musicandroid.R;
import com.bku.musicandroid.Activity.SongOnlinePlayerActivity;
import com.bku.musicandroid.Model.SongPlayerOnlineInfo;
import com.bku.musicandroid.Utility.UtilitySongOnlineClass;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Welcome on 4/29/2018.
 */

public class SongGenreRecycleAdapter extends RecyclerView.Adapter<SongGenreRecycleAdapter.ViewHolder> {

    static Context context;
    ArrayList<SongPlayerOnlineInfo> ListSong;
    public final String USER_DATABASE="All_Users_Info_Database";
    public SongGenreRecycleAdapter(Context context, ArrayList<SongPlayerOnlineInfo>listSong){

        this.ListSong=listSong;
        this.context=context;
    }

    @Override
    public SongGenreRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_online_list, parent, false);

        SongGenreRecycleAdapter.ViewHolder viewHolder = new SongGenreRecycleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongGenreRecycleAdapter.ViewHolder holder, int position) {
        final SongPlayerOnlineInfo songPlayerOnlineInfo=ListSong.get(position);
        holder.nameArtist.setText(songPlayerOnlineInfo.getSongArtists());
        Glide.with(context).load(songPlayerOnlineInfo.getImageSongURL()).into(holder.songImage);
        holder.nameSong.setText(songPlayerOnlineInfo.getSongName());
        holder.userUpload.setText(songPlayerOnlineInfo.getUserName());
        String strTemp="Listen : "+songPlayerOnlineInfo.getView();
        holder.ViewListen.setText(strTemp);
        strTemp="DownLoad : "+songPlayerOnlineInfo.getDownload();
        holder.DownLoad.setText(strTemp);
        strTemp="Liked : "+songPlayerOnlineInfo.getLiked();
        holder.Liked.setText(strTemp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilitySongOnlineClass utilitySongOnlineClass=UtilitySongOnlineClass.getInstance();
//                utilitySongOnlineClass.setItem(songPlayerOnlineInfo);
                utilitySongOnlineClass.setItemOfList(ListSong);
                Intent intent=new Intent(context,SongOnlinePlayerActivity.class);
                intent.putExtra("currentPosition",ListSong.indexOf(songPlayerOnlineInfo));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ListSong.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView songImage;
        public TextView nameSong,userUpload,nameArtist,DownLoad,Liked,ViewListen;
        public ViewHolder(View itemView) {
            super(itemView);

            songImage=(ImageView)itemView.findViewById(R.id.songImage);
            nameArtist=(TextView)itemView.findViewById(R.id.nameArtist);
            nameSong=(TextView)itemView.findViewById(R.id.nameSong);
            userUpload=(TextView)itemView.findViewById(R.id.userUpload);
            DownLoad=(TextView)itemView.findViewById(R.id.DownLoad);
            Liked=(TextView)itemView.findViewById(R.id.Liked);
            ViewListen=(TextView)itemView.findViewById(R.id.ViewListen);

        }
    }
}
