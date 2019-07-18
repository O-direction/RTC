package com.example.kchat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.R;
import com.example.kchat.adapter.ConverseAdapter;
import com.example.kchat.model.Converse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

public class ChatFragment extends Fragment {
    private List<Converse> converseList = new ArrayList<>();
    private ConverseAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_con);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConverseAdapter(converseList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration((new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL)));
    }
    public void initList(){
        List<Conversation> listConversation= JMessageClient.getConversationList();
        for (Conversation conversation:listConversation
        ) {
            if(conversation.getLatestMessage()!=null){
                Message latestMessage = conversation.getLatestMessage();
                long time = latestMessage.getCreateTime();
                String result1 = new SimpleDateFormat("HH:mm").format(time);
                TextContent textContent =(TextContent) latestMessage.getContent();
                String message = textContent.getText();
                if(message.length()>=20)
                    message = message.substring(0,20);
                String name = conversation.getTargetId();
                converseList.add(new Converse(name, message, result1, R.drawable.ic_launcher_background));
            }

        }

    }
}
