package com.thiha.quickchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thiha.quickchat.model.UserModel;
import com.thiha.quickchat.utils.FirebaseUtil;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class CallMainActivity extends AppCompatActivity {

    EditText userIdEditText;
    Button startBtn;
    UserModel currentUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_main);

        userIdEditText= findViewById(R.id.user_id_edit_text);
        startBtn = findViewById(R.id.start_btn);

        startBtn.setOnClickListener(view -> {
         String userID= userIdEditText.getText().toString().trim();
         if(userID.isEmpty()){
             return;
         }
         //start the service
            startService(userID);
            Intent intent= new Intent(CallMainActivity.this,CallActivity.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
        });

//        String username = getIntent().getStringExtra("name");
//        userIdEditText.setText("Hey"+ username);

//        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
//            currentUserModel = task.getResult().toObject(UserModel.class);
//            userIdEditText.setText(currentUserModel.getUsername());
//        });

    }
    void startService(String userID){
        Application application = getApplication();
        long appID =1377709487;
        String appSign ="ac89e41e222d607f3fb1a927ef0aa19377f8f80b8b2ca2526abe0138e328ad0d";  // yourAppSign

        String userName =userID;

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}