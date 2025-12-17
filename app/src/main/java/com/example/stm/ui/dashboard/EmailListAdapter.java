package com.example.stm.ui.dashboard;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stm.model.Email;
import com.example.stm.databinding.ItemEmailBinding;
import java.util.ArrayList;
import java.util.List;

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.EmailViewHolder> {

    private List<Email> emails = new ArrayList<>();

    @NonNull
    @Override
    public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout using ViewBinding
        ItemEmailBinding binding = ItemEmailBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new EmailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailViewHolder holder, int position) {
        Email currentEmail = emails.get(position);
        holder.bind(currentEmail);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    // Helper method to update the data in the adapter
    public void setEmails(List<Email> newEmails) {
        this.emails = newEmails;
        notifyDataSetChanged(); // Refresh the list
    }

    // The ViewHolder class that holds the views for a single item
    static class EmailViewHolder extends RecyclerView.ViewHolder {
        private final ItemEmailBinding binding;

        public EmailViewHolder(@NonNull ItemEmailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Email email) {
            binding.textViewSubject.setText(email.getSubject());
            binding.textViewRecipient.setText(email.getRecipient());
            binding.textViewStatus.setText(email.getStatus());
        }
    }
}
