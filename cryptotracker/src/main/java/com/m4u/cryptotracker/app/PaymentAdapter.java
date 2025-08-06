package com.m4u.cryptotracker.app;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private final OnPaymentClickListener onPaymentClickListener;
    private List<Payment> paymentList;

    public PaymentAdapter(List<Payment> paymentList, OnPaymentClickListener onPaymentClickListener) {
        this.paymentList = paymentList;
        this.onPaymentClickListener = onPaymentClickListener;
    }

    // ...

    public interface OnPaymentClickListener {
        void onPaymentClick(int position, Payment payment);
    }


    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        int mm = position;
        Payment payment = paymentList.get(position);
        holder.bind(payment);
        holder.itemView.setOnClickListener(view -> {
            if (onPaymentClickListener != null) {
                onPaymentClickListener.onPaymentClick(mm, payment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView descriptionTextView, amountTextView, serviceTextView;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
        }

        public void bind(Payment payment) {
            descriptionTextView.setText(payment.getDescription());
            amountTextView.setText(String.valueOf(payment.getAmountValue()));
            serviceTextView.setText(payment.getService());
        }
    }

    public void sortByDescription() {
        Collections.sort(paymentList, (payment1, payment2) -> payment1.getDescription().compareTo(payment2.getDescription()));
        notifyDataSetChanged();
    }

    public void sortByAmount() {
        Collections.sort(paymentList, (payment1, payment2) -> {
            // Пример сортировки по числовому значению. Может потребоваться настройка в зависимости от формата данных.
            return Double.compare(payment1.getAmountValue(),payment2.getAmountValue());
        });
        notifyDataSetChanged();
    }

    public void sortByService() {
        Collections.sort(paymentList, (payment1, payment2) -> payment1.getService().compareTo(payment2.getService()));
        notifyDataSetChanged();

    }
}

