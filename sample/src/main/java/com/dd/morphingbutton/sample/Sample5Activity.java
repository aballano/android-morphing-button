package com.dd.morphingbutton.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.CircularProgressButton;
import com.dd.morphingbutton.utils.ProgressGenerator;

public class Sample5Activity extends BaseActivity {

    private int mMorphCounter1 = 1;
    private int mMorphCounter2 = 1;

    public static void startThisActivity(@NonNull Context context) {
        context.startActivity(new Intent(context, Sample5Activity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sample_circular);

        final CircularProgressButton btnMorph1 = (CircularProgressButton) findViewById(R.id.btnMorph1);
        btnMorph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMorphButton1Clicked(btnMorph1);
            }
        });

        final CircularProgressButton btnMorph2 = (CircularProgressButton) findViewById(R.id.btnMorph2);
        btnMorph2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMorphButton2Clicked(btnMorph2);
            }
        });

        morphToIdle(btnMorph1, 0);
        morphToFailure(btnMorph2, 0);
    }

    private void onMorphButton1Clicked(CircularProgressButton btnMorph) {
        if (mMorphCounter1 == 0) {
            mMorphCounter1++;
            morphToIdle(btnMorph, integer(R.integer.mb_animation));
        } else if (mMorphCounter1 == 1) {
            mMorphCounter1 = 0;
            simulateProgress1(btnMorph);
        }
    }

    private void onMorphButton2Clicked(CircularProgressButton btnMorph) {
        if (mMorphCounter2 == 0) {
            mMorphCounter2++;
            morphToFailure(btnMorph, integer(R.integer.mb_animation));
        } else if (mMorphCounter2 == 1) {
            mMorphCounter2 = 0;
            simulateProgress2(btnMorph);
        }
    }

    private void morphToIdle(MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
              .duration(duration)
              .cornerRadius(dimen(R.dimen.mb_height_56))
              .width(dimen(R.dimen.mb_width_100))
              .height(dimen(R.dimen.mb_height_56))
              .color(color(R.color.mb_black_50))
              .icon(R.drawable.ic_download)
              .colorPressed(color(R.color.mb_black))
              .text("321 KB");
        btnMorph.morph(square);
    }

    private void morphToSuccess(MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
              .duration(integer(R.integer.mb_animation))
              .cornerRadius(dimen(R.dimen.mb_height_56))
              .width(dimen(R.dimen.mb_height_56))
              .height(dimen(R.dimen.mb_height_56))
              .color(color(R.color.mb_green))
              .colorPressed(color(R.color.mb_green_dark))
              .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
    }

    private void morphToFailure(MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
              .duration(duration)
              .cornerRadius(dimen(R.dimen.mb_height_56))
              .width(dimen(R.dimen.mb_height_56))
              .height(dimen(R.dimen.mb_height_56))
              .color(color(R.color.mb_red))
              .colorPressed(color(R.color.mb_red_dark))
              .icon(R.drawable.ic_lock);
        btnMorph.morph(circle);
    }

    private void simulateProgress2(@NonNull final CircularProgressButton button) {
        int progressColor = color(R.color.mb_purple);
        int color = color(R.color.mb_gray);
        int width = dimen(R.dimen.mb_height_56);
        int duration = integer(R.integer.mb_animation);

        ProgressGenerator generator = new ProgressGenerator(new ProgressGenerator.OnCompleteListener() {
            @Override
            public void onComplete() {
                morphToIdle(button, integer(R.integer.mb_animation));
                button.unblockTouch();
            }
        });
        button.blockTouch(); // prevent user from clicking while button is in progress
        button.morphToProgress(color, progressColor, width, duration, R.drawable.ic_cancel);
        generator.start(button);
    }

    private void simulateProgress1(@NonNull final CircularProgressButton button) {
        int progressColor = color(R.color.mb_white);
        int color = color(R.color.mb_black_50);
        int radius = dimen(R.dimen.mb_height_56);
        int duration = integer(R.integer.mb_animation);

        ProgressGenerator generator = new ProgressGenerator(new ProgressGenerator.OnCompleteListener() {
            @Override
            public void onComplete() {
                morphToSuccess(button);
                button.unblockTouch();
            }
        });
        button.blockTouch(); // prevent user from clicking while button is in progress
        button.morphToProgress(color, progressColor, radius, duration, R.drawable.ic_cancel);
        generator.start(button);
    }
}
