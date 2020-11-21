package com.example.ponycui_home.svgaplayer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AnimationFromAssetsActivity extends Activity {

    int currentIndex = 7;
    SVGAImageView animationView = null;
    private boolean isPause = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationView = new SVGAImageView(this);
        animationView.setBackgroundColor(Color.BLACK);
        //animationView.setLoops(1);
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPause = false;
                animationView.stepToFrame(currentIndex, true);
            }
        });
        animationView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {
                Log.e("bsm","onPause");
            }

            @Override
            public void onFinished() {
                Log.e("bsm","onFinished");
                if(!isPause){
                    animationView.stopAnimation();
                }
            }

            @Override
            public void onRepeat() {
                Log.e("bsm","onRepeat");
                animationView.stopAnimation();
            }

            @Override
            public void onStep(int frame, double percentage) {
                Log.e("bsm","onStep frame=="+frame+", percentage=="+percentage);
                if(isPause){
                    if(frame >= 7){
                        animationView.stepToFrame(frame, false);
                    }
                }
            }
        });
        loadAnimation();
        setContentView(animationView);
    }

    private void loadAnimation() {
        SVGAParser svgaParser = SVGAParser.Companion.shareParser();
        String name = this.randomSample();
        name = "2.750.svga";
        Log.d("SVGA", "## name " + name);
        svgaParser.setFrameSize(100, 100);
        svgaParser.decodeFromAssets(name, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                animationView.setVideoItem(videoItem);
                animationView.stepToFrame(0, true);

                //animationView.stepToFrame(7, false);
            }

            @Override
            public void onError() {

            }
        });
    }

    private ArrayList<String> samples = new ArrayList();

    private String randomSample() {
        if (samples.size() == 0) {
            samples.add("750x80.svga");
            samples.add("alarm.svga");
            samples.add("angel.svga");
            samples.add("Castle.svga");
            samples.add("EmptyState.svga");
            samples.add("Goddess.svga");
            samples.add("gradientBorder.svga");
            samples.add("heartbeat.svga");
            samples.add("matteBitmap.svga");
            samples.add("matteBitmap_1.x.svga");
            samples.add("matteRect.svga");
            samples.add("MerryChristmas.svga");
            samples.add("posche.svga");
            samples.add("Rocket.svga");
            samples.add("rose.svga");
            samples.add("rose_2.0.0.svga");
        }
        return samples.get((int) Math.floor(Math.random() * samples.size()));
    }

}
