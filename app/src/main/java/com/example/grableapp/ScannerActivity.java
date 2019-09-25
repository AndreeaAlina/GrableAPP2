package com.example.grableapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;



public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    Firebase myFirebase;
    DatabaseReference Ref;
    private int cart;
    private int newVal;
    String res;
    private int oldVal;
    private int weight;
    private String myCart="";
    private int hascart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {

                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(ScannerActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        FirebaseDatabase _database = FirebaseDatabase.getInstance();
        Ref = _database.getReference();
        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());
        Intent intent= getIntent();
        cart = intent.getIntExtra("cos", 0 );

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        if (cart ==0){
        builder.setPositiveButton("Adauga in cos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScannerActivity.this);
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                String user = currentFirebaseUser.getUid();

                String key = Ref.child("users").child(user).child("Cumparaturi").push().getKey();

                Ref.child("users").child(user).child("Cumparaturi").child(key).setValue(myResult);
                scannerView.resumeCameraPreview(ScannerActivity.this);

            }
        });
        builder.setNegativeButton("renunta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScannerActivity.this);
            }
        });

        }
        else
            if (hascart==0){
        builder.setPositiveButton("Alege acest cos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myCart = result.getText();
                Toast.makeText(getApplicationContext(),"Ati ales "+ myCart, Toast.LENGTH_LONG).show();
                hascart = 1;
                scannerView.resumeCameraPreview(ScannerActivity.this);
            }
        });
        builder.setNegativeButton("renunta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScannerActivity.this);
            }
        });
        }
            else{

                builder.setPositiveButton("Adauga in cos", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Ref.child("cosuri").child(myCart).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                String myChildValues = (String) dataSnapshot.getKey();
                                int Value= dataSnapshot.getValue(Integer.class);
                                if(myChildValues.equals("oldValue"))
                                   oldVal =Value;
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {}

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
                        Ref.child("cosuri").child(myCart).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                String myChildValues = (String) dataSnapshot.getKey();
                                int Value= dataSnapshot.getValue(Integer.class);
                                if(myChildValues.equals("newValue"))
                                    newVal =Value;
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {}

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
                         int psweight = newVal - oldVal;
                         Ref.child("PRODUSE").child(myResult).addChildEventListener(new ChildEventListener() {
                             @Override
                             public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                 String myChildValues = (String) dataSnapshot.getKey();
                                 int Value;
                                 if(myChildValues.equals("greutate"))
                                 {Value=  dataSnapshot.getValue(Integer.class);
                                     oldVal =Value;}
                             }

                             @Override
                             public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                             @Override
                             public void onChildRemoved(DataSnapshot dataSnapshot) {}

                             @Override
                             public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                             @Override
                             public void onCancelled(DatabaseError databaseError) {}
                         });
                         if(psweight==weight)
                         { FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                         String user = currentFirebaseUser.getUid();

                         String key = Ref.child("users").child(user).child("Cumparaturi").push().getKey();

                         Ref.child("users").child(user).child("Cumparaturi").child(key).setValue(myResult);
                        scannerView.resumeCameraPreview(ScannerActivity.this);}
                         else Toast.makeText(getApplicationContext(), "greutatea produsului scanat nu corespunde cu greutatea adaugata in cos! ", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("renunta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.resumeCameraPreview(ScannerActivity.this);
                    }
                });

            }
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}
