package com.xbbxsnia.the2806.CapitanCar.PersonalInfoContract;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


import androidx.lifecycle.ViewModelProviders;


import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnModelsReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.ParseObjectGot;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PersonalInfoFragment extends BaseFragment implements BaseView {
    private PersonalInfoViewModel infoViewModel;

    private AutoCompleteTextView et_brand, et_model;
    private EditText et_name, et_last_name;
    private ArrayList<String> brands = new ArrayList<>();
    private ArrayList<ParseObject> carObjects = new ArrayList<>();

    @Override
    public void setupViews() {
        et_brand = (AutoCompleteTextView) rootView.findViewById(R.id.et_brand);
        et_model = (AutoCompleteTextView) rootView.findViewById(R.id.et_model);
        et_name = (EditText) rootView.findViewById(R.id.et_name);
        et_last_name = (EditText) rootView.findViewById(R.id.et_last_name);

        et_name.setText(((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager().getUserData(OrderActivity.NAME));
        et_last_name.setText(((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager().getUserData(OrderActivity.LAST_NAME));
        et_brand.setText(((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager().getUserData(OrderActivity.CAR_BRAND));
        et_model.setText(((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager().getUserData(OrderActivity.CARD_MODEL));

        infoViewModel = ViewModelProviders.of(this).get(PersonalInfoViewModel.class);
        infoViewModel.getBrands(getViewContext(), new OnParseObjectReceived() {
            @Override
            public void onReceived(List<ParseObject> parseObjects) {
                String lastBrand = "";
                carObjects.addAll(parseObjects);
                for (int i = 0; i < parseObjects.size(); i++) {
                    String newBrand = String.valueOf(parseObjects.get(i).get("Brand"));
                    if (!newBrand.equals(lastBrand)) {
                        brands.add(newBrand);
                        lastBrand = newBrand;
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_list_item_1, brands);
                et_brand.setAdapter(adapter);
            }
        });

        et_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d(TAG, "onItemClick: " + adapterView.getItemAtPosition((int) l));
                infoViewModel.getModel(getViewContext(), carObjects, (String) adapterView.getItemAtPosition((int) l), new OnModelsReceived() {
                    @Override
                    public void onReceived(final ArrayList<String> models) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "onReceived: " + models.size());
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_list_item_1, models);
                                et_model.setAdapter(adapter);
                            }
                        });
                    }

                });


            }
        });

    }


    @Override
    public int getLayout() {
        return R.layout.layout_personal_info;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        PersonalInfoFragment newFragment = new PersonalInfoFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        String name = et_name.getText().toString().trim();
        String last_name = et_last_name.getText().toString().trim();
        String car_brand = et_brand.getText().toString().trim();
        String car_model = et_model.getText().toString().trim();
        if (name.isEmpty() || last_name.isEmpty() || car_brand.isEmpty() || car_model.isEmpty()) {
            Toast.makeText(context, getResources().getString(R.string.fill_all_fileds), Toast.LENGTH_SHORT).show();
            return false;
        } else {
           UserSharedPrefManager prefManager=((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager();
           prefManager.saveUserData((OrderActivity.NAME),name);
           prefManager.saveUserData((OrderActivity.LAST_NAME),last_name);
           prefManager.saveUserData((OrderActivity.CAR_BRAND),car_brand);
           prefManager.saveUserData((OrderActivity.CARD_MODEL),car_model);

            return true;
        }

    }

    @Override
    public Context getViewContext() {
        return getContext();
    }


}
