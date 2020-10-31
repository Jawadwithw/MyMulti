package com.xbbxsnia.the2806.Main2806.Main2806.Data;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.xbbxsnia.the2806.G;
import com.xbbxsnia.the2806.R;
import com.xbbxsnia.the2806.ZipJet.LaundryDetailsContract.LaundryDetailsActivity;

import java.util.Objects;

public class DialogGenerator {
    private View view_dialog;
    private Dialog dialog_progress, dialog;
    private float rate;


    public Dialog progressDialog(Context context) {
        dialog_progress = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view_dialog = inflater.inflate(R.layout.layout_progress_dialog, (ViewGroup) dialog_progress.findViewById(R.id.progress_dialog));
        dialog_progress.setContentView(view_dialog);
        dialog_progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_progress.getWindow().getAttributes().windowAnimations = R.style.FadeInOutAnimation;
        dialog_progress.setCanceledOnTouchOutside(false);
        return dialog_progress;
    }

    public Dialog generateDialogRating(final String username_servicer, Context context) {
        dialog = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view_dialog = inflater.inflate(R.layout.layout_rating_bar, (ViewGroup) dialog.findViewById(R.id.rating_dialog));
        dialog.setContentView(view_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.FadeInOutAnimation;
        dialog.setCanceledOnTouchOutside(true);
        RatingBar ratingBar = (RatingBar) view_dialog.findViewById(R.id.framework_normal_ratingbar);
        final EditText et_comment = (EditText) view_dialog.findViewById(R.id.et_comment);
        Button btn_ok = (Button) view_dialog.findViewById(R.id.btn_ok);
        rate = ratingBar.getRating();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment = et_comment.getText().toString().trim();
                if (!comment.isEmpty()) {
                    ParseObject parseObject = new ParseObject("cmm_" + username_servicer);
                    parseObject.put(LaundryDetailsActivity.CMM_RATE, String.valueOf(rate));
                    parseObject.put(LaundryDetailsActivity.CMM_BODY, comment);
                    parseObject.put(LaundryDetailsActivity.CMM_IS_CONFIRMED, false);
                    //I have to put an input to get my name;
                    parseObject.put(LaundryDetailsActivity.CMM_NAME, "jawad");
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //   dismissDialog();
//                                                        SendPushAdmin sendPushAdmin= new SendPushAdmin();
//                                                        sendPushAdmin.SendPushAdmin("نظر برای : "+"tbl_" + username_servicer);
                                Toast.makeText(context, "با تشکر..نظر شما بعد از بررس نمایش داده خواهد شد.", Toast.LENGTH_SHORT).show();
                                et_comment.setText("");
                            } else {
                                Toast.makeText(context, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } else {
                    Toast.makeText(context, "لطفا تمامی فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return dialog;
    }
}
