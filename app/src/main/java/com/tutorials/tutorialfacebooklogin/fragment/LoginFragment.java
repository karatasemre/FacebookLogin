package com.tutorials.tutorialfacebooklogin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tutorials.tutorialfacebooklogin.MainActivity;
import com.tutorials.tutorialfacebooklogin.listener.OnAlbumListener;
import com.tutorials.tutorialfacebooklogin.R;
import com.tutorials.tutorialfacebooklogin.manager.GraphManager;
import com.tutorials.tutorialfacebooklogin.manager.PreferencesUtility;
import com.tutorials.tutorialfacebooklogin.model.AlbumModel;
import com.tutorials.tutorialfacebooklogin.utils.HashKeyHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Emre.Karatas on 4.07.2018.
 */

public class LoginFragment extends Fragment {

    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String USER_PHOTOS = "user_photos";
    private static final String PUBLISH_ACTIONS = "publish_to_groups ";
    LoginButton loginButton;
    ProgressBar progressBar;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();

        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        setUI(v);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList(EMAIL, USER_PHOTOS));
        loginButton.setFragment(this);

        HashKeyHelper.printHashKey(getActivity());

        FBTokenValidation();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphManager.instance.getUserInfo(getContext(), loginResult.getAccessToken(), new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                FBTokenValidation();
                            }
                        });
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        return v;
    }

    public void setUI(View v){
        loginButton = v.findViewById(R.id.login_button);
        progressBar = v.findViewById(R.id.progress);
    }

    public void FBTokenValidation(){
        if (AccessToken.isCurrentAccessTokenActive()) {
            loginButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            String userId = PreferencesUtility.getFbUserId(getActivity());
            GraphManager.instance.getAlbums(userId, new OnAlbumListener() {
                @Override
                public void getAlbumList(List<AlbumModel> albumModels) {
                    PreferencesUtility.setAlbumList(getContext(), albumModels);
                    MainActivity.instance.openFragment(AlbumFragment.newInstance(), true);
                }
            });
        }
        else{
            loginButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
