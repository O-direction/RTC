package com.example.kchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kchat.adapter.MsgAdapter;
import com.example.kchat.model.Msg;
import com.example.kchat.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class ChatActivity extends BaseActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private Msg msg;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ImageView back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        JMessageClient.registerEventReceiver(this);
        function();
    }
    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        JMessageClient.exitConversation();
        super.onDestroy();
    }

    public void function(){
        //创建会话
        Intent i = getIntent();
        final String name= i.getStringExtra("Name");
        Conversation.createSingleConversation(name);
        Conversation conversation = JMessageClient.getSingleConversation(name);
        List<Message> listMessage = conversation.getAllMessage();

        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        //获取上一次对话的内容
        for (Message mes: listMessage
        ) {
            if(mes.getDirect() == MessageDirect.send){
                String JsonMessage = mes.getContent().toJson();
                String message = JsonMessage.substring(JsonMessage.indexOf(':')+2,JsonMessage.indexOf(',')-1);
                msg = new Msg(message,1);
                msgList.add(msg);
            }else{
                String JsonMessage = mes.getContent().toJson();
                String message = JsonMessage.substring(JsonMessage.indexOf(':')+2,JsonMessage.indexOf(',')-1);
                msg = new Msg(message,0);
                msgList.add(msg);
            }
        }
        msgRecyclerView.scrollToPosition(msgList.size() -1);




        //发送消息并且展示
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    msg = new Msg(content, Msg.TYPE_SENT);
                    Message message = JMessageClient.createSingleTextMessage(name,"" ,msg.getContent());
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if(i==0){

                            }else{
                                Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    JMessageClient.sendMessage(message);

                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size() -1);
                    inputText.setText("");

                }
            }
        });


    }
    //接收消息
    public void onEvent(MessageEvent event) {
        //获取事件发生的会话对象
        Message msg = event.getMessage();//获取此次离线期间会话收到的新消息列表
        System.out.println(String.format(Locale.SIMPLIFIED_CHINESE, "收到一条来自%s的在线消息。\n", msg.getTargetID()));
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                Msg msg_text = new Msg(textContent.getText(),0);
                msgList.add(msg_text);
                adapter.notifyItemInserted(msgList.size()-1);
                msgRecyclerView.scrollToPosition(msgList.size() -1);
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()){
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                    case group_info_updated://since 2.2.1
                        //群信息变更事件
                        break;
                }
                break;
            case unknown:
                // 处理未知消息，未知消息的Content为PromptContent 默认提示文本为“当前版本不支持此类型消息，请更新sdk版本”，上层可选择不处理
                PromptContent promptContent = (PromptContent) msg.getContent();
                promptContent.getPromptType();//未知消息的type是unknown_msg_type
                promptContent.getPromptText();//提示文本，“当前版本不支持此类型消息，请更新sdk版本”
                break;
        }
        System.out.println("arrived3");
    }
}


