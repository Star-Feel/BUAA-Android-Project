package com.example.buaaexercise.group;

import android.graphics.Bitmap;

public class JoinerItem {
    private String username;
    private Bitmap avatarResId;
    public JoinerItem(String username, Bitmap avatarResId) {
        this.username = username;
        this.avatarResId = avatarResId;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getAvatarResId() {
        return avatarResId;
    }
}
