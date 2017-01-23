package com.example.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class EditMemInfoFragment extends Fragment {

    EditText name, id, pw, studNum, univName;
    CheckBox male, female;

    public EditMemInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_mem_info, container, false);

        name = (EditText)view.findViewById(R.id.input_Name_Edit);
        id = (EditText)view.findViewById(R.id.input_Id_Edit);
        pw = (EditText)view.findViewById(R.id.input_Pw_Edit);
        studNum = (EditText)view.findViewById(R.id.input_Stu_Num_Edit);
        univName = (EditText)view.findViewById(R.id.input_School_Name_Edit);
        male = (CheckBox)view.findViewById(R.id.check_Box_M_Edit);
        female = (CheckBox)view.findViewById(R.id.checkBoxW_Edit);

        CallToShowText(name, id, pw, studNum, univName, male, female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "남성", Toast.LENGTH_SHORT).show();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "여성", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        Button btnSearch = (Button)view.findViewById(R.id.search_School_Edit);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "검색중...", Toast.LENGTH_SHORT).show();
                //TODO: 검색 관련 코드 추가
            }
        });

        Button btnUnregister = (Button)view.findViewById(R.id.unregister_Button);
        btnUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "회원탈퇴 완료\n이전 정보는 열람할 수 없습니다.", Toast.LENGTH_SHORT).show();
                //TODO: 뒤로가기 코드 추가
            }
        });

        Button btnSubmit = (Button)view.findViewById(R.id.submit_Edit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String ID = id.getText().toString();
                String PW = pw.getText().toString();
                String StudNum = studNum.getText().toString();
                String UnivName = univName.getText().toString();

                if(chkString_Edit(Name, ID, PW, StudNum, UnivName) && chkMaleFemale_Edit(male, female)) {
                    Toast.makeText(getActivity(), "회원정보 수정 완료", Toast.LENGTH_LONG).show();
                    //TODO: 뒤로가기 코드 추가
                }
            }
        });

        Button btnCancel = (Button)view.findViewById(R.id.cancel_Edit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "수정 취소", Toast.LENGTH_SHORT).show();
                //TODO: 뒤로가기 코드 추가
            }
        });

        return view;
    }

    private boolean chkString_Edit(String Name, String ID, String PW, String StudNum, String UnivName){
        int[] chk = { Name.length(), ID.length(), PW.length(), StudNum.length(), UnivName.length() };

        for(int i = 0; i < 5; i++){
            if(chk[i] <= 0){
                Toast.makeText(getActivity(), "정보를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        /*
        *  그 외에 다른 특수한 조건 사항이 있으면 추가할 것 (예: 특정 특수 문자 금지 등)
        *  (참인 것에 대해서 return true 하는 것이 아니라 거짓인 것에 대해서 return false 할 것)
        * */

        return true;
    }

    private boolean chkMaleFemale_Edit(CheckBox male, CheckBox female){
        boolean isMaleChecked = male.isChecked();
        boolean isFemaleChecked = female.isChecked();

        if(isMaleChecked && isFemaleChecked){
            Toast.makeText(getActivity(), "양성애자가 아니시라면 한 가지 성별만 선택하십시오.\n", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isMaleChecked || isFemaleChecked){
            return true;
        }

        Toast.makeText(getActivity(), "성별을 체크해 주세요.", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void CallToShowText(EditText name, EditText id, EditText pw, EditText studNum, EditText univName,
                                CheckBox male, CheckBox female){
        //TODO: 회원의 정보를 먼저 출력하도록 DB와 연동

        //일단은 임시 정보로 초기화.
        String Name = "Test Name";
        String ID = "Test ID";
        String PW = "Test PW";
        String StudNum = "11111111";
        String UnivName = "띵지머";
        Boolean isMaleChecked = true;
        Boolean isFemaleChecked = false;

        name.setText(Name);
        id.setText(ID);
        pw.setText(PW);
        studNum.setText(StudNum);
        univName.setText(UnivName);
        male.setChecked(isMaleChecked);
        female.setChecked(isFemaleChecked);
    }
}
