package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.FragmentSignupPageFourBinding;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.services.RetrofitClient;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.services.UserApi;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities.SignupActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.POST;


public class SignupPageFour extends Fragment {
    private static final String ARG_USER = "user";
    private static final String ARG_USER_PARAM = "user_param";

    private final int PICK_IMAGE = 12345;
    private final int TAKE_PICTURE = 6352;
    private static final int REQUEST_CAMERA_ACCESS_PERMISSION =5674;
    private Bitmap bitmap;

    private FragmentSignupPageFourBinding binding;
    private UserVO user;


    public static SignupPageFour newInstance(UserVO user) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        SignupPageFour fragment = new SignupPageFour();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserVO) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_signup_page_four, container, false);
        View view = binding.getRoot();
        binding.fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_ACCESS_PERMISSION);
                }else {
                    getImageFromCamera();
                }

            }
        });
        binding.fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromGallery();
            }
        });
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null)
                    uploadImageToServer();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setProfilePicture(bitmap);
                updateUser();
                postRequest();
                Fragment fragment = ProfileScreen.newInstance(user);
                SignupActivity.fragmentManager.beginTransaction().setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }

    private void uploadImageToServer() {
        File imageFile = persistImage(bitmap, "profile");
        Ion.with(this)
                .load("https://external.dev.pheramor.com/")  // replace it with your upload url
                .setMultipartFile("profile_pic", "image/jpeg", imageFile)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                    }
                });
    }
    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = Objects.requireNonNull(getActivity()).getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

        return imageFile;
    }

    private void getImageFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivityForResult(takePicture, TAKE_PICTURE);
        }
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }


    public void updateUser() {
        Intent intent = new Intent();
        intent.putExtra(ARG_USER_PARAM, user);
        if (getActivity() != null)
            getActivity().setResult(Activity.RESULT_OK, intent);
    }

    public static UserVO parseUser(Intent result) {
        return (UserVO) result.getSerializableExtra(ARG_USER_PARAM);
    }


    public void getRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = "https://external.dev.pheramor.com/";
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.toString());
                    }
                }

        );


        requestQueue.add(objectRequest);
    }

    private void postUser() {

        Retrofit retrofit = RetrofitClient.getRetrofitClient(UserApi.BASE_URL);

        UserApi usersApi = retrofit.create(UserApi.class);

        Call<POST> post = usersApi.createUser(user);
        post.enqueue(new Callback<POST>() {
            @Override
            public void onResponse(Call<POST> call, retrofit2.Response<POST> response) {
                Log.d("Response", response.toString());
            }

            @Override
            public void onFailure(Call<POST> call, Throwable t) {
                Log.d("Response", "Post failed");
            }
        });
    }

    private void postRequest() {
        String URL = "https://external.dev.pheramor.com/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("EMAIL", user.getEmail());
                params.put("PASSWORD", user.getPassword());
                params.put("FULLNAME", user.getFullName());
                params.put("ZIPCODE", Integer.toString(user.getZipCode()));
                params.put("HEIGHT", user.getHeight());
                params.put("GENDER", user.getGender());
                params.put("DOB", user.getDateOfBirth().toString());
                params.put("GENDER_INTEREST", user.getInterestGender());
                params.put("MIN_AGE", Integer.toString(user.getAgeRangeMin()));
                params.put("MAX_AGE", Integer.toString(user.getAgeRangeMax()));
                params.put("RACE", user.getRace());
                params.put("RELIGION", user.getReligion());
                params.put("PROFILE_PICTURE", user.getProfilePicture().toString());

                return params;
            }
        };

        if (getActivity() != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    binding.imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else if (requestCode == TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) (extras != null ? extras.get("data") : null);
                binding.imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromCamera();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
