package com.m4u.cryptotracker.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ComV19 comv19 = new ComV19();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Payment> paymentList = generateDummyPayments(); // Замените этот метод на ваш способ получения данных
        PaymentAdapter paymentAdapter = new PaymentAdapter(paymentList, (position, payment) -> {
            Activity activity = PaymentActivity.this;
            Toasty.custom(activity,
                    payment.getDescription(),
                    comv19.getDrawable(activity, R.drawable.ic_info),
                    ContextCompat.getColor(activity, R.color.colorPrimaryDark),
                    ContextCompat.getColor(activity, android.R.color.white),
                    Toasty.LENGTH_LONG, true, true).show();
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(paymentAdapter);

        View sortByDescriptionButton = findViewById(R.id.sortByDescriptionButton);
        View sortByAmountButton = findViewById(R.id.sortByAmountButton);
        View sortByServiceButton = findViewById(R.id.sortByServiceButton);

        sortByDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentAdapter.sortByDescription();
            }
        });

        sortByAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentAdapter.sortByAmount();
            }
        });

        sortByServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentAdapter.sortByService();
            }
        });
    }

    // Метод для генерации тестовых данных (замените его на ваш способ получения данных)
    private List<Payment> generateDummyPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("В 12:30 поступил платеж", 0.01 ,Currency.BTC, "Пайер"));
        payments.add(new Payment("В 22:45 поступил платеж", 2431,Currency.RUR, "Киви"));
        payments.add(new Payment("В 12:30 поступил платеж", 0.01 ,Currency.BTC, "Пайер"));
        payments.add(new Payment("В 22:45 поступил платеж", 2431,Currency.RUR, "Киви"));
        payments.add(new Payment("В 12:30 поступил платеж", 0.01,Currency.BTC, "Пайер"));
        payments.add(new Payment("В 22:45 поступил платеж", 2431,Currency.RUR, "Киви"));
        payments.add(new Payment("В 12:30 поступил платеж", 0.01 ,Currency.BTC, "Пайер"));
        payments.add(new Payment("В 22:45 поступил платеж", 2431,Currency.RUR, "Киви"));
        payments.add(new Payment("В 12:30 поступил платеж", 0.01 ,Currency.BTC, "Пайер"));
        payments.add(new Payment("В 22:45 поступил платеж", 2431,Currency.RUR, "Киви"));

        return payments;
    }
}