package com.example.sqlitetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    private ListView listviewCongViec;
    private ArrayList<CongViec> arrayCongViec;
    private CongViecAdapter congViecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listviewCongViec = findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();

        congViecAdapter = new CongViecAdapter(this,R.layout.item_cong_viec,arrayCongViec);
        listviewCongViec.setAdapter(congViecAdapter);

        database = new Database(this, "ghichu.sqlite", null,1);

        //tao bang
        database.queryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

        //insert data
//        database.queryData("INSERT INTO CongViec VALUES(null, 'Làm bài tập lớn')");

        getDataCongViec();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_cong_viec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menuAdd){
            showDialod();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDataCongViec(){
        //select data
        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            String tenCV = dataCongViec.getString(1);
            int Id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(Id, tenCV));
        }

        congViecAdapter.notifyDataSetChanged();
    }

    public void showDialogXoa(String congViec, int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Xoa cong viec");
        dialogXoa.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.queryData("delete from CongViec where Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                getDataCongViec();
            }
        });
        dialogXoa.show();
    }

    public void showDialogSua(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        EditText edtSuaCV = dialog.findViewById(R.id.edtSuaCV);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        Button btnSua = dialog.findViewById(R.id.btnSua);

        edtSuaCV.setText(ten);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCV = edtSuaCV.getText().toString().trim();
                database.queryData("update CongViec set TenCV='"+tenCV+"' where Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getDataCongViec();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialod() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtTen = dialog.findViewById(R.id.edtTenCongViec);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCV = edtTen.getText().toString();
                if (tenCV.equals("")){
                    Toast.makeText(MainActivity.this, "Ten cong viec khong duoc de trong", Toast.LENGTH_SHORT).show();
                }else {
                    database.queryData("insert into CongViec values(null,'"+tenCV+"')");
                    Toast.makeText(MainActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDataCongViec();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}