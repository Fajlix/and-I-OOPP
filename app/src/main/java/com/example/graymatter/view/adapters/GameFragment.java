package com.example.graymatter.view.adapters;

import android.content.Context;

public interface GameFragment {
    void makeMove(int position);

    Context getContext();
}
