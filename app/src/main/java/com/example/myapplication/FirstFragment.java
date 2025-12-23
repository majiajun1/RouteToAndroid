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
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

public class FirstFragment extends Fragment {

    private TextView tvResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        tvResult = view.findViewById(R.id.tv_result);
        Button btnToSecond = view.findViewById(R.id.btn_to_second);

        // Listen for results from SecondFragment
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String resultString = result.getString("bundleKey");
                tvResult.setText("Result: " + resultString);
            }
        });

        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SecondFragment
                Fragment secondFragment = new SecondFragment();

                // Pass data to SecondFragment
                Bundle args = new Bundle();
                args.putString("some_arg", "Hello from FirstFragment!");
                secondFragment.setArguments(args);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Check if we are in the SplitFragmentActivity (container_top exists)
                // or FragmentTestActivity (fragment_container exists)
                int containerId = R.id.fragment_container;
                if (getParentFragmentManager().findFragmentById(R.id.container_top) != null) {
                    // We are in split mode, maybe we want to replace the bottom one?
                    // Or maybe in split mode we shouldn't navigate at all?
                    // Let's assume for now we want to replace the current fragment's container
                    if (getId() != 0) {
                        containerId = getId();
                    }
                }

                transaction.replace(containerId, secondFragment);
                transaction.addToBackStack(null); // Add to back stack so we can navigate back
                transaction.commit();
            }
        });

        return view;
    }
}
