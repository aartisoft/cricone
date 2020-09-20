package com.onecricket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onecricket.APICallingPackage.Class.APIRequestManager;
import com.onecricket.APICallingPackage.Interface.ResponseManager;
import com.onecricket.R;
import com.onecricket.utils.SessionManager;
import com.onecricket.databinding.ActivityMyAccountBinding;

import org.json.JSONException;
import org.json.JSONObject;

import static com.onecricket.APICallingPackage.Class.Validations.ShowToast;
import static com.onecricket.APICallingPackage.Config.MYACCOUNT;
import static com.onecricket.APICallingPackage.Constants.MYACCOUNTTYPE;

public class MyAccountActivity extends AppCompatActivity implements ResponseManager {

    MyAccountActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;

    String TotalBalance,Deposited,Winnings,Bonus,PanStatus,AadhaarStatus;

    ActivityMyAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_account);
        context = activity = this;
        initViews();
        sessionManager = new SessionManager();

        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);


        binding.tvAddBalance.setOnClickListener(view -> {
            Intent i = new Intent(activity, AddCashActivity.class);
            startActivity(i);
        });
        callMyAccount(true);

        binding.RLMyRecentTransactions.setOnClickListener(view -> {
            Intent i = new Intent(activity,MyTransactionActivity.class);
            startActivity(i);
        });

        binding.tvWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double Amount = Double.parseDouble(activity.getString(R.string.MinimumWithdrawAmountLimit));
                if (PanStatus.equals("2")&&AadhaarStatus.equals("2")) {
                    if (Double.parseDouble(Winnings) >= Amount) {
                        Intent i = new Intent(activity, WithdrawAmountActivity.class);
                        i.putExtra("AvailableBalance", Winnings);
                        startActivity(i);
                    } else {
                        ShowToast(context, "Not Enough Amount for Withdraw Request.");
                    }
                }
                else {
                    ShowToast(context, "Update your KYC document for withdraw amount.");
                }
            }
        });

        binding.tvUploadAadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(activity,UploadKYCActivity.class);
                i.putExtra("DocType","Aadhaar");
                startActivity(i);
            }
        });
        binding.tvUploadPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(activity,UploadKYCActivity.class);
                i.putExtra("DocType","Pan");
                startActivity(i);
            }
        });


    }
    public void initViews() {

        binding.head.tvHeaderName.setText("MY ACCOUNT");
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void callMyAccount(boolean isShowLoader) {
        try {
            apiRequestManager.callAPIWithAuthorization(MYACCOUNT,
                    createRequestJson(), context, activity, MYACCOUNTTYPE,
                    isShowLoader, responseManager,sessionManager.getUser(context).getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        try{
            TotalBalance = result.getString("total_amount");
            Deposited = result.getString("credit_amount");
            Winnings = result.getString("winning_amount");
            Bonus = result.getString("bonous_amount");
            AadhaarStatus = result.getString("aadhar_status");
            PanStatus = result.getString("pan_status");


            binding.tvTotalBalance.setText("₹ "+TotalBalance);
            binding.tvDepositedAmount.setText("₹ "+Deposited);
            binding.tvWinningAmount.setText("₹ "+Winnings);
            binding.tvBonusAmount.setText("₹ "+Bonus);

            if (PanStatus.equals("0")){
                binding.imPanVerified.setVisibility(View.INVISIBLE);
                binding.tvUploadPan.setEnabled(true);
            }
            else if (PanStatus.equals("1")){
                binding.imPanVerified.setVisibility(View.VISIBLE);
                binding.imPanVerified.setImageResource(R.drawable.pending_icon);
                binding.tvUploadPan.setText("Pending");
                binding.tvUploadPan.setEnabled(false);
            }
            else if (PanStatus.equals("2")){
                binding.imPanVerified.setVisibility(View.VISIBLE);
                binding.imPanVerified.setImageResource(R.drawable.verify_icon);
                binding.tvUploadPan.setText("Verified");
                binding.tvUploadPan.setEnabled(false);
            }
            else {
                binding.imPanVerified.setVisibility(View.INVISIBLE);
                binding.tvUploadPan.setText("Upload");
                binding.tvUploadPan.setEnabled(true);
            }

            if (AadhaarStatus.equals("0")){
                binding.imAadhaarVerified.setVisibility(View.INVISIBLE);
                binding.tvUploadAadhaar.setEnabled(true);
            }
            else if (AadhaarStatus.equals("1")){
                binding.imAadhaarVerified.setVisibility(View.VISIBLE);
                binding.imAadhaarVerified.setImageResource(R.drawable.pending_icon);
                binding.tvUploadAadhaar.setText("Pending");
                binding.tvUploadAadhaar.setEnabled(false);

            }
            else if (AadhaarStatus.equals("2")){
                binding.imAadhaarVerified.setVisibility(View.VISIBLE);
                binding.imAadhaarVerified.setImageResource(R.drawable.verify_icon);
                binding.tvUploadAadhaar.setText("Verified");
                binding.tvUploadAadhaar.setEnabled(false);
            }
            else {
                binding.imAadhaarVerified.setVisibility(View.INVISIBLE);
                binding.tvUploadAadhaar.setText("Upload");
                binding.tvUploadAadhaar.setEnabled(true);
            }

    } catch (JSONException e) {
        e.printStackTrace();
    }

    }



    @Override
    public void onError(Context mContext, String type, String message) {

    }

    private void showCoinsConversionAlert(Context context) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_coins, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button submit = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button cancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        cancel.setOnClickListener(view -> dialogBuilder.dismiss());
        submit.setOnClickListener(view -> {
            // DO SOMETHINGS
            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

}
