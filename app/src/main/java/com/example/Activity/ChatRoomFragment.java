package com.example.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Beans.RoomInfo;
import com.example.Beans.Student;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private String m_detailedInterestsFlag;
    private String m_roomName;

    private int roomLimitNumber;
    private int currentRoomNumber;
    private Boolean isEnterRoom;

    private long m_currentMemberNumber;        //현재인원
    private String m_currentMemberNumberString;   //현재인원 스트링으로 변환해줄거


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

        list_of_rooms.clear();

        mStudent = (Student) getArguments().getSerializable("myInfo");
        m_detailedInterests = getArguments().getString("detailedInterests");
        m_chattingNumber = getArguments().getString("chattingNumber");
        m_roomName = getArguments().getString("roomName");
        m_makeRoomFlag = getArguments().getString("makeRoomFlag");
        m_detailedInterestsFlag = getArguments().getString("detailedInterestsFlag");

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
            /*트리 생성 chats -> 방제목 -> 관심분야
                                        -> 방제목
                                        ->인원  이 형태로 디비에 생성됨*/
            root.child("chats").child(m_roomName).child("title").setValue(m_roomName);
            root.child("chats").child(m_roomName).child("detailedInterests").setValue(m_detailedInterests);
            root.child("chats").child(m_roomName).child("limitMemberNumber").setValue(m_chattingNumber);
            root.child("chats").child(m_roomName).child("currentMemberNumber").setValue("1");
            root.child("chats").child(m_roomName).child("isEnterRoom").setValue("T");

            //날짜변환
            Calendar rightNow = Calendar.getInstance();
            Date date = rightNow.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String strDate = df.format(date);

            //트리 chats에 날짜까지 넣어준다.
            //memeber는 어떤방에 어떤유저가 있는지 알려주기 위해 넣어줌.
            root.child("chats").child(m_roomName).child("time").setValue(strDate);
            root.child("member").child(m_roomName).child(mStudent.getId()).setValue(true);

            //바로 방만들어줌. 바로 방만들어주는거 아니면 모든 방 목록 구경할 수 있음.
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("user_id", mStudent.getId());
            intent.putExtra("room_name", (m_roomName));
            startActivity(intent);
        }

        if(m_detailedInterestsFlag.equals("Y")) {
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    list_of_rooms.clear();

                    String detailedInterests = "";
                    String memberLimitNumber = "";
                    String time = "";
                    String title = "";

                    //Toast.makeText(getContext(), m_detailedInterests, Toast.LENGTH_SHORT).show();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //child는 현재 root에서 바로 아래 chats, message, users, member까지 온 상태
                        if (child.getKey().equals("chats")) {
                            for (DataSnapshot child2 : child.getChildren()) {
                                //child2는 if문에서 chats로 들어오고 방제목까지 온 상태
                                //list_of_rooms.add(new RoomInfo(child2.getChildren().));
                                for (DataSnapshot child3 : child2.getChildren()) {
                                    //child3는 if문에서 방제목(고유값)으로 들어오고 관심분야, 시간, 인원에 접근 할 수 있는 상태 if문으로 하나하나 값을 넣어주게 만듬.
                                    if (child3.getKey().equals("detailedInterests")) {
                                        detailedInterests = child3.getValue().toString();
                                    }
                                    if (child3.getKey().equals("limitMemberNumber")) {
                                        memberLimitNumber = (child3.getValue().toString());
                                    }
                                    if (child3.getKey().equals("title")) {
                                        title = (child3.getValue().toString());
                                    }
                                    if (child3.getKey().equals("time")) {
                                        time = child3.getValue().toString();
                                    }
                                    if (child3.getKey().equals("currentMemberNumber")) {
                                        m_currentMemberNumber = Long.valueOf(child3.getValue().toString());
                                        m_currentMemberNumberString = String.valueOf(m_currentMemberNumber);
                                        //currentMemberNumber++;
                                    }
                                }

                                if (m_detailedInterests.equals(child2.child("detailedInterests").getValue().toString())) {
                                        list_of_rooms.add(new RoomInfo(title, detailedInterests, memberLimitNumber, time, m_currentMemberNumberString));
                                }
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    list_of_rooms.clear();

                    String detailedInterests = "";
                    String memberLimitNumber = "";
                    String time = "";
                    String title = "";

                    //Toast.makeText(getContext(), m_detailedInterests, Toast.LENGTH_SHORT).show();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //child는 현재 root에서 바로 아래 chats, message, users, member까지 온 상태
                        if (child.getKey().equals("chats")) {
                            for (DataSnapshot child2 : child.getChildren()) {
                                //child2는 if문에서 chats로 들어오고 방제목까지 온 상태
                                //list_of_rooms.add(new RoomInfo(child2.getChildren().));
                                for (DataSnapshot child3 : child2.getChildren()) {
                                    //child3는 if문에서 방제목(고유값)으로 들어오고 관심분야, 시간, 인원에 접근 할 수 있는 상태 if문으로 하나하나 값을 넣어주게 만듬.
                                    if (child3.getKey().equals("detailedInterests")) {
                                        detailedInterests = child3.getValue().toString();
                                    }
                                    if (child3.getKey().equals("limitMemberNumber")) {
                                        memberLimitNumber = (child3.getValue().toString());
                                    }
                                    if (child3.getKey().equals("title")) {
                                        title = (child3.getValue().toString());
                                    }
                                    if (child3.getKey().equals("time")) {
                                        time = child3.getValue().toString();
                                    }
                                    if (child3.getKey().equals("currentMemberNumber")) {
                                        m_currentMemberNumber = Long.valueOf(child3.getValue().toString());
                                        m_currentMemberNumberString = String.valueOf(m_currentMemberNumber);
                                        //currentMemberNumber++;
                                    }
                                }
                                list_of_rooms.add(new RoomInfo(title, detailedInterests, memberLimitNumber, time, m_currentMemberNumberString));
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            RoomInfo r;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* TODO: 일단 채팅은 보류
                Intent intent = new Intent(getActivity(), Chat_Room.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("user_name", name);
                startActivity(intent);
                */

                r = list_of_rooms.get(position);

                //m_currentMemberNumber = Integer.valueOf(r.getM_roomCurrentMemberNumber());
                //m_currentMemberNumber++;
                //m_currentMemberNumberString = String.valueOf(m_currentMemberNumber);
                //r.setM_roomCurrentMemberNumber(m_currentMemberNumberString);
                //root.child("chats").child(r.getM_roomTitle()).child("currentMemberNumber").setValue(r.getM_roomCurrentMemberNumber());

                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("3", "3");
                        //Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

                        Loop1 : for (DataSnapshot child : dataSnapshot.getChildren()) {
                            //root안으로 들어옴.
                            if(child.getKey().equals("chats")){
                                Loop2 : for (DataSnapshot child2 : child.getChildren()) {
                                    if(child2.getKey().equals(r.getM_roomTitle())){
                                        for(DataSnapshot child3 : child2.getChildren()){
                                            if(child3.getKey().equals("limitMemberNumber")){

                                                roomLimitNumber = Integer.parseInt(child3.getValue().toString());
                                                currentRoomNumber = Integer.parseInt(r.getM_roomCurrentMemberNumber());

                                                if(roomLimitNumber > currentRoomNumber){
                                                    root.child("users").child(mStudent.getId()).child(r.getM_roomTitle()).setValue(true);
                                                    root.child("member").child(r.getM_roomTitle()).child(mStudent.getId()).setValue(true);
                                                    break Loop2;
                                                } else {
                                                    r.setM_isEnterRoom("F");
                                                    root.child("chats").child(r.getM_roomTitle()).child("isEnterRoom").setValue(r.getM_isEnterRoom());
                                                    break Loop1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (child.getKey().equals("member")) {
                                for (DataSnapshot child2 : child.getChildren()) {
                                    //if문으로 users, message, chats를 걸러내주고 member 가지로 들어왔음.
                                    if(child2.getKey().equals(r.getM_roomTitle())){
                                        m_currentMemberNumber = child2.getChildrenCount();
                                        m_currentMemberNumberString = String.valueOf(m_currentMemberNumber);
                                        r.setM_roomCurrentMemberNumber(m_currentMemberNumberString);
                                        root.child("chats").child(r.getM_roomTitle()).child("currentMemberNumber").setValue(r.getM_roomCurrentMemberNumber());
                                    }
                                }
                            }
                        }

                        if(r.getM_isEnterRoom().equals("T")) {
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra("user_id", mStudent.getId());
                            intent.putExtra("room_name", r.getM_roomTitle());
                            startActivity(intent);
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private class ChatRoomArrayAdapter extends ArrayAdapter<RoomInfo> {
        private ArrayList<RoomInfo> items;
        private int textViewResourceId;
        private Context context;

        public ChatRoomArrayAdapter(Context context, int textViewResourceId, ArrayList<RoomInfo> items) {
            super(context, textViewResourceId, items);
            this.context = context;
            this.textViewResourceId = textViewResourceId;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_item_chat_room, null);
            }

            RoomInfo r = items.get(position);

            if (r != null) {
                TextView text_room_time = (TextView) v.findViewById(R.id.text_room_time);
                TextView text_room_interests = (TextView) v.findViewById(R.id.text_room_interests);
                TextView text_room_title = (TextView) v.findViewById(R.id.text_room_title);
                TextView text_room_num_people = (TextView) v.findViewById(R.id.text_room_num_people);

                text_room_time.setText(r.getM_roomTime());
                text_room_interests.setText(r.getM_roomInterest());
                text_room_title.setText(r.getM_roomTitle());
                text_room_num_people.setText(r.getM_roomCurrentMemberNumber() + "/" + r.getM_roomLimitMemberNumber());
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

