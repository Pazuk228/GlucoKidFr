package com.example.glucokidfr.ui.child;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glucokidfr.R;
import com.example.glucokidfr.domain.entities.Child;
import java.util.ArrayList;
import java.util.List;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ViewHolder> {
    private List<Child> childrenList = new ArrayList<>();
    private final OnChildInteractionListener listener;

    public interface OnChildInteractionListener {
        void onUnlinkClick(Child child);
        void onChildClick(Child child);
    }

    public ChildrenAdapter(OnChildInteractionListener listener) {
        this.listener = listener;
    }

    public void setChildren(List<Child> children) {
        this.childrenList.clear();
        this.childrenList.addAll(children);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Child child = childrenList.get(position);
        holder.tvName.setText(child.getFirstName());
        holder.tvStatus.setText("Нажми для просмотра истории");

        if (holder.btnUnlink != null) {
            holder.btnUnlink.setOnClickListener(v -> listener.onUnlinkClick(child));
        }
        holder.itemView.setOnClickListener(v -> listener.onChildClick(child));
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus;
        ImageView btnUnlink;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvChildName);
            tvStatus = itemView.findViewById(R.id.tvChildStatus);
            btnUnlink = itemView.findViewById(R.id.btnUnlink);
        }
    }
}