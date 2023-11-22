package pe.pcs.escanearbarcode;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    private EditText txtCodigo;
    private Button btEscanear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btEscanear = findViewById(R.id.bt_escanear);
        txtCodigo = findViewById(R.id.et_codigo);

        btEscanear.setOnClickListener(view -> {
            if (MainActivity.this.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                escanear();
            } else {
                pedirPermisoCamara.launch(android.Manifest.permission.CAMERA);
            }
        });
    }

    private void escanear() {
        barcodeLauncher.launch(
                new ScanOptions().setPrompt("Escanear un  codigo de barras").setOrientationLocked(false)
        );
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() != null)
                    txtCodigo.setText(result.getContents());
            });

    private final ActivityResultLauncher<String> pedirPermisoCamara =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    escanear();
                } else {
                    Toast.makeText(MainActivity.this, "Necesita otorgar permiso de CAMARA", Toast.LENGTH_LONG).show();
                }
            });
}