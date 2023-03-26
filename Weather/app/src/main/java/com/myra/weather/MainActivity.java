package com.myra.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import java.util.Formatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText edtCity;
    TextView txtCity, txtMain, txtTempInfo, txtFeelsInfo, txtHumidityInfo, txtSpeed,
            txtDirection, txtSunriseInfo, txtSunsetInfo;
    TextView txtTemp, txtFeels, txtHumidity, txtWind, txtSunrise, txtSunset;
    Button btnFetch;
    ImageView imgIcon;
    ApiCall apiCall;
    APIGeo apiGeoCall;
    FusedLocationProviderClient locClient;
    public static final int LOCATION_REQUEST = 0;
    public static final int LOCATION_ENABLED = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppWeather);
        setContentView(R.layout.activity_main);

        edtCity = findViewById(R.id.edtCity);
        btnFetch = findViewById(R.id.fetch);
        imgIcon = findViewById(R.id.imgIcon);

        txtCity = findViewById(R.id.txtCity);
        txtMain = findViewById(R.id.txtMain);
        txtTempInfo = findViewById(R.id.txtTempInfo);
        txtFeelsInfo = findViewById(R.id.txtFeelsInfo);
        txtHumidityInfo = findViewById(R.id.txtHumidityInfo);
        txtSpeed = findViewById(R.id.txtSpeed);
        txtDirection = findViewById(R.id.txtDirection);
        txtSunriseInfo = findViewById(R.id.txtSunriseInfo);
        txtSunsetInfo = findViewById(R.id.txtSunsetInfo);

        txtTemp = findViewById(R.id.txtTemp);
        txtFeels = findViewById(R.id.txtFeels);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtWind = findViewById(R.id.txtWind);
        txtSunrise = findViewById(R.id.txtSunrise);
        txtSunset = findViewById(R.id.txtSunset);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiCall = retrofit.create(ApiCall.class);
        apiGeoCall = retrofit.create(APIGeo.class);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtCity.getText().toString().matches("")) {
                    Toast.makeText(MainActivity.this, "City not entered.", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Weather> call = apiCall.getData(edtCity.getText().toString(), ServerInfo.key);
                    representData(call);
                }
            }
        });

        edtCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEND) {
                    btnFetch.performClick();
                    return true;
                }
                return false;
            }
        });

        locClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getLocation() {
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, LOCATION_REQUEST);
        } else {
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            edtCity.setText(String.format("%s, %s", location.getLatitude(), location.getLongitude()));
                            Call<com.myra.weather.Weather> call = apiGeoCall.getData(Double.toString(location.getLatitude()),
                                    Double.toString(location.getLongitude()), ServerInfo.key);
                            representData(call);
                        } else {
                            locClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
                                @Override
                                public boolean isCancellationRequested() {
                                    return false;
                                }

                                @NonNull
                                @Override
                                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                                    return null;
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Location>() {
                                @Override
                                public void onComplete(@NonNull Task<Location> task) {
                                    Location location = task.getResult();
                                    edtCity.setText(String.format("%s, %s", location.getLatitude(), location.getLongitude()));
                                    Call<Weather> call = apiGeoCall.getData(Double.toString(location.getLatitude()),
                                            Double.toString(location.getLongitude()), ServerInfo.key);
                                    representData(call);
                                }
                            });
                        }
                    }
                });
            } else {
                new AlertDialog.Builder(this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, LOCATION_ENABLED);
                    }
                }).setTitle("Turn On Location").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        }
    }

    public void representData(Call<Weather> call) {
        txtCity.setVisibility(View.GONE);
        txtMain.setVisibility(View.GONE);
        txtTempInfo.setVisibility(View.GONE);
        txtFeelsInfo.setVisibility(View.GONE);
        txtHumidityInfo.setVisibility(View.GONE);
        txtSpeed.setVisibility(View.GONE);
        txtDirection.setVisibility(View.GONE);
        txtSunriseInfo.setVisibility(View.GONE);
        txtSunsetInfo.setVisibility(View.GONE);
        imgIcon.setVisibility(View.GONE);

        txtTemp.setVisibility(View.GONE);
        txtFeels.setVisibility(View.GONE);
        txtHumidity.setVisibility(View.GONE);
        txtWind.setVisibility(View.GONE);
        txtSunrise.setVisibility(View.GONE);
        txtSunset.setVisibility(View.GONE);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.code() == 400) {
                    Toast.makeText(MainActivity.this, "City not entered.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "City not found.", Toast.LENGTH_SHORT).show();
                    return;
                }
                txtCity.setText(String.format("%s", edtCity.getText()));
                txtCity.setVisibility(View.VISIBLE);
                txtMain.setText(String.format("Description: %s", response.body().getDetails().getMain()));
                txtMain.setVisibility(View.VISIBLE);
                txtTempInfo.setText(String.format("%s°C", new Formatter().format("%1.1f", response.body().getMain().getTemp() - 273.15)));
                txtTempInfo.setVisibility(View.VISIBLE);
                txtFeelsInfo.setText(String.format("%s°C", new Formatter().format("%1.1f", response.body().getMain().getFeels_like() - 273.15)));
                txtFeelsInfo.setVisibility(View.VISIBLE);
                txtHumidityInfo.setText(String.format("%s%%", response.body().getMain().getHumidity()));
                txtHumidityInfo.setVisibility(View.VISIBLE);
                txtSpeed.setText(String.format("%s Km/h", new Formatter().format("%1.1f", response.body().getWind().getSpeed() * 3.6)));
                txtSpeed.setVisibility(View.VISIBLE);
                txtDirection.setText(response.body().getWind().getDirection());
                txtDirection.setVisibility(View.VISIBLE);
                txtSunriseInfo.setText(response.body().getSun().getSunrise());
                txtSunriseInfo.setVisibility(View.VISIBLE);
                txtSunsetInfo.setText(response.body().getSun().getSunset());
                txtSunsetInfo.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(getResources().getIdentifier("ic_" + response.body().getDetails().getIcon(), "drawable", "com.myra.weather"));
                imgIcon.setVisibility(View.VISIBLE);

                txtTemp.setVisibility(View.VISIBLE);
                txtFeels.setVisibility(View.VISIBLE);
                txtHumidity.setVisibility(View.VISIBLE);
                txtWind.setVisibility(View.VISIBLE);
                txtSunrise.setVisibility(View.VISIBLE);
                txtSunset.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to get data.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOCATION_ENABLED) {
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                getLocation();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_REQUEST)
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                getLocation();
    }

}