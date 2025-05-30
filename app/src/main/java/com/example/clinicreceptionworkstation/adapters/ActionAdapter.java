package com.example.clinicreceptionworkstation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.models.Action;

import java.util.List;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {
    private final List<Action> actions;

    public ActionAdapter(List<Action> actions) {
        this.actions = actions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTimeTextView;
        private final TextView messageTextView;

        public ViewHolder(View view) {
            super(view);
            dateTimeTextView = view.findViewById(R.id.action_date_time);
            messageTextView = view.findViewById(R.id.action_message);
        }

        public void bind(Action action) {
            dateTimeTextView.setText(action.getDate() + " " + action.getTime());
            messageTextView.setText(action.getMessage());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_action, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(actions.get(position));
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}