package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN=123;

    /**
     * Método de creación de la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        login();
    }

    /**
     * Método de inicio de sesión de usuario
     */
    private void login(){
        FirebaseUser usuario= FirebaseAuth.getInstance().getCurrentUser();
        if(usuario!=null){
            Toast.makeText(this, "Inicia sesión: "+usuario.getDisplayName()+" - "+
                    usuario.getEmail()+" - "+usuario.getProviderId(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else{
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())).setIsSmartLockEnabled(false).build(), RC_SIGN_IN);
        }
    }

    /**
     * Método para devolución de errores en la ventana o en caso de ser correcta finalizar
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RC_SIGN_IN){
            if(resultCode== ResultCodes.OK){
                login();
                finish();
            }else{
                IdpResponse response=IdpResponse.fromResultIntent(data);
                if(response==null){
                    Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
                    return;
                }else if(response.getErrorCode()== ErrorCodes.NO_NETWORK){
                    Toast.makeText(this, "Sin conexion a internet", Toast.LENGTH_LONG).show();
                    return;
                }else if(response.getErrorCode()==ErrorCodes.UNKNOWN_ERROR){
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
