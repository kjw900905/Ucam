package com.example.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Beans.RoomInfo;
import com.example.Beans.Student;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class ChatRoomFragment extends Fragment {

    private EditText room_name;

    private ListView listView;
    private ChatRoomArrayAdapter adapter;
    private ArrayList<RoomInfo> list_of_rooms = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    Student mStudent;

    private String m_detailedInterests;                //관심분야
    private String m_chattingNumber;
    private String m_makeRoomFlag;

    private String m_roomName;

    private int roomIndex;


    public ChatRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mStudent = (Student) getArguments().getSerializable("myInfo");
        m_detailedInterests = getArguments().getString("detailedInterests");
        m_chattingNumber = getArguments().getString("chattingNumber");
        m_roomName = getArguments().getString("roomName");
        m_makeRoomFlag = getArguments().getString("makeRoomFlag");

        //Toast.makeText(getActivity(), m_detailedInterests+m_chattingNumber, Toast.LENGTH_SHORT).show();
        //room_name = (EditText) view.findViewById(R.id.room_name_edittext);
        adapter = new ChatRoomArrayAdapter(getContext(), R.layout.list_item_chat_room, list_of_rooms);
        listView = (ListView) view.findViewById(R.id.listViewConv);
        listView.setAdapter(adapter);
/*
        adapter = new FirebaseListAdapter<RoomInfo>(getActivity(), RoomInfo.class, R.layout.list_item_chat_room, root.child(m_roomName)) {
            @Override
            protected void populateView(View v, RoomInfo model, int position) {
                TextView room_title, room_interest, room_mem_num, room_time;
                room_title = (TextView) v.findViewById(R.id.text_room_title);
                room_interest = (TextView) v.findViewById(R.id.text_room_interests);
                room_mem_num = (TextView) v.findViewById(R.id.text_room_num_people);
                room_time = (TextView) v.findViewById(R.id.text_room_time);

                room_title.setText(model.getM_roomTitle());
                room_interest.setText(model.getM_roomInterest());
                room_mem_num.setText(model.getM_roomMemberNumber());
                room_time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getM_roomTime()));
            }
        };

        listView.setAdapter(adapter);
*/
        //request_user_name();

        /*add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Map<String, Object> map = new HashMap<String, Object>();
                //map.put(room_name.getText().toString(), "");
                //root.updateChildren(map);
                root.child("chats").child(room_name.getText().toString()).child("title").setValue(" ");
                root.child("chats").child(room_name.getText().toString()).child("memberNumber").setValue(" ");
            }
        });*/

        if (m_makeRoomFlag.equals("Y")) {

            root.child("chats").child(m_roomName).child("title").setValue(m_roomName);
            root.child("chats").child(m_roomName).child("detailedInterests").setValue(m_detailedInterests);
            root.child("chats").child(m_roomName).child("memberNumber").setValue(m_chattingNumber);
            root.child("chats").child(m_roomName).child("time").setValue(new Date().getTime());

            root.child("member").child(m_roomName).child(mStudent.getId()).setValue(true);

            if (m_makeRoomFlag.equals("Y")) {

                //root.child("chats").child(m_detailedInterests).child("title").setValue(m_detailedInterests);
                //root.child("chats").child(m_detailedInterests).child("memberNumber").setValue(m_chattingNumber);
                //root.child("users").child(mStudent.getId()).child("roomName").setValue(m_detailedInterests);


                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("user_id", mStudent.getId());
                intent.putExtra("room_name", (m_roomName));
                startActivity(intent);
            }
        }

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list_of_rooms.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String detailedInterests="";
                    String memberNumber="";
                    long time = 0;
                    String title="";

                    if (child.getKey().equals("chats")) {
                        for (DataSnapshot child2 : child.getChildren()) {
                            //list_of_rooms.add(new RoomInfo(child2.getChildren().));
                            for(DataSnapshot child3 : child2.getChildren()){
                                if(child3.getKey().equals("detailedInterests")){
                                    detailedInterests = child3.getValue().toString();
                                }
                                if(child3.getKey().equals("memberNumber")){
                                    memberNumber = (child3.getValue().toString());
                                }
                                if(child3.getKey().equals("title")){
                                    title = (child3.getValue().toString());
                                }
                                if(child3.getKey().equals("time")){
                                    time = Long.parseLong(child3.getValue().toString());
                                }
                            }
                            list_of_rooms.add(new RoomInfo(title, detailedInterests, memberNumber, time));
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* TODO: 일단 채팅은 보류
                Intent intent = new Intent(getActivity(), Chat_Room.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("user_name", name);
                startActivity(intent);
                */
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("user_id", mStudent.getId());
                intent.putExtra("room_name", ((TextView) view).getText().toString());
                root.child("users").child(mStudent.getId()).child("roomName").setValue(true);
                startActivity(intent);
            }
        });

        return view;
    }

    private class ChatRoomArrayAdapter extends ArrayAdapter<RoomInfo>{
        private ArrayList<RoomInfo> items;
        private int textViewResourceId;
        private Context context;

        public ChatRoomArrayAdapter(Context context, int textViewResourceId, ArrayList<RoomInfo> items){
            super(context, textViewResourceId, items);
            this.context = context;
            this.textViewResourceId = textViewResourceId;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_item_chat_room, null);
            }

            RoomInfo r = items.get(position);

            if(r != null){
                TextView text_room_time = (TextView)v.findViewById(R.id.text_room_title);
                TextView text_room_interests = (TextView)v.findViewById(R.id.text_room_interests);
                TextView text_room_title = (TextView)v.findViewById(R.id.text_room_title);
                TextView text_room_num_people = (TextView)v.findViewById(R.id.text_room_num_people);

                text_room_time.setText(String.valueOf(r.getM_roomTime()));
                text_room_interests.setText(r.getM_roomInterest());
                text_room_title.setText(r.getM_roomTitle());
                text_room_num_people.setText(r.getM_roomMemberNumber());

            }
            return v;
        }


    }

    /*
    private void request_user_name() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter name:");

        final EditText input_field = new EditText(getActivity());

        builder.setView(input_field);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText().toString();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                //request_user_name();
            }
        });

        builder.show();
        */
}

