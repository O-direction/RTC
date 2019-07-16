package com.example.androiddesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;


import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;


public class HomePage extends AppCompatActivity {

    private TextView friends;
    private TextView talk;
    private TextView me;
    private LinkedList<HomeConversation> data = null;
    private Context context;
    private ConversationAdapter conversationAdapter = null;
    private ListView conversation_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        JMessageClient.init(this, true);

        DrawableChange();
        DisplayConversation();
    }

    public void DrawableChange(){

        talk = (TextView)findViewById(R.id.talk);
        friends = (TextView)findViewById(R.id.friends);
        me = (TextView)findViewById(R.id.me);
        final HomePage that = this;

        Drawable D_talk = getResources().getDrawable(R.drawable.talk);
        D_talk.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        talk.setCompoundDrawables(null, D_talk, null, null);

        Drawable D_friends = getResources().getDrawable(R.drawable.friends);
        D_friends.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        friends.setCompoundDrawables(null, D_friends, null, null);

        Drawable D_me = getResources().getDrawable(R.drawable.me);
        D_me.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        me.setCompoundDrawables(null, D_me, null, null);

        friends.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, FriendsMain.class);
                startActivity(intent);
            }
        });
    }

    public void DisplayConversation(){
        Message latestMessage;
        String name;

        context = HomePage.this;
        conversation_list = (ListView) findViewById(R.id.conversation_list);
        data = new LinkedList<HomeConversation>();
        conversationAdapter = new ConversationAdapter(data, context);
        conversation_list.setAdapter(conversationAdapter);


        List<Conversation> listConversation=JMessageClient.getConversationList();
        for (Conversation conversation:listConversation
        ) {
            latestMessage = conversation.getLatestMessage();
            TextContent textContent =(TextContent) latestMessage.getContent();
            String message = textContent.getText();
            if(message.length()>=20)
                message = message.substring(0,20);
            name = conversation.getTargetId();
            data.add(new HomeConversation(R.drawable.head_portrait, name , message));
            conversationAdapter.notifyDataSetChanged();
        }


        conversation_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HomeConversation click_friend = (HomeConversation)conversationAdapter.getItem(position);
                            Intent i = new Intent(context, Communication.class);
                            i.putExtra("Name", click_friend.getName());
                            startActivity(i);
                        }
                    });
            }
        });

    }
}
