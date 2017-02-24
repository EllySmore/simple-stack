package com.zhuinden.simplestackdemomultistack.presentation.paths.main.cloudsync;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhuinden.simplestack.Backstack;
import com.zhuinden.simplestack.Bundleable;
import com.zhuinden.simplestack.NestedStack;
import com.zhuinden.simplestack.StateBundle;
import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.StateChanger;
import com.zhuinden.simplestackdemomultistack.R;
import com.zhuinden.simplestackdemomultistack.application.Key;
import com.zhuinden.simplestackdemomultistack.presentation.paths.main.cloudsync.another.AnotherKey;
import com.zhuinden.simplestackdemomultistack.presentation.paths.main.mail.MailKey;

import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Owner on 2017. 01. 12..
 */

public class CloudSyncView
        extends RelativeLayout
        implements Bundleable, StateChanger {
    private static final String TAG = "FirstView";

    public CloudSyncView(Context context) {
        super(context);
        init(context);
    }

    public CloudSyncView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CloudSyncView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public CloudSyncView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    CloudSyncKey cloudSyncKey;

    private void init(Context context) {
        if(!isInEditMode()) {
            cloudSyncKey = Backstack.getKey(context);
        }
    }

    @OnClick(R.id.first_button)
    public void clickButton(View view) {
        Key key = Backstack.getKey(getContext());
        key.selectDelegate(getContext()).getBackstack().goTo(AnotherKey.create(key));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        NestedStack nestedStack = Backstack.getNestedStack(getContext());
        nestedStack.initialize(MailKey.create());
        nestedStack.setStateChanger(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NestedStack nestedStack = Backstack.getNestedStack(getContext());
        nestedStack.reattachStateChanger();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NestedStack nestedStack = Backstack.getNestedStack(getContext());
        nestedStack.detachStateChanger();
    }

    @Override
    public StateBundle toBundle() {
        StateBundle bundle = new StateBundle();
        bundle.putString("HELLO", "WORLD");
        StateBundle innerBundle = new StateBundle();
        innerBundle.putString("KAPPA", "KAPPA");
        bundle.putBundle("GOOMBA", innerBundle);
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable StateBundle bundle) {
        if(bundle != null) {
            Log.i(TAG, bundle.getString("HELLO"));
            Log.i(TAG, bundle.getBundle("GOOMBA") == null ? null : bundle.getBundle("GOOMBA").getString("KAPPA"));
        }
    }

    @Override
    public void handleStateChange(StateChange stateChange, Callback completionCallback) {
        completionCallback.stateChangeComplete();
    }
}