package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        TextView tvReceivedMsg = view.findViewById(R.id.tv_received_msg);
        
        // Receive data from FirstFragment
        if (getArguments() != null) {
            String msg = getArguments().getString("some_arg");
            tvReceivedMsg.setText("Received: " + msg);
        }

        Button btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send result back to FirstFragment
                Bundle result = new Bundle();
                result.putString("bundleKey", "Hello back from Second!");
                getParentFragmentManager().setFragmentResult("requestKey", result);

                // Go back to the previous fragment
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
