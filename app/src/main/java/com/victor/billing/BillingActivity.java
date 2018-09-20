package com.victor.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.victor.billing.library.data.SkuDetails;
import com.victor.billing.library.data.TransactionDetails;
import com.victor.billing.library.module.GoogleInBillingHelper;
import com.victor.billing.library.util.Loger;

public class BillingActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleInBillingHelper.OnGoogleInBillingListener {
    private String TAG = "BillingActivity";
    private String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkWfhjsw0FbcBFmSciboQonoe/eTwCjaaXCds2jbGsNZ+Byyj+/mCeFgvNvXHnKw/SxDAzMBsyxs/j9q2jeGUxzJyxJm0ssdMfIize8d/Pyl5+ugnZZjyZHQOiw633Sr4qOZLZWKJknkTlKzZqegcafvGHPfTxkfHlP/Xxk7trEK2N7+Z7IoJ9JgeXRfVC46/XYVelPz5rQDCmuJYB06X7U2XHC/RsCqk2ZM8eX0HuNnAdl2QbLUjvHpb2WzIAl0IF0/HNZeS5PyhUb77i7aZ2IuJ8VkeSnpfHOdHuKGgm5Ytr+l6vWs5PJvgA9t0nil3BPQX9AGoGTxQgGTli0UjAQIDAQAB";
    private String merchantId = "Meetgo";

    private Button mBtnPurchaseConsume,mBtnProductDetails,mBtnSubscribe,mBtnSubscribeDetails;

    private GoogleInBillingHelper mGoogleInBillingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        initialize();
    }
    private void initialize () {
        mBtnPurchaseConsume = findViewById(R.id.btn_purchase_consume);
        mBtnProductDetails = findViewById(R.id.btn_product_details);
        mBtnSubscribe = findViewById(R.id.btn_subscribe);
        mBtnSubscribeDetails = findViewById(R.id.btn_subscribe_details);
        mBtnPurchaseConsume.setOnClickListener(this);
        mBtnProductDetails.setOnClickListener(this);
        mBtnSubscribe.setOnClickListener(this);
        mBtnSubscribeDetails.setOnClickListener(this);

        mGoogleInBillingHelper = new GoogleInBillingHelper(this,base64EncodedPublicKey,merchantId,this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_purchase_consume:
                if (mGoogleInBillingHelper != null) {
                    mGoogleInBillingHelper.sendRequestWithParms(GoogleInBillingHelper.PURCHASE,"android.test.purchased");
                }
                break;
            case R.id.btn_product_details:
                if (mGoogleInBillingHelper != null) {
                    mGoogleInBillingHelper.sendRequestWithParms(GoogleInBillingHelper.PRODUCT_DETAILS,"android.test.purchased");
                }
                break;
            case R.id.btn_subscribe:
                if (mGoogleInBillingHelper != null) {
                    mGoogleInBillingHelper.sendRequestWithParms(GoogleInBillingHelper.SUBSCRIBE,"android.test.subscribe");
                }
                break;
            case R.id.btn_subscribe_details:
                if (mGoogleInBillingHelper != null) {
                    mGoogleInBillingHelper.sendRequestWithParms(GoogleInBillingHelper.SUBSCRIBE_DETAILS,"android.test.subscribe");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mGoogleInBillingHelper != null) {
            if (!mGoogleInBillingHelper.onActivityResult(requestCode,resultCode,data)) {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBillingComplete(TransactionDetails details) {
        Loger.d(TAG,"onBillingComplete-buy success" + details.orderId);
        Toast.makeText(this,"onBillingComplete-buy success" + details.orderId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingFailed(String error) {
        Loger.e(TAG,"onBillingFailed-error = " + error);
        Toast.makeText(this,"onBillingFailed-error = " + error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductDetails(SkuDetails skuDetails) {
        Toast.makeText(this,skuDetails == null ? "" : skuDetails.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleInBillingHelper != null) {
            mGoogleInBillingHelper.onDestory();
            mGoogleInBillingHelper = null;
        }
    }
}
