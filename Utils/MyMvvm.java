package com.solution.uploadimage.Utils;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.solution.uploadimage.ClientApi.ClientApi;
import com.solution.uploadimage.Interface.ImageInterface;
import com.solution.uploadimage.MainActivity;
import com.solution.uploadimage.Resopnse.ImagePojo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMvvm extends ViewModel{
    ImageInterface imageInterface = ClientApi.apiClient().create(ImageInterface.class);

    private MutableLiveData<ImagePojo> updateUserProfile;

    public LiveData<ImagePojo> UpdateUserDetailProfile(final Activity activity, RequestBody id, RequestBody username, RequestBody phone, MultipartBody.Part image)  {
        updateUserProfile = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            imageInterface.UpdateProfile(id, username, phone, image).enqueue(new Callback<ImagePojo>() {
                @Override
                public void onResponse(Call<ImagePojo> call, Response<ImagePojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        updateUserProfile.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ImagePojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return updateUserProfile;
    }
}
