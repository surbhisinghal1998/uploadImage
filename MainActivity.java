package com.solution.uploadimage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.omninos.util_data.CommonUtils;
import com.solution.uploadimage.Resopnse.ImagePojo;
import com.solution.uploadimage.Utils.MyMvvm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView user_img;
    private EditText name, phone;
    private Button submit;
    private MyMvvm myView;
    String et_name, et_number;
    String id = "1";
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = ViewModelProviders.of(MainActivity.this).get(MyMvvm.class);
        ids();
    }

    private void ids() {
        user_img = findViewById(R.id.user_img);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        user_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                et_name = name.getText().toString();
                et_number = phone.getText().toString();
                hitApi();
                break;
            case R.id.user_img:
                ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)//Crop image(Optional), Check Customization for more option
                        .maxResultSize(500, 500)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        user_img.setImageURI(data.getData());
        image = data.getData().getPath();
        Toast.makeText(this, image + "", Toast.LENGTH_SHORT).show();
    }

    private void hitApi() {
        myView.UpdateUserDetailProfile(this, CommonUtils.getRequestBodyText(id), CommonUtils.getRequestBodyText(et_name), CommonUtils.getRequestBodyText(et_number), CommonUtils.getImgdData(image, "image")).observe(this, new Observer<ImagePojo>() {
            @Override
            public void onChanged(ImagePojo imagePojo) {
                if (imagePojo.getSuccess().equalsIgnoreCase("1")){
                    Toast.makeText(MainActivity.this,imagePojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,imagePojo.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}