//package com.vego.vego.Adapters;
////dev.edmt.expandablerecyclerdemo.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//
//import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
//import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
//import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
//import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
//import com.vego.vego.Fragment.TrackProgressFragmentUser;
//import com.vego.vego.R;
//import com.vego.vego.model.Artist;
//
//import java.util.List;
//
//
//public class ExpandableAdapter extends ExpandableRecyclerViewAdapter<GenreViewHolder, ArtistViewHolder> {
//
//    public ExpandableAdapter(List<? extends ExpandableGroup> groups) {
//        super(groups);
//    }
//
//    @Override
//    public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item_genre, parent, false);
//        return new GenreViewHolder(view);
//    }
//
//    @Override
//    public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item_artist, parent, false);
//        return new ArtistViewHolder(view);
//    }
//
//    @Override
//    public void onBindChildViewHolder(ArtistViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
//        Artist artist = (Artist) group.getItems().get(childIndex);
//
//        holder.setArtistName(artist.getName());
//        holder.setVol(artist.getVol());
//    }
//
//    @Override
//    public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition, ExpandableGroup group) {
//        holder.setGenreName(group.getTitle());
//    }
//}
//    class GenreViewHolder extends GroupViewHolder {
//
//    private TextView genreTitle;
//
//    public GenreViewHolder(View itemView) {
//        super(itemView);
//        genreTitle = (TextView)itemView.findViewById(R.id.genre_title);
//    }
//
//    public void setGenreName(String name){
//        genreTitle.setText(name);
//    }
//
//}
//class ArtistViewHolder extends ChildViewHolder {
//
//    private TextView artistName;
//    private TextView volume1Rm;
//
//    public ArtistViewHolder(View itemView) {
//        super(itemView);
//        artistName = (TextView)itemView.findViewById(R.id.list_item_artist_name);
//        volume1Rm = (TextView)itemView.findViewById(R.id.tv_vol);
//    }
//
//    public void setArtistName(String name){
//        artistName.setText(name);
//    }
//    public void setVol(String vol){
//        volume1Rm.setText(vol);
//    }
//
//
//
//}
//
//
//
