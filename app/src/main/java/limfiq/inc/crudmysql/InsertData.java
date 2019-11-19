package limfiq.inc.crudmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertData extends AppCompatActivity {
    //deklarasi komponen
    EditText id,nama,jumlah,harga, status;
    Button btnbatal,btnsimpan;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

//mengambil data dari intent yang digunakan
        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_id = data.getStringExtra("id");
        String intent_nama = data.getStringExtra("nama");
        String intent_jumlah = data.getStringExtra("jumlah");
        String intent_harga = data.getStringExtra("harga");
        String intent_status = data.getStringExtra("status");

//inisialisasi komponen dengan data yang diambil
        id = (EditText) findViewById(R.id.inp_id);
        nama = (EditText) findViewById(R.id.inp_nama);
        jumlah = (EditText) findViewById(R.id.inp_jumlah);
        harga= (EditText) findViewById(R.id.inp_harga);
        status= (EditText) findViewById(R.id.inp_status);
        btnbatal = (Button) findViewById(R.id.btn_cancel);
        btnsimpan = (Button) findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(InsertData.this);

        /*kondisi update / insert = */
        if(update == 1)
        {
            btnsimpan.setText("Update Data");
            id.setText(intent_id);
            id.setVisibility(View.GONE);
            nama.setText(intent_nama);
            jumlah.setText(intent_jumlah);
            harga.setText(intent_harga);
            status.setText(intent_status);

        }


        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update == 1)
                {
                    Update_data();
                }else {
                    simpanData();
                }
            }
        });

//perintah ketika button diklik
        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(InsertData.this,MainActivity.class);
                startActivity(main);
            }
        });
    }

    //perintah update data pada btnsimpan
    private void Update_data()
    {
        pd.setMessage("Update Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertData.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(InsertData.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertData.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("jumlah",jumlah.getText().toString());
                map.put("harga",harga.getText().toString());
                map.put("status",status.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);
    }



    //perintah simpan data di btnsimpan
    private void simpanData()
    {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertData.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(InsertData.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertData.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",id.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("jumlah",jumlah.getText().toString());
                map.put("harga",harga.getText().toString());
                map.put("status",status.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }
}

