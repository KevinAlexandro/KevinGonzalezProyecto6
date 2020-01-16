package com.example.kevin.proyecto6;


import android.media.AudioManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment{

    public GameFragment() {
        // Required empty public constructor
    }

    private GameView mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_game, container, false);
        mainView = (GameView) v.findViewById(R.id.surfView);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mainView.stopGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mainView.releaseResources();
    }
}