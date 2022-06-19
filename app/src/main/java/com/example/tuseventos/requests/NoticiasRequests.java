package com.example.tuseventos.requests;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.tuseventos.DialogTipos;
import com.example.tuseventos.Tags;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.models.TipoArticulos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiasRequests {

    private static final String TAG = "NoticiasRequests";

    public static void get_articles(Fragment fragment, int page, String type_id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .get_articles(ApiUtils.getBasicAuthWith("page", page, "type_id", type_id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            JSONArray jsonArticulos = json.getJSONArray("articles");
                            ArrayList<Articulos> articulosList = new ArrayList<>();
                            for (int i = 0; i < jsonArticulos.length(); i++) {
                                JSONObject articuloJson = jsonArticulos.getJSONObject(i);
                                Articulos articulo = new Articulos(articuloJson);
                                articulosList.add(articulo);
                            }
                            UserRequests.invokeMethodWithList("onGetArticlesSuccess", fragment, articulosList);
                        } else {
                            UserRequests.invokeMethodWithString("onGetArticlesFailed", fragment, json.getString(Tags.MESSAGE));
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

    public static void add_favorite_article(Activity activity, String id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .add_favorite_article(ApiUtils.getBasicAuthWith("article_id", id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            UserRequests.invokeMethod("onAddFavoriteArticleSuccess", activity);
                        } else {
                            UserRequests.invokeMethodWithString("onAddFavoriteArticleFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void remove_favorite_article(Activity activity, String id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .remove_favorite_article(ApiUtils.getBasicAuthWith("article_id", id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            UserRequests.invokeMethod("onRemoveFavoriteArticleSuccess", activity);
                        } else {
                            UserRequests.invokeMethodWithString("onRemoveFavoriteArticleFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void get_favorite_articles(Fragment fragment, int page, String type_id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .get_favorite_articles(ApiUtils.getBasicAuthWith("page", page, "type_id", type_id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            JSONArray jsonArticulos = json.getJSONArray("articles");
                            ArrayList<Articulos> articulosList = new ArrayList<>();
                            for (int i = 0; i < jsonArticulos.length(); i++) {
                                JSONObject articuloJson = jsonArticulos.getJSONObject(i);
                                Articulos articulo = new Articulos(articuloJson);
                                articulosList.add(articulo);
                            }
                            UserRequests.invokeMethodWithList("onGetArticlesSuccess", fragment, articulosList);
                        } else {
                            UserRequests.invokeMethodWithString("onGetArticlesFailed", fragment, json.getString(Tags.MESSAGE));
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

    public static void add_remindme_article(Activity activity, String id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .add_remindme_article(ApiUtils.getBasicAuthWith("article_id", id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            UserRequests.invokeMethod("onAddRemindmeArticleSuccess", activity);
                        } else {
                            UserRequests.invokeMethodWithString("onAddRemindmeArticleFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void remove_remindme_article(Activity activity, String id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .remove_remindme_article(ApiUtils.getBasicAuthWith("article_id", id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            UserRequests.invokeMethod("onRemoveRemindmeArticleSuccess", activity);
                        } else {
                            UserRequests.invokeMethodWithString("onRemoveRemindmeArticleFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void get_article_types(DialogTipos dialogTipos) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .get_article_types(ApiUtils.getBasicAuth());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            JSONArray jsonTiposArticulos = json.getJSONArray("article_types");
                            ArrayList<TipoArticulos> tiposArticulosList = new ArrayList<>();
                            for (int i = 0; i < jsonTiposArticulos.length(); i++) {
                                JSONObject tipoArticuloJson = jsonTiposArticulos.getJSONObject(i);
                                TipoArticulos tipoArticulo = new TipoArticulos(tipoArticuloJson);
                                tiposArticulosList.add(tipoArticulo);
                            }

                            // aprovechamos para guardar los tipos de articulos en la base de datos
                            TipoArticulos.saveTiposArticulos(tiposArticulosList);

                            UserRequests.invokeMethodWithList("onGetTypeArticlesSuccess", dialogTipos, tiposArticulosList);
                        } else {
                            UserRequests.invokeMethodWithString("onGetTypeArticlesFailed", dialogTipos, json.getString(Tags.MESSAGE));
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
                ApiUtils.errorResponse(t, dialogTipos.getActivity());
            }
        });
    }

    public static void get_article(Activity activity, String id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .get_article(ApiUtils.getBasicAuthWith("article_id", id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            JSONObject jsonArticulo = json.getJSONObject("article");
                            Articulos articulo = new Articulos(jsonArticulo);

                            UserRequests.invokeMethodWithObject("onGetArticleSuccess", activity, articulo);
                        } else {
                            UserRequests.invokeMethodWithString("onGetArticleFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void read_article(Activity activity, String id) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .read_article(ApiUtils.getBasicAuthWith("article_id", id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            UserRequests.invokeMethod("onReadArticleSuccess", activity);
                        } else {
                            UserRequests.invokeMethodWithString("onReadArticleFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void get_recommended_articles(Activity activity) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .get_recommended_articles(ApiUtils.getBasicAuth());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            JSONArray jsonArticulos = json.getJSONArray("articles");
                            ArrayList<Articulos> articulosList = new ArrayList<>();
                            for (int i = 0; i < jsonArticulos.length(); i++) {
                                JSONObject articuloJson = jsonArticulos.getJSONObject(i);
                                Articulos articulo = new Articulos(articuloJson);
                                articulosList.add(articulo);
                            }
                            UserRequests.invokeMethodWithList("onGetRecommendedArticlesSuccess", activity, articulosList);
                        } else {
                            UserRequests.invokeMethodWithString("onGetRecommendedArticlesFailed", activity, json.getString(Tags.MESSAGE));
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

    public static void send_article_comment(Activity activity, String id, String text) {
        Call<String> call = RetrofitClient.getClient().create(NoticiasService.class)
                .send_article_comment(ApiUtils.getBasicAuthWith("article_id", id, "comment_text", text));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        Log.v(TAG, response.body());
                        JSONObject json = new JSONObject(response.body());

                        if (json.getString(Tags.RESULT).contains(Tags.OK)) {
                            UserRequests.invokeMethod("onSentCommentSuccess", activity);
                        } else {
                            UserRequests.invokeMethodWithString("onSentCommentFailed", activity, json.getString(Tags.MESSAGE));
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
}
