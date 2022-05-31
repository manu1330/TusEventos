package com.example.tuseventos.requests;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.tuseventos.Preferences;
import com.example.tuseventos.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRequests {

    private static final String TAG = "UserRequests";

    public static void invokeMethod(String methodName, Object object) {
        try {
            Method method = object.getClass().getMethod(methodName);
            method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void invokeMethodWithString(String methodName, Object object, String string) {
        try {
            Method method = object.getClass().getMethod(methodName, String.class);
            method.invoke(object, string);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void invokeMethodWithList(String methodName, Object object, List list) {
        try {
            Method method = object.getClass().getMethod(methodName, List.class);
            method.invoke(object, list);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void register(
            Fragment fragment,
            String username,
            String email,
            String password
    ) {
        Call<String> call = RetrofitClient.getClient().create(UserService.class)
                .register(ApiUtils.getRegisterJSON(
                        username,
                        email,
                        password
                ));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            Preferences.setToken(json.getString(Tags.TOKEN));
                            invokeMethod("onRegisterSuccess", fragment);
                        } else {
                            invokeMethodWithString("onRegisterFailed", fragment, json.getString(Tags.MESSAGE));
                        }
                    } else {
                        Log.e(TAG, "response.body is null");
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.errorResponse(t, fragment.getActivity());
            }
        });
    }

    public static void login(final Fragment fragment, final String username, final String password) {
        Call<String> call = RetrofitClient.getClient().create(UserService.class)
                .login(ApiUtils.getLoginJSON(username, password));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            Preferences.setToken(json.getString(Tags.TOKEN));
                            invokeMethod("onLoginSuccess", fragment);
                        } else {
                            invokeMethodWithString("onLoginFailed", fragment, json.getString(Tags.MESSAGE));
                        }
                    } else {
                        Log.e(TAG, "response.body is null");
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.errorResponse(t, fragment.getActivity());
            }
        });
    }

    public static void logout(Activity activity) {
        Call<String> call = RetrofitClient.getClient().create(UserService.class)
                .logout(ApiUtils.getBasicAuth());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            Preferences.setToken(null);
                            invokeMethod("onLogoutSuccess", activity);
                        } else {
                            invokeMethodWithString("onLogoutFailed", activity, json.getString(Tags.MESSAGE));
                        }
                    } else {
                        Log.e(TAG, "response.body is null");
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.errorResponse(t, activity);
            }
        });
    }

    public static void change_credentials(Fragment fragment, String email, String contrasena) {
        Call<String> call = RetrofitClient.getClient().create(UserService.class)
                .change_credentials(ApiUtils.getBasicAuthWith("email", email, "password", contrasena));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            if (json.getBoolean("password_changed")){
                                logout(fragment.getActivity());
                            }
                            invokeMethod("onChangedCredentialsSuccess", fragment);
                        } else {
                            invokeMethodWithString("onChangedCredentialsFailed", fragment, json.getString(Tags.MESSAGE));
                        }
                    } else {
                        Log.e(TAG, "response.body is null");
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ApiUtils.errorResponse(t, fragment.getActivity());
            }
        });
    }
}
