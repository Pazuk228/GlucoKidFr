//package com.example.glucokidfr.ui.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.glucokidfr.R;
//import com.example.glucokidfr.domain.entities.ItemUserEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {
//    private List<ItemUserEntity> parents = new ArrayList<>();
//
//    @NonNull
//    @Override
//    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(android.R.layout.simple_list_item_1, parent, false);
//        return new ParentViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
//        ItemUserEntity parent = parents.get(position);
//        holder.textView.setText(parent.getName() + " (ID: " + parent.getId() + ")");
//    }
//
//    @Override
//    public int getItemCount() {
//        return parents.size();
//    }
//
//    public void setParents(List<ItemUserEntity> parents) {
//        this.parents = parents;
//        notifyDataSetChanged();
//    }
//
//    static class ParentViewHolder extends RecyclerView.ViewHolder {
//        TextView textView;
//
//        ParentViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(android.R.id.text1);
//        }
//    }
//}
