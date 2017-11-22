package id.sch.smkn2cikbar.exambrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputAddress extends Activity {
    private Button btnLanjut;
    private EditText inputAddress;
    private TextInputLayout inputLayoutAddress;
    private String s = "online";

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (this.view.getId()) {
                case R.id.input_address /*2131230805*/:
                    InputAddress.this.validateAddress();
                    return;
                default:
                    return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_address);
        try {
            s = getIntent().getStringExtra("valid");
            if (s.equals("offline")){
                Toast.makeText(InputAddress.this, "Url tidak valid/offline", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        this.inputLayoutAddress = findViewById(R.id.input_layout_address);
        this.inputAddress = findViewById(R.id.input_address);
        this.inputAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputAddress.this.inputAddress.setSelection(InputAddress.this.inputAddress.getText().length());
                }
            }
        });
        this.btnLanjut = findViewById(R.id.btn_lanjut);
        this.inputAddress.addTextChangedListener(new MyTextWatcher(this.inputAddress));
        this.btnLanjut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InputAddress.this.submitForm();
            }
        });
        if (!isNetworkAvailable()) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Peringatan").setMessage("Tidak ada koneksi").setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    InputAddress.this.finish();
                    System.exit(0);
                }
            }).show();
        }
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.inputAddress.setText(prefs.getString("autoSave", BuildConfig.FLAVOR));
        this.inputAddress.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                prefs.edit().putString("autoSave", s.toString()).commit();
            }
        });
        this.inputAddress.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == 0) {
                    switch (keyCode) {
                        case R.styleable.Toolbar_titleMarginEnd /*23*/:
                        case R.styleable.AppCompatTheme_editTextColor /*66*/:
                            InputAddress.this.submitForm();
                            return true;
                    }
                }
                return false;
            }
        });
    }

    private void submitForm() {
        if (validateAddress()) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("url", this.inputAddress.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    private boolean validateAddress() {
        if (this.inputAddress.getText().toString().trim().isEmpty()) {
            this.inputLayoutAddress.setError(getString(R.string.err_msg_name));
            requestFocus(this.inputAddress);
            return false;
        }
        this.inputLayoutAddress.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(5);
        }
    }

    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Keluar");
        alertDialogBuilder.setMessage("Yakin keluar dari aplikasi ?").setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }
}
