package com.xbbxsnia.the2806.ZipJet.OrderContract.ClotheTypeContract;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xbbxsnia.the2806.CapitanCar.Base.BaseFragment;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.Adapters.ClotheTypeNumberAdapter;
import com.xbbxsnia.the2806.ZipJet.OrderContract.OnNextOrBackClickedCallBack;
import com.xbbxsnia.the2806.ZipJet.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ClotheTypeFragment extends BaseFragment {
    private Button btnNextStep;
    private ClotheTypeViewModel viewModel;
    private CardView cvClotheCategory;
    private OnNextOrBackClickedCallBack callBack;
    private CheckBox cbChooseAll;
    private CheckBox cbIron;
    private EditText etDescription;
    private CheckBox cbUseSoftener;
    private CheckBox cbStainRemoval;
    private EditText etTemperature,et_full_name;
    private CheckBox cbSteamWashing;
    private CheckBox cbScentedCleaner;
    private List<CheckBox> servicesCheckBox = new ArrayList<>();
    private TextView tvClotheCategory;
    List<String> services = new ArrayList<>();
    private Order order;
    private RadioGroup rgTemperature;
    private RadioGroup rgColor;
    private boolean isChooseAll = false;
    private ClotheTypeNumberAdapter adapter;

    public ClotheTypeFragment(OnNextOrBackClickedCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void setupViews() {
        order = new Order();
        viewModel = ViewModelProviders.of(this).get(ClotheTypeViewModel.class);
        btnNextStep = rootView.findViewById(R.id.btn_clotheTypeFragment_nextStep);
        rgTemperature = rootView.findViewById(R.id.rg_clotheTypeFragment_temperature);
        cbChooseAll = rootView.findViewById(R.id.cb_clotheTypeFragment_chooseAll);
        etDescription = rootView.findViewById(R.id.et_clotheTypeFragment_description);
        cbIron = rootView.findViewById(R.id.cb_clotheTypeFragment_iron);
        cbUseSoftener = rootView.findViewById(R.id.cb_clotheTypeFragment_userSoftner);
        cbStainRemoval = rootView.findViewById(R.id.cb_clotheTypeFragment_stainRemoval);
        cbSteamWashing = rootView.findViewById(R.id.cb_clotheTypeFragment_steamWashing);
        cbScentedCleaner = rootView.findViewById(R.id.cb_clotheTypeFragment_scentedCleaner);
        rgColor = rootView.findViewById(R.id.rg_clotheTypeFragment_color);
        cvClotheCategory = rootView.findViewById(R.id.cv_clotheTypeFragment_clothesCategories);
        tvClotheCategory = rootView.findViewById(R.id.tv_clotheTypeFragment_clothesCategories);
        etTemperature = rootView.findViewById(R.id.et_clotheTypeFragment_temperature);
        et_full_name = rootView.findViewById(R.id.et_name);
        servicesCheckBox.add(cbIron);
        servicesCheckBox.add(cbUseSoftener);
        servicesCheckBox.add(cbStainRemoval);
        servicesCheckBox.add(cbSteamWashing);
        servicesCheckBox.add(cbScentedCleaner);
        cvClotheCategory.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(rootView.getContext(), cvClotheCategory);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate(R.menu.clothes_categories_menu, popup.getMenu());

            popup.setGravity(Gravity.END);

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                tvClotheCategory.setText(item.getTitle());
                if (item.getItemId() == R.id.item_clothesCategoryMenu_category)
                    tvClotheCategory.setTextColor(Color.parseColor("#A3A3A3"));
                else
                    tvClotheCategory.setTextColor(Color.BLACK);
                return true;
            });

            popup.show();
        });

        initRecyclerView();

        cbChooseAll.setOnClickListener(v -> {
            if (!isChooseAll) {
                isChooseAll = true;
                for (int i = 0; i < servicesCheckBox.size(); i++) {
                    servicesCheckBox.get(i).setChecked(true);
                }
            } else {
                isChooseAll = false;
                for (int i = 0; i < servicesCheckBox.size(); i++) {
                    servicesCheckBox.get(i).setChecked(false);
                }
                services = new ArrayList<>();
            }
        });

        cbIron.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                order.setPrice(11000);
                services.add("اتو");
            } else {
                isChooseAll = false;
                order.decreasePrice(11000);
                cbChooseAll.setChecked(false);
                services.remove("اتو");
            }
        });

        cbUseSoftener.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                order.setPrice(22000);
                services.add("استفاده از نرم کننده");
            } else {
                isChooseAll = false;
                order.decreasePrice(22000);
                cbChooseAll.setChecked(false);
                services.remove("استفاده از نرم کننده");
            }
        });

        cbStainRemoval.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                services.add("لکه بری");
            else {
                isChooseAll = false;
                cbChooseAll.setChecked(false);
                services.remove("لکه بری");
            }
        });

        cbSteamWashing.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                services.add("بخارشویی");
            else {
                isChooseAll = false;
                cbChooseAll.setChecked(false);
                services.remove("بخارشویی");
            }
        });

        cbScentedCleaner.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                services.add("استفاده از شوینده معطر");
            else {
                isChooseAll = false;
                cbChooseAll.setChecked(false);
                services.remove("استفاده از شوینده معطر");
            }
        });


        btnNextStep.setOnClickListener(v -> {
            if (isConfirmed(getContext())) {
                initOrder();
                callBack.onNextClick(1, order);
            } else
                Toast.makeText(getContext(), "لطفا اطلاعات خواسته شده را تکمیل کنید", Toast.LENGTH_SHORT).show();
        });

    }

    public void initOrder() {

        order.setPrice(calculateOrderFinalPrice());
        if (rgTemperature.getCheckedRadioButtonId() == R.id.cb_clotheTypeFragment_celsius)
            order.setTemperatureUnit("celsius");
        else if (rgTemperature.getCheckedRadioButtonId() == R.id.cb_clotheTypeFragment_fahrenheit)
            order.setTemperatureUnit("fahrenheit");

        if (rgColor.getCheckedRadioButtonId() == R.id.cb_clotheTypeFragment_whiteCloth)
            order.setColor("سفید");
        else if (rgColor.getCheckedRadioButtonId() == R.id.cb_clotheTypeFragment_withColorCloth)
            order.setColor("رنگی");

        order.setClothesModel(adapter.getSelectedClothes());

        order.setState("doing");


        order.setTemperature(Integer.parseInt(etTemperature.getText().toString()));
        order.setFull_name(et_full_name.getText().toString());

        order.setServices(services);

        order.setTitle(tvClotheCategory.getText().toString());

        if (!etDescription.equals(""))
            order.setDescription(etDescription.getText().toString());
    }

    public void initRecyclerView() {
        adapter = new ClotheTypeNumberAdapter(viewModel.getClothes());
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_clotheTypeFragment_clothesNumber);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        recyclerView.setAdapter(adapter);
    }

    public int calculateOrderFinalPrice() {
        int price = 0;
        for (int i = 0; i < adapter.getSelectedClothes().size(); i++) {
            price += adapter.getSelectedClothes().get(i).getFinalPrice();
        }
        return price;
    }

    @Override
    public int getLayout() {
        return R.layout.cloth_type_fragment;
    }

    @Override
    public BaseFragment newInstance() {
        return null;
    }

    @Override
    public boolean isConfirmed(Context context) {
        String category = tvClotheCategory.getText().toString();
        int selectedClothes = adapter.getSelectedClothes().size();
        int isRgColorChecked = rgColor.getCheckedRadioButtonId();
        int isRgTemperatureChecked = rgTemperature.getCheckedRadioButtonId();
        String temperature = etTemperature.getText().toString();
        int number = 0;
        for (int i = 0; i < servicesCheckBox.size(); i++) {
            if (servicesCheckBox.get(i).isChecked())
                number++;
        }
        return !category.equals("دسته بندی البسه") && selectedClothes != 0 && isRgColorChecked != -1 && isRgTemperatureChecked != -1 && !temperature.equals("") && number != 0;
    }
}
