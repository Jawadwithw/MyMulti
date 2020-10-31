package com.xbbxsnia.the2806.CapitanCar.ProblemInfoContract;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.lifecycle.ViewModelProviders;

import com.parse.ParseObject;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.CapitanCar.Base.BaseView;
import com.xbbxsnia.the2806.CapitanCar.Data.OnModelsReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.OnParseObjectReceived;
import com.xbbxsnia.the2806.CapitanCar.Data.UserSharedPrefManager;
import com.xbbxsnia.the2806.CapitanCar.OrderContract.OrderActivity;
import com.xbbxsnia.the2806.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProblemInfoFragment extends BaseFragment implements BaseView, AdapterView.OnItemSelectedListener {
    private Spinner problem_name_spinner,problem_type_spinner;
    private ProblemInfoViewModel problemInfoViewModel;
    private EditText et_problem_desc;
    ArrayList<String> problem_types =new ArrayList<>();
    List<ParseObject> problem_objects =new ArrayList<>();



    @Override
    public void setupViews() {
        problem_name_spinner = (Spinner) rootView.findViewById(R.id.spinner_problem_name);
        problem_type_spinner = (Spinner) rootView.findViewById(R.id.spinner_problem_type);
        et_problem_desc=(EditText)rootView.findViewById(R.id.problem_desc);
        ArrayAdapter<CharSequence> problem_name_adapter = ArrayAdapter.createFromResource(getViewContext(), R.array.problems, android.R.layout.simple_spinner_item);
        problem_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        problem_name_spinner.setAdapter(problem_name_adapter);
        problem_name_spinner.setOnItemSelectedListener(this);

        problemInfoViewModel = ViewModelProviders.of(this).get(ProblemInfoViewModel.class);


    }


    @Override
    public int getLayout() {
        return R.layout.layout_problem_info;
    }

    @Override
    public BaseFragment newInstance() {
        Bundle args = new Bundle();
        ProblemInfoFragment newFragment = new ProblemInfoFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public boolean isConfirmed(Context context) {
        if (problem_type_spinner.getSelectedItem() == null){
            Toast.makeText(context, getResources().getString(R.string.fill_all_fileds), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            String problem_name = problem_name_spinner.getSelectedItem().toString().trim();
            String problem_type = problem_name_spinner.getSelectedItem().toString().trim();
            String problem_desc = et_problem_desc.getText().toString().trim();
            UserSharedPrefManager prefManager=((OrderActivity) Objects.requireNonNull(getActivity())).sharedPrefManager();
            prefManager.saveUserData(OrderActivity.PROBLEM_NAME,problem_name);
            prefManager.saveUserData(OrderActivity.PROBLEM_TYPE,problem_type);
            prefManager.saveUserData(OrderActivity.PROBLEM_DESC,problem_desc);
            return true;
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String problem_name = adapterView.getItemAtPosition(i).toString();
        if (problem_objects.isEmpty()){
            problemInfoViewModel.getProblemType(getViewContext(), new OnParseObjectReceived() {
                @Override
                public void onReceived(List<ParseObject> parseObjects) {
                    problem_objects.addAll(parseObjects);
                    initProblemType(parseObjects,problem_name);
                }
            });
        }else{

        initProblemType(problem_objects,problem_name);
        }


    }

   private void initProblemType(List<ParseObject> problemObject,String problem_name){
        problem_types.clear();
        for (int i = 0; i < problemObject.size(); i++) {
            if (Objects.requireNonNull(problemObject.get(i).getString("Name")).equals(problem_name)){
                problem_types.add(Objects.requireNonNull(problemObject.get(i).get("Type")).toString());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_list_item_1, problem_types);
        problem_type_spinner.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
