package com.yesspree.app.screens.addsubscription;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.yesspree.app.R;
import com.yesspree.app.constatnts.Constants;
import com.yesspree.app.interfaces.OnFragmentInteractionListener;
import com.yesspree.app.modelapi.AddSubcrInput;
import com.yesspree.app.modelapi.ProductData;
import com.yesspree.app.screens.addsubscription.fragment.AddSubscriptionFragment;
import com.yesspree.app.screens.subbasescreen.SubBaseActivity;
import com.yesspree.app.utility.Validation;

import butterknife.ButterKnife;

public class AddSubscriptionActivity extends SubBaseActivity implements OnFragmentInteractionListener {
    private String source;
    public ProductData productData;
    public AddSubcrInput subScrinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_subscribe, fragmentLayout);
        ButterKnife.bind(this);
        setSpecificScreenData();

        if (Validation.isValidObject(getIntent())) {
            source = getIntent().getStringExtra(Constants.SOURCE);
            productData = getIntent().getParcelableExtra(Constants.SUBSCRIBE_DIALOG_FRAGMENT);
            addFragment();
            return;
        }
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                navigateBackpressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateBackpressed() {
        FragmentManager fragments = getSupportFragmentManager();
        if (fragments.getBackStackEntryCount() > 1) {
            fragments.popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    public void setSpecificScreenData() {
        title.setText(getString(R.string.subscribe));
    }

    private void addFragment() {
        subScrinput = new AddSubcrInput();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, AddSubscriptionFragment.newInstance());
        transaction.addToBackStack(AddSubscriptionFragment.class.getName());
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Object data) {

    }
}
