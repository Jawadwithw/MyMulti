package com.xbbxsnia.the2806.CaptainBike.ChooseTransportationVehicleContract;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.xbbxsnia.the2806.R;

public class ChooseTransportationVehicleFragment extends BottomSheetDialogFragment {
    private OnChooseTransportationVehicleNextClickedListener listener;
    private MaterialCardView cvTruck;
    private MaterialCardView cvMotorcycle;
    private Button btnNextStep;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_tranportation_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvTruck = view.findViewById(R.id.cv_chooseTransportationVehicle_truck);
        cvMotorcycle = view.findViewById(R.id.cv_chooseTransportationVehicle_motorcycle);
        btnNextStep = view.findViewById(R.id.btn_chooseTransportationVehicle_nextStep);

        cvTruck.setOnClickListener(v -> {
            cvMotorcycle.setStrokeColor(null);
            cvTruck.setStrokeWidth(10);
            cvTruck.setStrokeColor(getResources().getColor(R.color.captainBikeColorPrimary));
        });

        cvMotorcycle.setOnClickListener(v -> {
            cvTruck.setStrokeColor(null);
            cvMotorcycle.setStrokeWidth(10);
            cvMotorcycle.setStrokeColor(getResources().getColor(R.color.captainBikeColorPrimary));
        });
    }

    public interface OnChooseTransportationVehicleNextClickedListener {
        void onClick();
    }
}
